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
//        Temp t=new Temp();
//        Object data=t.logto();
//        Server temp=new Server();
//        Object finish=temp.logIn("1",data);
//        LogFrom logFrom=(LogFrom)finish;
//        System.out.println(logFrom)
// --------------ADDNEWACCREQ-----------
        Temp t=new Temp();
        Object data=t.addNewAccountRequest();
        Server temp=new Server();
        Object finish=temp.requestAddAccount("1",data);
        String add=(String) finish;
        System.out.println(add);

        System.out.println("FINISH");
    }

}
