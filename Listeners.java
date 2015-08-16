package customkeys.blockynights;


import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;



public class Listeners implements Listener {
	
	HandleToken handletoken;
	
	public Listeners(HandleToken instance) {
		handletoken = instance;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
	    if(event.getRightClicked().getType() != EntityType.VILLAGER) return;
	    Villager v = (Villager) event.getRightClicked();
	    String vname = v.getCustomName();
	    if (vname != null) {
		    if (vname.equalsIgnoreCase("Token Redeemer")) {
		    	ItemStack handitem = event.getPlayer().getItemInHand();
			    event.setCancelled(true);
		    	if (handitem.getType() == Material.NAME_TAG && handitem.getItemMeta().getLore() != null) {
			    	List<String> Lore = handitem.getItemMeta().getLore();
			    	String[] array = Lore.toArray(new String[Lore.size()]);
			    	array[1] = array[1].replace("§6","");
			    	array[2] = array[2].replace("§8","");
			    	String code = array[2].substring(5, 10);
			    	String key = array[2].substring(1, 4);
			    	if (key.equalsIgnoreCase("key")) {
			    	event.getPlayer().getInventory().removeItem(event.getPlayer().getInventory().getItemInHand());
			    	handletoken.tokenHandler(event.getPlayer(),code);
			    	} else { event.getPlayer().sendMessage("§eToken Redeemer says: §3This does not look like a key I can use, move a long!!"); }
		    	} else { event.getPlayer().sendMessage("§eToken Redeemer says: §3This is not a token, move along!"); }
		    }
	    }
	}
}
