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
	 * Verifica se um jogador já existe no banco de dados.
	 * @param email Email do jogador a ser verificado.
	 * @return Verdadeiro caso o jogador exista.
	 * @throws Exception Caso não seja possível realizar a query sql.
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
			throw new Exception("Falha ao verificar usuário.");
		}
		
		return ret;
	}
	
	/**
	 * Cadastra um novo jogador no banco de dados.
	 * @param player Jogador a ser cadastrado.
	 * @param password Senha do jogador já com a criptografia.
	 * @throws Exception Caso a senha não esteja criptografada ou já exista um usuário com o mesmo e-mail.
	 */
	public static void register(Player player) throws Exception
	{
		if(player == null)
			throw new Exception("Falha ao registrar usuário. (Player equals null)");
		
		if(player.getPassword().length() != 32)
			throw new Exception("Tentativa de registrar usuário sem senha segura.");
		
		if(playerExists(player.getEmail()))
			throw new Exception("Já existe um usuário com esse e-mail.");
		
		try
		{
			String sqlQuery = "INSERT INTO players "
					+ "(email, name, password, registerDate, monthlyWins) "
					+ "VALUES (?, ?, ?, CURDATE(), 0)";
			
			DB.command.prepareStatement(sqlQuery);
			DB.command.setString(1, player.getEmail());
			DB.command.setString(2, player.getName());
			DB.command.setString(3, player.getPassword());
			DB.command.executeUpdate();
			DB.command.commit();
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new Exception("Falha ao registrar usuário. (SQLException)");
		}
	}
	
	/**
	 * Obtem um jogador através do email.
	 * @param email Email do jogador a ser obtido.
	 * @return Objeto do jogador caso o mesmo exista, se não, retorna nulo.
	 * @throws Exception Caso não exista jogador com o e-mail cadastrado.
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
				throw new Exception("Não existe usuário cadastrado com esse e-mail.");
			
			player = new Player();
			player.setEmail(result.getString("email"));
			player.setName(result.getString("name"));
			player.setPassword(result.getString("password"));
			player.setWinsCount(result.getInt("monthlyWins"));
		} catch(SQLException e)
		{
			throw new Exception("Falha ao obter jogador.");
		}
		
		return player;
	}
}
