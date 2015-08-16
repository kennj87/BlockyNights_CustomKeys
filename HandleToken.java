package customkeys.blockynights;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class HandleToken {
	
	Main main;
	MySQL mysql;
	
	public HandleToken(Main main,MySQL mysql) {
		this.main = main;
		this.mysql = mysql;
	}
	
	HandleToken handletoken;
	public void tokenHandler(final Player player,final String code) {
	player.sendMessage("§eToken Redeemer says:§3 Checking your Token! Give me just a minute *mumbles*");
	new BukkitRunnable() {
        @Override
        public void run() {
            mysql.validateToken(player, code);
        	}
		}.runTaskLater(main, 150);
	}
	
}
