package BaseServer;

import Base.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;

public class BaseServerImpl
	extends UnicastRemoteObject
	implements BaseServerFace
{
	private static final long serialVersionUID = 1L;

	// This collection contain current user session.
	//private static Map<String, ClientData> clientDataMap;

	BaseServerFace computingSever;

	public BaseServerImpl(BaseServerFace serverFace) throws RemoteException
	{
		this.computingSever = serverFace;
	}

	@Override
	public synchronized LogFrom logIn(String login, LogTo data) throws RemoteException
	{
		System.out.println("LOGIN");
		return computingSever.logIn(login, data);
}

	@Override
	public String restartPassword(String login) throws RemoteException
	{
//		System.out.println("RESTARTPASSWORD");
//		return computingSever.restartPassword(login);
		return null;
	}

	@Override
	public String transfer( Transfer data) throws RemoteException
	{
		System.out.println("TRANSFER");
		return computingSever.transfer(data);
	}

	@Override
	public String changePassword(LogTo data) throws RemoteException
	{
		System.out.println("changepassword");
		return computingSever.changePassword(data);

	}

	@Override
	public Object makeDeposit(String login, String accTo, Object data) throws RemoteException
	{
		return null;
	}

	@Override
	public String addFunds(Funds data) throws RemoteException
	{
//		System.out.println("addFunds");
//		return computingSever.addFunds(data);
		return null;
	}

	@Override
	public String requestAddAccount(String login, PersonalData data) throws RemoteException
	{
		System.out.println("requestaddaccount");
		return  computingSever.requestAddAccount(login, data);

	}

	@Override
	public String requestChangePersonalData(String login, PersonalData data) throws RemoteException
	{
//		System.out.println("request chane persoanl data");
//		return  computingSever.requestChangePersonalData(login, data);
		return null;
	}

	@Override
	public Object requestLoan(String login, Object data) throws RemoteException
	{
		return null;
	}

	@Override
	public Object requestInvestment(String login, Object data) throws RemoteException
	{
		return null;
	}

	@Override
	public String answerAddAccountReq(String login, AddAccReqDecision data) throws RemoteException
	{
		System.out.println("ANSWERADDACCOUNTREQ");
		return computingSever.answerAddAccountReq(login, data);

	}

	@Override
	public String answerChangePersonalDataReq(String login,AddAccReqDecision data) throws RemoteException
	{
		return null;
	}

	@Override
	public Object answerLoanReq(String login, String answer, String accTo, Object data) throws RemoteException
	{
		return null;
	}

	@Override
	public Object answerInvestmentReq(String login, String answer, String accTo, Object data) throws RemoteException
	{
		return null;
	}

	@Override
	public String getBalance(String login) throws RemoteException
	{
		System.out.println("GETBALANCE");
		return computingSever.getBalance(login);
	}

	@Override
	public TransferData getTransferHistory(TransferHistory data) throws RemoteException
	{
		return null;
	}

	@Override
	public PersonalData getPersonalData(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public Object getLoanHistory(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public Object getInvestmentHistory(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public RequestListAddAccount getRequestAddAccount(String login) throws RemoteException
	{
		System.out.println("Zakladanie konta(wniosek)");
		return computingSever.getRequestAddAccount(login);
	}

	@Override
	public  RequestListAddAccount getRequestChangePersonalData(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public Object getRequestLoan(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public Object getRequestInvestment(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public Object LogOut(String login) throws RemoteException
	{
		return null;
	}

	@Override
	public String getDescription(String text) throws RemoteException
	{
		return null;
	}
}
