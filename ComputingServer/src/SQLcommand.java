import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**The class implements functions which gather data
 * from database
 */
public class SQLcommand {
    private Statement statement;
    private String sql;
    private Object temp;

    public SQLcommand(Statement statement,Object temp) {
        this.statement = statement;
        this.temp=temp;
    }

    /**
     *Log in function allows person to log in and use application
     * @return list
     * @throws SQLException
     */
    public Object logTo() throws SQLException {
        LogTo logTo=(LogTo)temp;
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

    /**
     * Add new customer to request table in database
     * @return error
     */
    public String requestAddAccount (){
        PersonalData personalData=(PersonalData) temp;
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
            return "1";
        }
        return "0";
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

    public Object getRequestAddAccount(){
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

    public String answerAddAccountReq(){

        return "0";
    }
//    public List<String> transfer() {
//        double balance,transferAmount,senderBalance;
//        if(!balance().equals("")) {
//            balance = Double.parseDouble(balance());
//            transferAmount = Double.parseDouble(list.get(3));
//            senderBalance = balance - transferAmount;
//
//            if (senderBalance < 0 || findAccount().equals("")) {
//                System.out.println("You don't have enough money/ there is no account with given number");
//                list.clear();
//                list.add("1");
//            }
//            else {
//                try {
//                    sql = "UPDATE account a  join customers c on a.pesel=c.pesel  set a.balance= '" + senderBalance + "' WHERE c.customer_nr='" + login + "'";
//                    statement.executeUpdate(sql); //update sender account
//                    sql = "UPDATE account SET balance=balance+'" + transferAmount + "' WHERE id_account='" + list.get(2) + "'";
//                    statement.executeUpdate(sql);
//                    list.clear();
//                    list.add("0"); //transfer accepted
//                } catch (SQLException e) {
//                    list.add("1");
//                    System.out.println(e.getMessage());
//                } catch (Exception e) {
//                    list.add("1");
//                    System.out.println("balance function exception");
//                    System.out.println(e.getMessage());
//                }
//            }
//        }else {
//            list.clear();
//            list.add("1");
//        }
//       return list;
//    }
//
//    /**
//     * Funcion chcecks customer balance
//     * @return balance for customer with specified login
//     * @return empty string if there is no balance for specified login
//     *
//
//    /**
//     * Function checks given account
//     * @return empty string if there is no given account
//     * @return string with account number
//     */
//    private String findAccount(){
//        sql="Select id_account from account where id_account='"+list.get(2)+"'";
//        try {
//            ResultSet rS = statement.executeQuery(sql);
//           if( rS.next()) //check rS if it doesn't contain any data then return empty string
//               return rS.getString("id_account");
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }catch(Exception e){
//            System.out.println("findAccount function exception");
//            System.out.println(e.getMessage());
//        }
//        return "";
//    }
//
//    public List<String> addCustomer(){
//            //CHECK AGE
//        return list;
//    }
//
//    int getAge(String pesel){
//        return 0;
//    }

}
