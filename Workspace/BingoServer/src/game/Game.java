package game;

import java.util.ArrayList;

import protocol.Cartela;
import protocol.Player;
import threads.ServerThread;

public class Game extends Thread {
	public static final int PLAYER_LIMIT	= 10;
	public static final int SORT_DELAY		= 5;
	public static final int START_TIME		= 10;
	
	private ArrayList<Player> playerList;
	private ArrayList<Integer> drawnNumbers = new ArrayList<Integer>();
	private int currentCountDownTime = 0;
	private boolean started = false;
	
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
		
		this.start();
	}
	
	public synchronized void start()
	{
		if(this.started)
		{
			System.out.println("Tentativa de iniciar um jogo já iniciado.");
			return;
		}
		
		this.started = true;
	}
	
	public synchronized void end()
	{
		this.started = false;
	}
	
	public synchronized boolean isFull()
	{
		return this.playerList.size() >= Game.PLAYER_LIMIT;
	}
	
	public synchronized boolean isGameStarted()
	{
		return this.started;
	}
	
	public synchronized void onPlayerJoined(PlayerHandler player)
	{
		if(this.isGameStarted())
		{
			//player.sendMessage("O jogo já começou!");
			// Expulsa o jogador da sala
			player.kick();
			return;
		}
		
		// Gera uma cartela para o jogador
		//player.setCartela(new Cartela());
	}
	
	public synchronized void onPlayerNumberPick(PlayerHandler player, int number)
	{
		
	}
	
	public synchronized void onDrawnNumber(int number)
	{
		this.drawnNumbers.add(number);
	}
}
