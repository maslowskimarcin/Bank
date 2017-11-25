package BaseServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BaseServerFace
	extends Remote
{
	// @password is only need for test!!!
	Object logIn(String name, String password, Object data) throws RemoteException;
	Object logOut(String name) throws RemoteException;

	Object requestAddAccount(String login, String password, Object data) throws RemoteException;
}
