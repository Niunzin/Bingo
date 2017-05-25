package server;

import java.util.ArrayList;

import protocol.Player;
import threads.ServerThread;

public class Game extends Thread {
	public static final int PLAYER_LIMIT	= 10;
	public static final int SORT_DELAY		= 5;
	public static final int START_TIME		= 10;
	
	private ArrayList<Player> playerList;
	private int currentCountDownTime = 0;
	
	public Game(ServerThread server)
	{
		this.playerList = new ArrayList<Player>();
	}
	
	public synchronized void startCountDown()
	{
		while(currentCountDownTime < START_TIME)
		{
			try {
				Thread.sleep(1000 * 60);
				currentCountDownTime++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void start()
	{
		
	}
	
	public synchronized void end()
	{
		
	}
	
	public synchronized boolean isFull()
	{
		return this.playerList.size() >= Game.PLAYER_LIMIT;
	}
	
	public void onPlayerJoined(Player player)
	{
		
	}
}
