package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import daos.Players;
import game.Game;
import game.PlayerHandler;
import protocol.GFProtocol;
import protocol.Player;
import protocol.Ranking;
import server.SocketHandler;

public class ClientThread extends Thread {
	protected Socket 					socket 			= null;
	protected ArrayList<ClientThread>	clientList		= null;
	protected SocketHandler				handler			= null;
	protected Game						game			= null;
	protected BufferedReader			reader			= null;
	protected Scanner					input			= null;
	protected Gson						gson			= null;
	
	public ClientThread(Socket socket, ArrayList<ClientThread> clientList, SocketHandler handler, Game game)
	{
		this.socket		= socket;
		this.clientList	= clientList;
		this.handler	= handler;
		this.game		= game;
		this.gson		= new Gson();
		
		InputStream inStream = null;
		try {
			inStream = this.socket.getInputStream();
			this.input = new Scanner(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStreamReader	inStreamReader	= new InputStreamReader(inStream);
		this.reader = new BufferedReader(inStreamReader);
	}

	@Override
	public void run()
	{
		while(this.input.hasNextLine())
		{
			String receivedPacket = this.input.nextLine();
			int packetType = GFProtocol.getPacketType(receivedPacket);
			
			System.out.println("Pacote recebido: " + receivedPacket);
			
			if(packetType == GFProtocol.PacketType.RANKING)
			{
				Ranking ranking = null;
				this.handler.sendMessage(String.format(GFProtocol.RANKING_INFORMATION, gson.toJson(ranking)));
			} else if(packetType == GFProtocol.PacketType.LOGIN)
			{
				Player receivedPlayer = GFProtocol.getPlayerFromLoginPacket(receivedPacket);
				PlayerHandler player = null;
				boolean success = false;
				
				if(receivedPlayer != null)
				{
					try {
						Player dbPlayer = Players.getPlayer(receivedPlayer.getEmail());
						
						if(dbPlayer.getPassword().equals(receivedPlayer.getPassword()))
						{
							player = new PlayerHandler(dbPlayer, this);
							success = true;
						}
					} catch (Exception e) {
						success = false;
					}
				}
				
				this.sendPacket(String.format(GFProtocol.LOGIN_RESPONSE, ((success) ? "T" : "F" )));
				
				if(success && player != null)
				{
					System.out.println("O jogador " + player.getName() + " entrou no jogo.");
					game.onPlayerJoined(player);
				}
			} else if(packetType == GFProtocol.PacketType.REGISTER)
			{
				Player newPlayer = GFProtocol.getPlayerFromRegisterPacket(receivedPacket);
				boolean success = false;
				
				try
				{
					Players.register(newPlayer);
					success = true;
				} catch(Exception e)
				{
					success = false;
				}
				
				this.sendPacket(String.format(GFProtocol.REGISTER_RESPONSE, ((success) ? "T" : "F")));
			} else
			{
				System.out.println("Pacote estranho recebido.");
				this.sendPacket(GFProtocol.KICK);
			}
		}
	}

	public void sendPacket(String packet)
	{
		this.handler.sendMessage(packet);
	}
	
	public void disconnect()
	{
		this.handler.disconnect();
	}

}
