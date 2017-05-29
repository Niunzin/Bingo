package daos;

import java.sql.SQLException;
import java.util.Date;

import db.DB;
import protocol.Player;

public class Players {
	
	public static boolean exists(String email)
	{
		boolean result = false;
		
		try
		{
			String sqlQuery = "";
			
			DB.command.prepareStatement(sqlQuery);
			
			
		} catch(SQLException e)
		{
			System.err.println(e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	public static void register(Player player, String password) throws Exception
	{
		if(player == null)
			throw new Exception("Falha ao registrar usuário. (Player equals null)");
		
		if(password.length() != 32) // nao foi criptografada
			throw new Exception("Tentativa de registrar usuário sem senha segura.");
		
		try
		{
			String sqlQuery = "INSERT INTO players "
					+ "(email, name, senha, registerDate, monthlyWins) "
					+ "VALUES (?, ?, ?, CURDATE(), 0)";
			
			DB.command.prepareStatement(sqlQuery);
			DB.command.setString(1, player.getEmail());
			DB.command.setString(2, player.getName());
			DB.command.setString(3, password);
			DB.command.executeUpdate();
			DB.command.commit();
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new Exception("Falha ao registrar usuário. (SQLException)");
		}
	}
	
}
