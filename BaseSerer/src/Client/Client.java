package Client;

import Base.*;

import java.util.List;

public class Client
{

    private CheckData checkData = new CheckData();
    String accNo;
    String userId;
    String balance;
    BaseServerFace server;
    PersonalData personalData;

    public void logOut(){

        try
        {
            server.LogOut(userId); // encoding userId
        }
        catch (Exception e)
        {
            System.out.println("err Log out");
        }

    }

    /*
    * errocode:
    * 0 everything ok
    * 1 sth wrong with base server
    * 2 unsuitable data in field
    * 3 not equal password and repeated password
    * */
    public String changePassword( String newPassword, String newPasswordRepeat)
    {
        LogTo toSend = new LogTo();
        String receivedErr;

        if(!newPassword.equals(newPasswordRepeat))
            return "3";

        if (!checkData.checkPassword(newPassword))
            return "2";

        toSend.login = userId;
        toSend.password = newPassword;

        try
        {
            receivedErr = server.changePassword(toSend);
        }
        catch (Exception e)
        {
            return "1";
        }

        if(receivedErr == null)
            return "1";

        return receivedErr;
    }

    /*
    * errocode:
    * 0 everything ok
    * -1 problem with data base
    * */
   public String getBalance()
   {
       String received;

        try
        {
            received = server.getBalance(userId);
        }
        catch (Exception e)
        {
            return "-1";
        }

       if(received == null)
           return "-1";

       if(!received.equals("-1"))
       {
           balance = received;
           return "0";
       }

       return "-1";

   }

    /*
    * errocode:
    * 0 everything ok
    * 1 no resources
    * 2 there is no such account
    * 3 sth wrong with data base
    * 4 sth wrong with base server
    * 5 unsuitable data in fields
    * */
    public String sendTransfer(String accNoTo, String amount, String amountAfterComma, String transferTitle)
    {
        Transfer toSend = new Transfer();
        String receivedErr;

        if (!checkData.checkTransferData(accNoTo, amount, amountAfterComma, transferTitle))
            return "5";

        toSend.login = userId;
        toSend.accNoFrom = accNo;
        toSend.accNoTo = accNoTo;
        toSend.amount = amount + "." + amountAfterComma;
        toSend.title = transferTitle;

        try
        {
            receivedErr = server.transfer(toSend);
        }
        catch (Exception e)
        {
            return "4";
        }

        if(receivedErr == null)
            return "4";

        return receivedErr;
    }

    /*
    * erroCode:
    * PersonalData object   ok
    * null   sth wrong with server
    * */
    public void getPersonalData()
    {
        PersonalData received = new PersonalData();

        try
        {
            received = server.getPersonalData(userId);
        }
        catch (Exception e)
        {

            System.out.println("Error: " + e);
            e.printStackTrace();
            personalData = null;
        }

        if(received == null)
            personalData = null;

        personalData = received;
    }

    /*
    * erroCode:
    * 1 sth wrong with data base
    * 0 ok
    * -1 sth wrong with base server
    * -2 email != emailRepeated
    * -3 unsuitable data in fields
    * */
    public String changePersonalData(String name, String lastName, String pesel, String city, String street, String zipCode, String idNumber, String phoneNum, String email, String emailRepeated)
    {

        PersonalData toSend = new PersonalData();
        String received;

        if(!email.equals(emailRepeated))
            return "-2";

        if (!checkData.checkPersonalData(name, lastName, pesel, city, street, zipCode, idNumber, phoneNum, email))
            return "-3";

        toSend.pesel = pesel;
        toSend.city = city;
        toSend.email = email;
        toSend.firstName = name;
        toSend.idNumber = idNumber;
        toSend.lastName = lastName;
        toSend.phoneNumber = phoneNum;
        toSend.street = street;
        toSend.zipCode = zipCode;

        try
        {
            received = server.requestChangePersonalData(userId, toSend);
        }
        catch (Exception e)
        {

            System.out.println("Error: " + e);
            e.printStackTrace();
            return "-1";
        }

        if(received == null)
            return "-1";

        return received;
    }

    /*
    * erroCode:
    * er 0 ok, return Loan object
    * er 1 sth wrong with database, return null
    * */
    public List<InvestmentHistory> getInvestmentHistory()
    {
        ListInvestment received;

        try
        {
            received = server.getInvestmentHistory(userId);
        }
        catch (Exception e)
        {

            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        }

        if (received == null)
            return null;

        if (received.error.equals("0"))
            return received.list;

        return null;
    }

    /*
    * erroCode:
    * er 0 ok, return Loan object
    * er 1 sth wrong with database, return null
    * */
    public Loan getLoanHistory()
    {
        Loan received;

        try
        {
            received = server.getLoanHistory(userId);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        }

        if (received == null)
            return null;

        if (received.error.equals("0"))
            return received;

        return null;
    }

    /*
    * erroCode:
    * er 0 ok, return Loan object
    * er 1 sth wrong with database, return null
    * */
    public List<Transfer> getTranserHistory()
    {
        TransferHistory toSend = new TransferHistory();
        TransferData received;

        toSend.date = "1000";
        toSend.login = userId;

        try
        {
            received = server.getTransferHistory(toSend);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        }

        if (received == null)
            return null;

        if (received.error.equals("0"))
            return received.transferList;

        return null;
    }

    /*
    * erroCode:
    * -2 wrong data
    * -1 sth worn with server
    * 0 ok
    * 1 sth wrong with database
    * */
    public String sendReqLoan(String amount, String months, String workPlace, String salary, String instolment)
    {
        Loan toSend = new Loan();
        String received;

        if(!checkData.checkCompanyName(workPlace) || !checkData.checkIfOnlyNum(salary))
            return "-2";

        toSend.amount = amount;
        toSend.numberOfMonths = months;
        toSend.salary = salary;
        toSend.instalment = instolment;
        toSend.login = userId;

        try
        {
            received = server.requestLoan(toSend);
        }
        catch (Exception e)
        {

            System.out.println("Error: " + e);
            e.printStackTrace();
            return "-1";
        }

        if(received == null)
            return "-1";

        return received;
    }

    /*
    * erroCode:
    * -3 amount is not between 500 and 10 0000
    * -2 unsuitable data in fields
    * -1 sth wrong with base server
    * 0 ok
    * 1 sth wrong with data base
    * 2 no enough resources
*   */
    public String  sendReqInvestment(String amount, String amountAfterComma, String time)
    {

        Investment toSend = new Investment();
        String received;
        double amountCheck, max = 10000.00, min = 500.00;

        System.out.println("time = " + time);

        if(amountAfterComma.length() != 2 || !checkData.checkIfOnlyNum(amount) || !checkData.checkIfOnlyNum(amountAfterComma) || time.equals("0"))
            return "-2";


         amountCheck = Double.parseDouble(amount + "." + amountAfterComma);
         if (amountCheck < min || amountCheck > max)
             return "-3";

        //Pack
        toSend.amount = amount + "." + amountAfterComma;
        toSend.time = time;
        toSend.login = userId;

        try
        {
            received = server.requestInvestment(toSend);
        }
        catch (Exception e)
        {

            System.out.println("Error: " + e);
            e.printStackTrace();
            return "-1";
        }

        //chcek if received if null !!!
        if(received == null)
            return "-1";

        return received;
    }
}
