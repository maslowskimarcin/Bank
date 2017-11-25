package BaseServer;

import Test.DataBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BaseServerImpl
	extends UnicastRemoteObject
	implements BaseServerFace
{
	// This is just for test!!!
	DataBase dataBase;

	// This collection contain current user session.
	private static Map<String, ClientData> clientDataMap;

	public BaseServerImpl() throws RemoteException
	{
		dataBase = new DataBase();
		clientDataMap = new HashMap<String, ClientData>();
	}

	@Override
	public Object logIn(String name, String password, Object data) throws RemoteException
	{
		if(clientDataMap.containsKey(name))
		{
			// There can be return error key
			return false;
		}
		else
		{
			synchronized (clientDataMap)
			{
				if(dataBase.CheckLogin(name, password))
				{
					synchronized (dataBase)
					{
						System.out.println("New client login: " + name);
						clientDataMap.put(name, new ClientData(name, data));

						return true;
					}
				}
				else
				{
					// There can be return error key
					return false;
				}
			}
		}
	}

	@Override
	public Object logOut(String name) throws RemoteException
	{
		if(!clientDataMap.containsKey(name))
		{
			// There can be return error key
			return false;
		}
		else
		{
			System.out.println("Client logout: " + name);
			clientDataMap.remove(name);

			return false;
		}
	}

	@Override
	public Object requestAddAccount(String login, String password, Object data) throws RemoteException
	{
		synchronized (dataBase)
		{
			if (dataBase.AddAccount(login, password, data))
			{
				return true;
			} else
			{
				return false;
			}
		}
	}
}
