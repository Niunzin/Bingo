package protocol;

import threads.ClientThread;

public class Player {
	private String name;
	private String email;
	private int winsCount;
	private ClientThread clientThread;
	
	public Player(String name, String email, int winsCount, ClientThread cThread)
	{
		this.name = name;
		this.email = email;
		this.winsCount = 0;
		this.clientThread = cThread;
		
		if(winsCount >= 0)
			this.winsCount = winsCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getWinsCount() {
		return winsCount;
	}

	public void setWinsCount(int winsCount) {
		this.winsCount = winsCount;
	}
	
	public void kick()
	{
		this.clientThread.sendPacket("expulso");
	}
	
	public void setCartela(Cartela cartela)
	{
		
	}
	
	public void sendMessage(String message)
	{
		
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", email=" + email + ", winsCount=" + winsCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + winsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (winsCount != other.winsCount)
			return false;
		return true;
	}
	
	
	
}
