package BaseServer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class BaseServer
{
	public static void main(String args[])
	{
		System.out.println("Server start!");

		try
		{
			BaseServerImpl server = new BaseServerImpl();

			Context contextServer = new InitialContext();
			contextServer.rebind("rmi:ServerFace", server);
			System.out.println("Server is ready!");
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch (NamingException e)
		{
			System.out.println(e);
			e.printStackTrace();
		}

	}
}
