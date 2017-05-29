package daos;

import java.sql.SQLException;

import db.DB;
import db.GFResultSet;
import protocol.Player;

/**
 * Classe para gerenciar jogadores no banco de dados.
 * @author Gustavo Ifanger
 * 
 */
public class Players {
	
	/**
	 * Verifica se um jogador j� existe no banco de dados.
	 * @param email Email do jogador a ser verificado.
	 * @return Verdadeiro caso o jogador exista.
	 * @throws Exception Caso n�o seja poss�vel realizar a query sql.
	 */
	private static boolean playerExists(String email) throws Exception
	{
		boolean ret = false;
		
		try
		{
			String sqlQuery = "SELECT * FROM players WHERE email = ?";
			
			DB.command.prepareStatement(sqlQuery);
			DB.command.setString(1, email);
			
			GFResultSet result = (GFResultSet) DB.command.executeQuery();
			ret = result.first();
		} catch(SQLException e)
		{
			System.out.println(e.getMessage());
			throw new Exception("Falha ao verificar usu�rio.");
		}
		
		return ret;
	}
	
	/**
	 * Cadastra um novo jogador no banco de dados.
	 * @param player Jogador a ser cadastrado.
	 * @param password Senha do jogador j� com a criptografia.
	 * @throws Exception Caso a senha n�o esteja criptografada ou j� exista um usu�rio com o mesmo e-mail.
	 */
	public static void register(Player player, String password) throws Exception
	{
		if(player == null)
			throw new Exception("Falha ao registrar usu�rio. (Player equals null)");
		
		if(password.length() != 32)
			throw new Exception("Tentativa de registrar usu�rio sem senha segura.");
		
		if(playerExists(player.getEmail()))
			throw new Exception("J� existe um usu�rio com esse e-mail.");
		
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
			throw new Exception("Falha ao registrar usu�rio. (SQLException)");
		}
	}
	
	/**
	 * Obtem um jogador atrav�s do email.
	 * @param email Email do jogador a ser obtido.
	 * @return Objeto do jogador caso o mesmo exista, se n�o, retorna nulo.
	 * @throws Exception Caso n�o exista jogador com o e-mail cadastrado.
	 */
	public static Player getPlayer(String email) throws Exception
	{
		Player player = null;
		
		try
		{
			String sqlQuery = "SELECT * FROM players WHERE email = ?";
			
			DB.command.prepareStatement(sqlQuery);
			DB.command.setString(1, email);
			
			GFResultSet result = (GFResultSet)DB.command.executeQuery();
			
			if(result.first())
				throw new Exception("N�o existe usu�rio cadastrado com esse e-mail.");
			
			player = new Player(
					result.getString("email"),
					result.getString("name"));
			player.setWinsCount(result.getInt("monthlyWins"));
		} catch(SQLException e)
		{
			throw new Exception("Falha ao obter jogador.");
		}
		
		return player;
	}
}
