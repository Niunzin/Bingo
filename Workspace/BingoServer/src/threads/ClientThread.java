package threads;

import java.net.Socket;
import java.util.ArrayList;

import server.SocketHandler;

public class ClientThread extends Thread {
	protected Socket 					socket 			= null;
	protected ArrayList<ClientThread>	clientList		= null;
	protected SocketHandler				handler			= null;
	protected BufferedReader			reader			= null;
	
	public ClientThread(Socket socket, ArrayList<ClientThread> clientList, SocketHandler handler)
	{
		this.socket		= socket;
		this.clientList	= clientList;
		this.handler	= handler;
		
		InputStream			inStream 		= this.socket.getInputStream();
		InputStreamReader	inStreamReader	= new InputStreamReader(inStream);
		this.reader = new BufferedReader(inStreamReader);
	}

	@Override
	public void run()
	{
		for(;;)
		{
			if(this.reader.ready())
			{
				String receivedPacket = this.reader.readLine();
				int packetType = GFProtocol.getPacketType(receivedPacket);
				
				switch(packetType)
				{
					case GFProtocol.PacketType.RANKING: // Solicitação de ranking
						this.handler.sendMessage(String.format(GFProtocol.RANKING_INFORMATION, "{}"));
						break;
					case GFProtocol.PacketType.LOGIN: // Tentativa de login
						String username = "";
						String password = "";
						boolean success = false;
						
						// consulta o banco de dados
						// verifica as credenciais
						this.handler.sendMessage(String.format(GFProtocol.LOGIN_RESPONSE, ((success) ? "T" : "F" )));
						
						// Informa o servidor sobre o login do jogador
						if(success)
							game.onPlayerJoined(player);
						
						break;
					case GFProtocol.PacketType.REGISTER: // Tentativa de registro
						String name, password, email;
						boolean success = false;
						
						// consulta banco de dados
						// verifica credenciais
						
						this.handler.sendMessage(String.format(GFProtocol.REGISTER_RESPONSE, ((success) ? "T" : "F" )));
						
						
						break;
					default: // Pacote desconhecido
						this.handler.sendMessage("MENSAGEM DE EXPULSÃO DEVIDO A PACOTE DESCONHECIDO");
						break;
				}

			}
		}
	}
	
	public void sendPacket(String packet)
	{
		this.handler.sendMessage(packet);
	}

}
