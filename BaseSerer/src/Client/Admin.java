package Client;

import Base.*;

import java.util.ArrayList;
import java.util.List;

public class Admin
{
    private CheckData checkData = new CheckData();
    String userId;
    BaseServerFace server;

    public void logOut(){

        try
        {
            server.LogOut(userId);
        }
        catch (Exception e)
        {
            System.out.println("err Log out");
        }
    }

    /* errocode:
    * 0 everything ok
    * 1 sth wrong with base server
    * 2 there is no such client
    * 3 unsuitable data in field
    * */
    public String addFunds(String login, String amount, String amountAfterComma)
    {
        Funds toSend = new Funds();
        String receivedErr;

        if (!checkData.checkIfOnlyNum(login) || !checkData.checkIfOnlyNum(amount) || amountAfterComma.length() != 2 || !checkData.checkIfOnlyNum(amountAfterComma))
            return "3";

        toSend.login = login;
        toSend.amount = amount + "." + amountAfterComma;

        try
        {
            receivedErr = server.addFunds(userId, toSend);
        }
        catch (Exception e)
        {
            return "-1";
        }

        if(receivedErr == null)
            return "1";

        return receivedErr;

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
    * errorCode
    * 0 ok
    * 1 sth wrong with data base
    * */
    public List<AddAccountRequest> getListAddAccReq()
    {
        RequestListAddAccount received;

        try
        {
            received = server.getRequestAddAccount(userId);
        }
        catch (Exception e)
        {
            return null;
        }

        if (received.error.equals("1"))
            return null;

        return  received.data;
    }

    /*
    * errorCode
    * -1 sth wrong with sending req
    * 0 ok
    * 1 sth wrong with data base
    * */
    public String sendAddAccDecision(String idReq, String decision, PersonalData data)
    {
        AddAccReqDecision toSend = new AddAccReqDecision();
        String receivedErr;

        toSend.id_req = idReq;
        toSend.decision = decision;
        toSend.personalData = data;

        try
        {
            receivedErr = server.answerAddAccountReq(userId, toSend);
        }
        catch (Exception e)
        {
            return "-1";
        }

        return receivedErr;
    }

    /*
    * errorCode
    * 0 ok
    * 1 sth wrong with data base
    * */
    public List<AddAccountRequest> getListChangePersonalDataReq()
    {
        RequestListAddAccount received;

        try
        {
            received = server.getRequestChangePersonalData(userId);

        }
        catch (Exception e)
        {
            return null;
        }

        if (received.error.equals("1"))
            return null;

        return  received.data;
    }

    /*
    * errorCode:
    * -1 sth wrong with sending req
    * 0 ok
    * 1 sth wrong with data base
    * */
    public String sendChangeDataDecision(String idReq, String decision, PersonalData data)
    {
        AddAccReqDecision toSend = new AddAccReqDecision();
        String receivedErr;

        toSend.id_req = idReq;
        toSend.decision = decision;
        toSend.personalData = data;

        try
        {
            receivedErr = server.answerChangePersonalDataReq(userId, toSend);
        }
        catch (Exception e)
        {
            return "-1";
        }

        return receivedErr;
    }

    /*
    * erroCode:
    * 0 ok
    * 1 sth wrong with data base
    * */
    public List<LoanReq> getListLoanReq()
    {
        ListLoanReq received;

        try
        {
            received = server.getRequestLoan(userId);
        }
        catch (Exception e)
        {
            return null;
        }

        if (received.error.equals("1"))
            return null;

        return  received.loanList;
    }

    /*
    * errorCode:
    * -1 sth wrong with sending req
    * 0 ok
    * 1 sth wrong with data base
    * */
    public String sendLoanReqDecision(String idReq, String decision)
    {
        LoanDecision toSend = new LoanDecision();
        String receivedErr;

        toSend.id_req = idReq;
        toSend.decision = decision;

        try
        {
            receivedErr = server.answerLoanReq(userId, toSend);
        }
        catch (Exception e)
        {
            return "-1";
        }

        return receivedErr;
    }

    /*
    * errorCode:
    * -2 unsuitable data in fields or clientNo and repeatedClientNo are not equal
    * -1 sth wrong with base server
    * 0 ok
    * 1 sth wrong with database
    * 2 there is no such clientNo
    * */
    public String unlockAcc(String clientNo, String repeatClientNo)
    {
        String received;

        if(!checkData.checkIfOnlyNum(clientNo) || !checkData.checkIfOnlyNum(repeatClientNo) || !clientNo.equals(repeatClientNo))
           return "-2";

        try
        {
            received = server.unlockAcc(userId, clientNo);
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
    * errorCode:
    * -2 unsuitable data in fields or clientNo and repeatedClientNo are not equal
    * -1 sth wrong with base server
    * 0 ok
    * 1 sth wrong with database
    * 2 there is no such clientNo
    * 3 client has active investment
    * 4 client has active loan
    * */
    public String deleteAcc(String clientNo, String repeatClientNo)
    {
        String received;

        if(!checkData.checkIfOnlyNum(clientNo) || !checkData.checkIfOnlyNum(repeatClientNo) || !clientNo.equals(repeatClientNo))
            return "-2";

        try
        {
            received = server.deleteAcc(userId, clientNo);
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
}
