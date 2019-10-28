package sample;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Client
{

    private CheckData checkData = new CheckData();
    String accNo;
    String userId;
    String balance;
    ServerFace server;

    public int communicateWithServer()
    {
        // String url = "rmi://217.182.77.242/";
        String url = "rmi://localhost/";
        String name = "maslo";

        try
        {
            Context context = new InitialContext();

            server = (ServerFace) context.lookup(url + "ServerFace");

            //wywołanie metdoy chcecklogin z serwera
            server.CheckLogin(name, "0");

        } catch (Exception e)
        {
            //return 0;   // test
            return -1;
        }

        return 0;
    }
    /*
    * erroCode:
    * 1 everything ok admin log in
    * 0 everything ok client log in
    * -1 cannot connect to server
    * -2 unsuitable data in fields
    * */
    public int login(String login, String password)
    {   
        int errorCode = -1;
        LogTo toSend = new LogTo();
        LogFrom received;

        // connect to server
//        if(communicateWithServer()==-1)
//            return errorCode;

        //check whether data is correct
        if (!checkData.checkLoginData(login, password))
        {
            errorCode = -2;
            return errorCode;
        }

        //encoding data to send
        //TO DO

        //Pack data to send
        toSend.login = login;
        toSend.password = password;

        //checking whether new thread can be created
        //TO DO

        //new thread creating
        //TO DO
//---------------------------------poprawne wysylanie------------------------------------------
//         //sending and receiving data to/from main server, interpreting received data all in thread
//        try
//        {
//            received = (LogFrom)server.logIn(login, toSend);
//        }
//        catch (Exception e)
//        {
//            return -1;
//        }
//         //chcek if received if null !!!
//        if(received == null)
//            return errorCode;
//----------------------------------------------------------------------------------------------
        //decoding data
        //TO DO

//--------------------------do testowania odbierania danych-------------------------------------------
        received = new LogFrom();
        received.error = "0";
        received.status = "c";
        received.login = "1234";
        received.balance = "1140.56";
        received.accNo  = "15975364820";
//----------------------------------------------------------------------------------------------

        if(received.error.equals("0"))
        {
            if (received.status.equals("c"))// ok and client
            {
                balance = received.balance;
                accNo = received.accNo;
                errorCode = 0;
            }
            else if (received.status.equals("a"))// ok and admin
                errorCode = 1;

            userId = received.login;
        }
        else if(received.error.equals("1"))// sht wrong
            errorCode=-2;

        //end thread

        //can I return sth inside a thread or better outside??
        return errorCode;
    }
    /*
    * errocode:
    * 0 everything ok
    * -1 cannot connect to server
    * -2 email != emailRepeated
    * -3 unsuitable data in fields
    * */
    public int register(String name, String lastName, String pesel, String city, String street, String zipCode, String idNumber, String phoneNum, String email, String emailRepeated)
    {
        int errorCode = -1;
        PersonalData toSend = new PersonalData();
        String received;

        if(!email.equals(emailRepeated))
        {
            errorCode = -2;
            return errorCode;
        }

        if (!checkData.checkPersonalData(name, lastName, pesel, city, street, zipCode, idNumber, phoneNum, email))
        {
            errorCode = -3;
            return errorCode;
        }

        // connect to server
        if(communicateWithServer()==-1)
            return errorCode;

        //Putting everything in to a list S

        //encoding the list S of data
        //TO DO

        //checking whether new thread can be created
        //TO DO

        //new thread creating
        //TO DO

//---------------------------------poprawne wysylanie------------------------------------------
//         //sending and receiving data to/from main server, interpreting received data all in thread
//        try
//        {
//            received = (String)server.requestAddAccount(userId, toSend);
//        }
//        catch (Exception e)
//        {
//            return -1;
//        }

//         //chcek if received if null !!!
//        if(received == null)
//            return errorCode;
//----------------------------------------------------------------------------------------------

//--------------------------do testowania odbierania danych-------------------------------------------
        received = "0";
//----------------------------------------------------------------------------------------------

        if(received.equals("0"))
            errorCode = 0;
        else if(received.equals("1"))
            errorCode=-2;

        //end thread

        //can I return sth inside a thread or better outside??
        return errorCode; // only to tests
    }
}
