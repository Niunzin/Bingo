package server;

import java.util.Random;

import daos.Players;
import protocol.GFSecurity;
import protocol.Player;
import threads.ServerThread;

public class Server {
	private static final int LISTEN_PORT	= 8090;
	
	public static void main(String args[])
	{
		try
		{
			Players.register(new Player("Gustavo Ifanger", "gustavoaifanger@gmail.com", 0, null), GFSecurity.passwordHash("123"));
			
			Thread serverThread =
					new ServerThread(Server.LISTEN_PORT);
			
			serverThread.start();
		} catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
