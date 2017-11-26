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
//-------------LOGIN----------------
        Temp t=new Temp();
//        Object data0=t0.logto();
//        Server temp0=new Server();
//        Object finish0=temp0.logIn("1",data0);
//        LogFrom logFrom=(LogFrom)finish0;
//        System.out.println(logFrom);
// --------------ADDNEWACCREQ-----------
        Object data=t.addNewAccountRequest();
        Server temp=new Server();
        Object finish=temp.requestAddAccount("1",data);
        String add=(String) finish;
        System.out.println(add);
        //-------- REQLISTADDACC---------------
//        Server temp2=new Server();
//        Object finish2=temp2.getRequestAddAccount("1");
//        RequestListAddAccount req= (RequestListAddAccount) finish2;
//        System.out.println(req);
        //---------ANSWERADDACCREQ----------
//        Server temp3=new Server();
//        Object finish3=temp3.answerAddAccountReq("1",t.decisionAddAcc());
//        String err3=(String) finish3;
//        System.out.println(err3);
    }

}
