package game;

import protocol.Cartela;
import protocol.GFProtocol;
import protocol.Player;
import threads.ClientThread;

public class PlayerHandler extends Player {
	private Cartela cartela;
	private ClientThread connection;
	
	public PlayerHandler(Player player, ClientThread connection)
	{
		this.setName(player.getName());
		this.setEmail(player.getEmail());
		this.setWinsCount(player.getWinsCount());
		this.setEmail(player.getPassword());
		this.connection = connection;
	}
	
	public Cartela getCartela() {
		return cartela;
	}

	public void setCartela(Cartela cartela) {
		this.cartela = cartela;
	}

	public void kick()
	{
		this.connection.sendPacket(GFProtocol.KICK);
	}
	
	public void disconnect()
	{
		this.connection.disconnect();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}
