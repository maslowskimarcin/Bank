package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Client
{


    private List<String> sendingData = new ArrayList<String>();
    private List<String> receivedData = new ArrayList<String>();
    private CheckData checkData = new CheckData();
    String clientNr;
    String userId;
    String balance;

    public List<String> communicateWithServer(List<String> data)
    {
        System.out.println(data);
        List<String> serverData = new ArrayList<String>();
        //login test
        serverData.addAll(Arrays.asList("0", "a", "192847"));
        return serverData;
    }

    public int login(String login, String password)
    {   
        int errorCode = -1;

        if (!checkData.checkLoginData(login, password))
        {
            errorCode = -2;
            return errorCode;
        }

        //Putting everything in to a list S
        sendingData.addAll(Arrays.asList(login, password));

        //encoding the list S of data
        //TO DO

        //checking whether new thread can be created
        //TO DO

        //new thread creating
        //TO DO

        // sending and receiving data to/from main server, interpreting received data all in thread
        receivedData = communicateWithServer(sendingData);

        //decoding data
        //TO DO

        System.out.println(receivedData);

        if(receivedData.get(0).equals("0"))
        {
            if (receivedData.get(1).equals("c"))// ok and client
            {
                balance = receivedData.get(2);
                errorCode = 0;
            }
            else if (receivedData.get(1).equals("a"))// ok and admin
                errorCode = 1;

            userId = receivedData.get(2);
        }
        else if(receivedData.get(0).equals("1"))// sht wrong
            errorCode=-2;

        //end thread

        //can I return sth inside a thread or better outside??
        return errorCode;
    }

    public int register(String name, String lastName, String pesel, String city, String street, String zipCode, String idNumber, String phoneNum, String email)
    {
        int errorCode = -1;

        if (!checkData.checkPersonalData(name, lastName, pesel, city, street, zipCode, idNumber, phoneNum, email))
        {
            errorCode = -3;
            return errorCode;
        }

        //Putting everything in to a list S
        sendingData.addAll(Arrays.asList(name, lastName, pesel, city, street, zipCode, idNumber, phoneNum, email));

        //encoding the list S of data
        //TO DO

        //checking whether new thread can be created
        //TO DO

        //new thread creating
        //TO DO

        // sending and receiving data to/from main server, interpreting received data all in thread
        receivedData = communicateWithServer(sendingData);

        System.out.println(receivedData);

        if(receivedData.get(0).equals("0"))
            errorCode = 0;
        else if(receivedData.get(0).equals("1"))
            errorCode=-2;

        //end thread

        //can I return sth inside a thread or better outside??
        return errorCode; // only to tests
    }
}
