package ch.dempsey.pointsapi.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

public class API {

	private String host, username, password, database, uri;
	private int port;
	
	public API(String host, int port, String username, String password, String database) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.database = database;
		this.port = port;
		
		this.uri = "jdbc:mysql://"+host+":"+String.valueOf(port)+"/"+database;
	}
	
	
	public void registerPlayer(Player player) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(uri, username, password);
			String sql = "SELECT * FROM players WHERE uuid='"+player.getUniqueId().toString()+"'";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				String sqli = "INSERT INTO players (id, uuid, points) VALUES (NULL, '"+player.getUniqueId().toString()+"', 0)";
				PreparedStatement sts = conn.prepareStatement(sqli);
				if(sts.execute()) System.out.println("Successfully registered new player: " + player.getName());
				sts.close();
			}
			
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getPoints(Player player) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(uri, username, password);
			String sql = "SELECT points FROM players WHERE uuid='"+player.getUniqueId().toString()+"'";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				conn.close();
				return rs.getInt("points");
			}else {
				conn.close();
				return 6969696;
			}
		}catch(Exception e) {
			return 6969696;
		}
	}
	
	
	private void setPoints(Player player, int amnt) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(uri, username, password);
			String sql = "UPDATE players SET points="+String.valueOf(amnt)+" WHERE uuid='"+player.getUniqueId().toString()+"'";
			PreparedStatement st = conn.prepareStatement(sql);
			st.execute();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void givePoints(Player player, int amnt) {
		
		int current = getPoints(player);
		int nw = current+amnt;
		setPoints(player,amnt);		
	}
	
	
	public void takePoints(Player player, int amnt) {
		int current = getPoints(player);
		int nw = current-amnt;
		setPoints(player,amnt);
	}
	
}
