package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.SocketHandler;

public class ServerThread extends Thread {
	private static final int MAX_CLIENT_CONNECTION	= 10;
	private static final int GAME_START_TIME		= 10;
	
	protected int 						serverPort		= 8090;
	protected ServerSocket				serverSocket	= null;
	protected boolean 					serverStopped	= false;
	protected Thread					runningThread	= null;
	protected ArrayList<ClientThread>	clientList		= null;
	protected boolean					gameStarted		= false;
	protected int						curStartTime	= 0;
	
	public ServerThread(int port) {
		this.serverPort = port;
		this.clientList = new ArrayList<ClientThread>();
	}

	@Override
	public void run()
	{
		synchronized(this)
		{ this.runningThread = Thread.currentThread(); }
		
		// Inicializa o ServerSocket
		this.startServer();
		
		// Executa operações enquanto o servidor estiver vivo
		while(!serverStopped)
		{
			// Inicialização de um cliente
			Socket clientSocket = null;
			try
			{
				// Recebe um novo cliente
				clientSocket = this.serverSocket.accept();
				System.out.println("Nova conexão recebida.");
			} catch(IOException e)
			{
				// Verifica se o servidor parou
				if(isStopped())
				{
					System.out.println("Servidor parado.");
					return;
				}
				
				throw new RuntimeException("Erro ao aceitar conexão de cliente.", e);
			}
			
			// Se o cliente for genuíno, executa a Thread do mesmo
			if(clientSocket != null)
			{
				SocketHandler handler = new SocketHandler(clientSocket);
				
				ClientThread clientThread =
						new ClientThread(clientSocket, this.clientList, handler);
				
				// Adiciona o cliente na lista de clientes
				this.addClient(clientThread);
				clientThread.start();
			}
		}
		
		System.out.println("Servidor parou.");
	}
	
	private synchronized boolean isStopped()
	{
		return this.serverStopped;
	}
	
	public synchronized void stopServer()
	{
		this.serverStopped = true;
		
		try
		{
			this.serverSocket.close();
		} catch(IOException e)
		{
			throw new RuntimeException("Falha ao encerrar servidor.", e);
		}
	}
	
	private void startServer()
	{
		try
		{
			this.serverSocket = new ServerSocket(this.serverPort);
			System.out.println("Servidor iniciado na porta " + this.serverPort + ".");
		} catch(IOException e)
		{
			throw new RuntimeException("Não foi possível iniciar o servidor na porta " + this.serverPort + ".", e);
		}
	}
	
	private synchronized boolean isGameStarted()
	{
		return this.gameStarted;
	}
	
	private synchronized void startCountDown()
	{
		this.curStartTime = 0;
		int gstMs = (ServerThread.GAME_START_TIME * 60 * 1000);
		System.out.println("Aguardando " + ServerThread.GAME_START_TIME + " minutos para início do jogo.");
		
		try {
			Thread.sleep(gstMs);
			startGame();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void startGame()
	{
		this.gameStarted = true;
		System.out.println("Jogo iniciado.");
	}
	
	private synchronized boolean isServerFull()
	{
		return (this.clientList.size() == ServerThread.MAX_CLIENT_CONNECTION);
	}
	
	private synchronized void addClient(ClientThread client)
	{
		this.clientList.add(client);
	}
	
	private synchronized int getClientCount()
	{
		return this.clientList.size();
	}

}
