import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Server extends UnicastRemoteObject implements ServerFace
{
    final private String URL="jdbc:mysql://localhost:3306/bankdb";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet rS=null;
    CheckGenerateData check;

    protected Server() throws RemoteException, SQLException {
        super();
        try {
            connection = DriverManager.getConnection(URL,"root","");
            statement=connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        check=new CheckGenerateData(statement);
    }

    @Override
    public void SendTransfer(String fromClient, String toClient, int value) throws RemoteException
    {

    }

    @Override
    public void CheckLogin(String login, String password) throws RemoteException
    {

    }

    @Override
    public void CheckLogout(String login) throws RemoteException
    {

    }

    @Override
    public Object logIn(String login, Object data) throws RemoteException
    {
        LogTo logTo=(LogTo)data;
        LogFrom logFrom=new LogFrom();
        try {
            rS= statement.executeQuery("Select password,status from users where login='"+logTo.login+"' and password='"+logTo.password+"'" );
            rS.next();
            if(logTo.password.equals(rS.getString("password"))){ //checks password capital/small letters
                logFrom.error="0";
                logFrom.status=rS.getString("status");
                logFrom.login=logTo.login;
                System.out.println(logFrom.login);

                if(logFrom.status.equals("C") ) { //add balance if client is a user
                    rS=statement.executeQuery("Select balance,id_account from account natural join customers where customer_nr='"+logTo.login+"'");
                    rS.next();
                    logFrom.balance= rS.getString("balance");
                    logFrom.accNo= rS.getString("id_account");
                }

            }else throw new Exception();
        } catch(SQLException e){
            System.out.println("resultset exception");
            System.out.println(e.getMessage());
            logFrom.error="1";
        } catch (Exception e){
            System.out.println("There is no that person in database");
            logFrom.error="1";
        }
        return logFrom;
    }

    @Override
    public Object restartPassword(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object transfer(String login, String accFrom, String accTo, Object data) throws RemoteException
    {
        Transfer transfer=(Transfer) data;
        String balanceDB=check.checkBalance(transfer.accNoFrom);
        double balance,transferAmount,senderBalance;
        int id_transfer=0;

        if(!transfer.equals("")) { // check balance before transfer
            balance = Double.parseDouble(balanceDB);
            transferAmount = Double.parseDouble(transfer.amount);
            senderBalance = balance - transferAmount;

            if (senderBalance < 0 || check.findAccount(transfer.accNoTo).equals("")) {
                System.out.println("You don't have enough money/ there is no account with given number");
               return "1";
            }
            else {
                try {
                    statement.executeUpdate("UPDATE account a  join customers c on a.pesel=c.pesel  set a.balance= '" + senderBalance + "' WHERE c.customer_nr='" + login + "'"); //update sender account
                    statement.executeUpdate("UPDATE account SET balance=balance+'" + transferAmount + "' WHERE id_account='" + transfer.accNoTo + "'");
                    statement.execute("SELECT id_transfer FROM transfer");
                    rS=statement.getResultSet();

                    while (rS.next())
                        id_transfer = Integer.parseInt(rS.getString(1));

                    id_transfer+=1;

                    statement.executeUpdate("INSERT into transfer (id_transfer,accFrom,accTo,amount,title,date) " +
                                            "VALUES ('"+id_transfer+"','"+transfer.accNoFrom+"','"+transfer.accNoTo+
                                            "','"+transfer.amount+"','"+transfer.title+"','"+check.generateDate()+"')");
                    return "0";
                } catch (Exception e) {
                    System.out.println("balance function exception");
                    System.out.println(e.getMessage());
                    return "1";
                }
            }
        }else {
            return "1";
        }
    }

    @Override
    public Object changePassword(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object makeDeposit(String login, String accTo, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object requestAddAccount(String login, Object data) throws RemoteException, SQLException {
        PersonalData personalData=(PersonalData) data;
        int id_req=0;
        try{
            if( check.checkIfCustomerExist(personalData.pesel) &&
                    check.checkCustomerInAddAccReq(personalData.pesel) ){ //+++age
                statement.execute("SELECT id_request FROM newaccountrequest");
                rS=statement.getResultSet();
                while (rS.next())
                    id_req = Integer.parseInt(rS.getString(1));

                id_req+=1;

                statement.executeUpdate("INSERT INTO newaccountrequest VALUES('"+id_req+ "','"+personalData.firstName+
                        "','"+personalData.lastName+"','"+personalData.street+"','"+personalData.city+"','"+personalData.zipCode+
                        "','"+personalData.idNumber+"','"+personalData.email+"','"+personalData.phoneNumber+"','"+personalData.pesel+"',1)");

            }else throw new Exception();

        }catch (Exception e){
            System.out.println(e.getMessage());
            statement.close();
            return "1";
        }
        statement.close();
        return "0";
    }

    @Override
    public Object requestChangePersonalData(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object requestLoan(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object requestInvestment(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object answerAddAccountReq(String login, Object data) throws RemoteException
    {
        AddAccReqDecision req=(AddAccReqDecision) data;

        if (req.decision.equals("y")) {
            try {
                String logNr = check.generateLogin();
                statement.executeUpdate("INSERT INTO users (login,password,status) VALUES ('" + logNr + "','" + check.generatePassword() + "','C')");
                statement.executeUpdate("INSERT INTO customers (pesel,customer_nr,firstname,lastname,idNumber,street,email,zipcode,city,phonenumber)" +
                        " SELECT pesel,'" + logNr + "',firstname,lastname,idNumber,street,email,zipcode,city,phonenumber from newaccountrequest where id_request='" + req.id_req + "'");
                statement.executeUpdate("INSERT into account (id_account,balance,pesel) SELECT '" + check.generateAccNr() + "',0.00,pesel " +
                        "from newaccountrequest where id_request='" + req.id_req + "'");
                statement.executeUpdate("Delete from newaccountrequest where id_request='" + req.id_req + "'");
            } catch (Exception e) {
                System.out.println("answer add acc req exception");
                System.out.println(e.getMessage());
                return "1";
            }

        }else {
            try{
                statement.executeUpdate("Delete from newaccountrequest where id_request='" + req.id_req + "'");
            } catch (SQLException e) {
                System.out.println("answer add acc req exception");
                e.printStackTrace();
                return "1";
            }

        }
        return "0";
    }

    @Override
    public Object answerChangePersonalDataReq(String login, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object answerLoanReq(String login, String answer, String accTo, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object answerInvestmentReq(String login, String answer, String accTo, Object data) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getBalance(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getTransferHistory(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getPersonalData(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getLoanHistory(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getInvestmentHistory(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getRequestAddAccount(String login) throws RemoteException
    {
        RequestListAddAccount req=new RequestListAddAccount();
        req.data=new ArrayList<>();
        try {
            ResultSet rS=statement.executeQuery("SELECT * from newaccountrequest");
            while(rS.next()){
                AddAccountRequest addAcc=new AddAccountRequest(rS.getString("id_request"),rS.getString("firstname"),
                        rS.getString("lastname"),rS.getString("street"),rS.getString("zipCode"),
                        rS.getString("city"),rS.getString("pesel"),rS.getString("idNumber"),
                        rS.getString("email"),rS.getString("phoneNumber"));
                req.data.add(addAcc);
            }
            req.error="0";
        }catch(Exception e){
            System.out.println(e.getMessage());
            req.error="1";
        }

        return  req;
    }

    @Override
    public Object getRequestChangePersonalData(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getRequestLoan(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object getRequestInvestment(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public Object LogOut(String login) throws RemoteException
    {
        return null;
    }

    @Override
    public String getDescription(String text) throws RemoteException
    {
        return null;
    }
}