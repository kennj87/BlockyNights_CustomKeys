package customkeys.blockynights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MySQL {
	

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://path";
	static final String USER = "user";
	static final String PASS = "pass";
	
	  private static Connection connect = null;
	  private static PreparedStatement preparedStatement = null;
	  private static ResultSet resultSet = null;
	  
	  public void createToken(String name,String perm, Long time,String code) {
	   try{
		      Class.forName("com.mysql.jdbc.Driver");
		      connect = DriverManager.getConnection(DB_URL,USER,PASS);
		      preparedStatement = connect.prepareStatement("insert into tokens values (DEFAULT,?, ?, ?, ?, ?, ?)");
		      preparedStatement.setString(1, name);
		      preparedStatement.setString(2, perm);
		      preparedStatement.setLong(3, time);
		      preparedStatement.setString(4, code);
		      preparedStatement.setInt(5, 0);
		      preparedStatement.setInt(6, 0);
		      preparedStatement.executeUpdate();
		      preparedStatement.close();
		      connect.close();

	   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
		finally{try{if(preparedStatement!=null)preparedStatement.close();}catch(SQLException se2){}try{if(connect!=null)connect.close();}catch(SQLException se){se.printStackTrace();}}
	  }
	  
		public void validateToken(Player player,String code) {
			   try{
				      Class.forName("com.mysql.jdbc.Driver");
				      connect = DriverManager.getConnection(DB_URL,USER,PASS);
				      preparedStatement = connect
				              .prepareStatement("SELECT updated,perm FROM tokens WHERE code=?;");
				      preparedStatement.setString(1, code);
				      resultSet = preparedStatement.executeQuery();
				      resultSet.next();
				      int updated = resultSet.getInt("updated");
				      String perm = resultSet.getString("perm");
				      if (updated == 0) {
				    	  player.sendMessage("§eToken Redeemer says: §3Token Valid! Congratulations!");
				    	  player.sendMessage("§eToken Redeemer says: §3To use your new disguise: type §b/d§e -§3 for a list. To undisguise type: §b/u");
					      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perm player "+player.getDisplayName()+" set "+perm);
				    	  updateValidToken(code,player.getName());
				      }
				      preparedStatement.close();
				      connect.close();

			   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
				finally{try{if(preparedStatement!=null)preparedStatement.close();}catch(SQLException se2){}try{if(connect!=null)connect.close();}catch(SQLException se){se.printStackTrace();}}
			  }
		
		public void updateValidToken(String code,String player) {
			   try{
				      Class.forName("com.mysql.jdbc.Driver");
				      connect = DriverManager.getConnection(DB_URL,USER,PASS);
				      long unixtime = (System.currentTimeMillis() / 1000L)+604800L;
				      preparedStatement = connect
				              .prepareStatement("UPDATE tokens SET updated='1',time='"+unixtime+"',name='"+player+"' WHERE code=?;");
				      preparedStatement.setString(1, code);
				      preparedStatement.executeUpdate();
				      preparedStatement.close();
				      connect.close();

			   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
				finally{try{if(preparedStatement!=null)preparedStatement.close();}catch(SQLException se2){}try{if(connect!=null)connect.close();}catch(SQLException se){se.printStackTrace();}}
			  }
		
		static public void updateExpiredToken() {
		    long unixtime = System.currentTimeMillis() / 1000L;
		    try{
			      Class.forName("com.mysql.jdbc.Driver");
			      connect = DriverManager.getConnection(DB_URL,USER,PASS);
			      preparedStatement = connect
			              .prepareStatement("SELECT time,ID,name,perm FROM tokens WHERE time != 0;");
			      resultSet = preparedStatement.executeQuery();
				      while (resultSet.next()) {
				      Long time = resultSet.getLong("time");
				      int id = resultSet.getInt("id");
				      String name = resultSet.getString("name");
				      String perm = resultSet.getString("perm");
				      if (time < unixtime) {
				    	  removeExpiredToken(id,name,perm);
				      }
			      }
			      preparedStatement.close();
			      connect.close();

		   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
			finally{try{if(preparedStatement!=null)preparedStatement.close();}catch(SQLException se2){}try{if(connect!=null)connect.close();}catch(SQLException se){se.printStackTrace();}}
		  }
		
		static public void removeExpiredToken(int id,String name,String perm) {
			   try{
				      Class.forName("com.mysql.jdbc.Driver");
				      connect = DriverManager.getConnection(DB_URL,USER,PASS);
				      preparedStatement = connect
				              .prepareStatement("DELETE FROM tokens WHERE ID=?;");
				      preparedStatement.setInt(1, id);
				      preparedStatement.executeUpdate();
				      preparedStatement.close();
				      connect.close();
				      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perm player "+name+" unset "+perm);

			   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
				finally{try{if(preparedStatement!=null)preparedStatement.close();}catch(SQLException se2){}try{if(connect!=null)connect.close();}catch(SQLException se){se.printStackTrace();}}
			  }
}
