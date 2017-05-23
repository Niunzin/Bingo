package threads;

import java.net.Socket;
import java.util.ArrayList;

import server.SocketHandler;

public class ClientThread extends Thread {
	protected Socket 					socket 			= null;
	protected ArrayList<ClientThread>	clientList		= null;
	protected SocketHandler				handler			= null;
	
	public ClientThread(Socket socket, ArrayList<ClientThread> clientList, SocketHandler handler)
	{
		this.socket		= socket;
		this.clientList	= clientList;
		this.handler	= handler;
	}

	@Override
	public void run() {
		
		this.handler.sendMessage(SocketHandler.MESSAGE_CONNECTED);
		// ENVIAR RANKING POR MENSAGEM

	}

}
