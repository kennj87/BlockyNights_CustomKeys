package customkeys.blockynights;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {
	
	Main main;
	ItemCreate itemcreate;
	MySQL mysql;
	public Commands(Main main, ItemCreate itemcreate,MySQL mysql) { 
		this.main = main;
		this.itemcreate = itemcreate; 
		this.mysql = mysql;
	}


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (player.hasPermission("customkeys.admin")) {
			if (args.length == 2 && cmd.getName().equalsIgnoreCase("ck") && (sender instanceof Player)) {
				if (main.getConfig().get("Disguise."+args[0]) != null) {
					itemcreate.createItems(player,args[0],Integer.parseInt(args[1]));
				} else { player.sendMessage("ups that disguise does not work yet"); }
			}
			if (cmd.getName().equalsIgnoreCase("ck") && args[0].equalsIgnoreCase("set") && (sender instanceof Player) && (args.length == 3)) {
				main.getConfig().set("Disguise."+args[1], args[2]);
				player.sendMessage("set: "+args[1]+" With perm: "+args[2]);
				main.saveConfig();
				return true;
			}
		}
		return true;
	}
}
