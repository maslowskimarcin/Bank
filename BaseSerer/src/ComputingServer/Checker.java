package ComputingServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.pow;

/**
 * additional class with checks requiered data
 * in database ,ComputnigServerImpl uses this
 * class in conditionals
 */
public class Checker {
    private Statement statement=null;
    private ResultSet rS;

    public Checker(Statement statement) {
        this.statement = statement;
    }

    /**
     * check person in AddAccReq
     * @param pesel
     * @ returnn false if someone is enrolled on requwst
     */
    public boolean checkCustomerInAddAccReq (String pesel) {
        try {
            rS=statement.executeQuery("Select * from newaccountrequest where pesel='"+pesel+"'");
            rS.next();
            rS.getString("firstname");
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return true;
        }
    }
    /**
     * this function checks if client with given
     * pesel already exist
     * @param data
     * @throws Exception
     */
    public boolean checkIfCustomerExist (String data){
        try {
            if(data.length()>5)
                rS=statement.executeQuery("Select * from customers where pesel='"+data+"'");
            else
                rS=statement.executeQuery("Select * from customers where customer_nr='"+data+"'");
            rS.next();
            rS.getString("firstname");
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return true;
        }
    }


    /**
     * function gets balance of client with given number
     * @param accNr
     * @return balance
     */
    public String checkBalance(String accNr){
        try {
            rS=statement.executeQuery("Select * from account where id_account='"+accNr+"'");
            rS.next();
            return rS.getString("balance");
        }catch (Exception e){
            System.out.println("generateAccNr exception");
            System.out.println(e.getMessage());
            return "";
        }
    }
    /**
     * function gets balance of client with given client number
     * @param login
     * @return balance
     */
    public String checkBalanceLogin(String login){
        try {
            rS=statement.executeQuery("Select * from account natural join customers where customer_nr='"+login+"'");
            rS.next();
            return rS.getString("balance");
        }catch (Exception e){
            System.out.println("generateAccNr exception");
            System.out.println(e.getMessage());
            return "";
        }
    }
    /**
     * function checks if given account number exists
     * @param accTo
     * @return
     */
    public String findAccount(String accTo){
        try {
            ResultSet rS = statement.executeQuery("Select id_account from account where id_account='"+accTo+"'");
            if( rS.next()) //check rS if it doesn't contain any data then return empty string
                return rS.getString("id_account");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println("findAccount function exception");
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * checks if someone is mature and date in pesel
     * @param pesel
     * @return false in case incorrect date or age under 18
     */
    public boolean checkAge(String pesel){

        String year=pesel.substring(0,2);
        String month=pesel.substring(2,4);
        String day=pesel.substring(4,6);

        if(Integer.parseInt(month) > 20){
            year="20"+year;
            month= (Integer.parseInt(month)-20)+"";
            if(Integer.parseInt(month)<10)
                month="0"+month;
        }else {
            year="19"+year;
        }

        LocalDate localDate = LocalDate.now();
        String peselDate = year+month+day;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate date = LocalDate.parse(peselDate, formatter);
            Period p = Period.between(date, localDate);
            if(p.getYears()<18)
                return false;
        }catch (Exception e){
            System.out.println("Zla data");
            return false;
        }
        return true;
    }

    /**
     * function checks if customer has a loan or request for loan in database
     * @param login
     * @return true when customer does't have any loan
     * @throws SQLException
     */
    public boolean checkLoan(String login) throws SQLException {
        try {
            rS=statement.executeQuery("Select * from loan where customer_nr='"+login+"'");
            rS.next();
            rS.getString(1);
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }
    /**
     * calculate final amoun
     * @param time
     * @return
     */
    public String checkAmount(String amount,String time,String rate){
        Double Ko=Double.parseDouble(amount);
        Double p=Double.parseDouble(rate);
        Double n=Double.parseDouble(time);
        Double result=Ko* pow(1+p*1/100,n);
        DecimalFormat df=new DecimalFormat("#.##");
        return df.format(result);
    }
    /**
     * checks Bank rate depending on time
     * @param time
     * @return corretc bank rate
     */
    public String[] checkBankRate(String time) throws SQLException {
        String rate[]=new String[2];
        switch(time){
            case "1":
                rS=statement.executeQuery("Select id_rate,rate from bankrate where time='"+time+"'");
                rS.next();
                rate[0]= rS.getString("id_rate");
                rate[1]=rS.getString("rate");
                break;
            case "3":
                rS=statement.executeQuery("Select id_rate,rate from bankrate where time='"+time+"'");
                rS.next();
                rate[0]= rS.getString("id_rate");
                rate[1]=rS.getString("rate");
                break;
            case "6":
                rS=statement.executeQuery("Select id_rate,rate from bankrate where time='"+time+"'");
                rS.next();
                rate[0]= rS.getString("id_rate");
                rate[1]=rS.getString("rate");
                break;
            case "12":
                rS=statement.executeQuery("Select id_rate,rate from bankrate where time='"+time+"'");
                rS.next();
                rate[0]= rS.getString("id_rate");
                rate[1]=rS.getString("rate");
                break;
            default: break;
        }
        return rate;
    }

    /**
     *
     *
     * @param cust_nr
     */
    public void checkToDelete(String cust_nr) {

        try {
            statement.executeUpdate("DELETE from investment where customer_nr='" + cust_nr + "'");
            statement.executeUpdate("DELETE  from loan where customer_nr='" + cust_nr + "'");

            rS=statement.executeQuery("SELECT pesel from customers where customer_nr='"+cust_nr+"'");
            rS.next();
            String pesel=rS.getString("pesel");
            statement.executeUpdate("DELETE  from newaccountrequest  where pesel='" + pesel + "'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("checkToDelete");
        }

        try {
            rS=statement.executeQuery("select id_account FROM transfer JOIN account on " +
                    "transfer.accFrom=account.id_account JOIN customers ON customers.pesel=account.pesel where customer_nr='"+cust_nr+"'");
            rS.next();
            String account=rS.getString("id_account");
            statement.executeUpdate("DELETE  from transfer where accFrom='" + account + "'");
        } catch (SQLException e) {
            System.out.println( e.getMessage());
            System.out.println("customer does not have transactions");
        }
    }
}
