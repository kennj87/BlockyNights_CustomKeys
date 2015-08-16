package customkeys.blockynights;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemCreate {

	Main main;
	MySQL mysql;
	public ItemCreate(Main main,MySQL mysql) { 
		this.main = main;
 		this.mysql = mysql;
	}
	
	public void createItems(Player player,String type,int time) {
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§5 "+type+" Key");
		List<String> loreList = new ArrayList<String>();
		loreList.add("§6 Unlocks "+type+" kit for:");
		loreList.add("§6 "+time+" week(s)");
		String rnd = randomString();
		loreList.add("§8 Key "+rnd);
		im.setLore(loreList);
		item.setItemMeta(im);
		player.getInventory().addItem(item);
		Long unixtime = 0L;
		mysql.createToken(player.getName(),main.getConfig().get("Disguise."+type).toString(),unixtime,rnd);
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	String randomString() {
	   StringBuilder sb = new StringBuilder(5);
	   for( int i = 0; i < 5; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
}
