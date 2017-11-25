import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class Server extends UnicastRemoteObject implements ServerFace
{
    final private String URL="jdbc:mysql://localhost:3306/bankdb";
    private Connection connection=null;
    private Statement statement=null;

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
            ResultSet rS= statement.executeQuery("Select password,status from users where login='"+logTo.login+"' and password='"+logTo.password+"'" );
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
        return null;
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
            if( checkIfCustomerExist(personalData.pesel) &&
                    checkCustomerInAddAccReq(personalData.pesel) ){ //+++age
                statement.execute("SELECT id_request FROM newaccountrequest");
                ResultSet rS=statement.getResultSet();
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
    /**
     * check person in AddAccReq
     * @param pesel
     * @ returnn false if someone is enrolled on requwst
     */
    public boolean checkCustomerInAddAccReq (String pesel) {
        try {
            ResultSet rS=statement.executeQuery("Select * from newaccountrequest where pesel='"+pesel+"'");
            rS.next();
            rS.getString("firstname");
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return true;
        }
    }
    /**
     * check person in customer , request table
     * @param pesel
     * @throws Exception
     */
    public boolean checkIfCustomerExist (String pesel){
        try {
            ResultSet rS=statement.executeQuery("Select * from customers where pesel='"+pesel+"'");
            rS.next();
            rS.getString("firstname");
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return true;
        }
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
        return null;
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
        return null;
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