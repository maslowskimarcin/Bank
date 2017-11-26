import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;


public class ListenServer
        extends UnicastRemoteObject
{

    protected ListenServer() throws RemoteException {
    }

    public static void main(String args[]) throws RemoteException, SQLException {
//        System.out.println("SERVER START!");
//        try
//        {
//            Server obj1 = new Server();
//            Context context = new InitialContext();
//            context.rebind("rmi:ServerFace", obj1);
//            System.out.println("Wait...");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        //TEST TEST TEST TEST TEST TEST//
        Temp t=new Temp();
        Server server=new Server();
//-------------LOGIN---------------
//        Object data0=t.logto();
//        Object finish0=server.logIn("1",data0);
//        LogFrom logFrom=(LogFrom)finish0;
//        System.out.println(logFrom);
// --------------ADDNEWACCREQ-----------
//        Object data=t.addNewAccountRequest();
//        Object finish=server.requestAddAccount("1",data);
//        String add=(String) finish;
//        System.out.println(add);
        //-------- REQLISTADDACC---------------
//        Object finish2=server.getRequestAddAccount("1");
//        RequestListAddAccount req= (RequestListAddAccount) finish2;
//        System.out.println(req);
        //---------ANSWERADDACCREQ----------
//        Object finish3=server.answerAddAccountReq("1",t.decisionAddAcc());
//        String err3=(String) finish3;
//        System.out.println(err3);
        //-----TRANSFER---------
        Object data4=t.transfer();
        Object finish4=server.transfer("2","123","123",data4);
        String err4=(String)finish4;
        System.out.println(err4);
    }

}
