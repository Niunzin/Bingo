package server;

import threads.ServerThread;

public class Server {
	private static final int LISTEN_PORT	= 8090;
	
	public static void main(String args[])
	{
		ServerThread serverThread =
				new ServerThread(Server.LISTEN_PORT);
		
		serverThread.start();
	}
}
