import java.sql.*;
import java.util.Random;

public class CheckGenerateData {
    private Statement statement=null;
    private ResultSet rS;

    public CheckGenerateData(Statement statement) {
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
     * check person in customer , request table
     * @param pesel
     * @throws Exception
     */
    public boolean checkIfCustomerExist (String pesel){
        try {
            rS=statement.executeQuery("Select * from customers where pesel='"+pesel+"'");
            rS.next();
            rS.getString("firstname");
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return true;
        }
    }

    public String generateAccNr() throws SQLException {
        Random random= new Random();
       while(true) {
           int accNr = random.nextInt(999999999) + 1;
           String strAccNr = Integer.toString(accNr);
           try {
               rS=statement.executeQuery("Select * from account where id_account='" + strAccNr + "'");
               rS.next();
               rS.getString("balance");
           }catch (Exception e){
               System.out.println("generateAccNr exception");
               System.out.println(e.getMessage());
               return strAccNr;
           }
       }

    }

    public String generateLogin() throws SQLException {
        Random random= new Random();
        while(true) {
            int login = random.nextInt(99999) + 1;
            String strLogin = Integer.toString(login);
            try {
                rS=statement.executeQuery("Select * from users where login='" + strLogin + "'");
                rS.next();
                rS.getString("login");
            }catch (Exception e){
                System.out.println("generateLogin exception");
                System.out.println(e.getMessage());
                return strLogin;
            }
        }

    }

    public String generatePassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
