package customkeys.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {

	private static Plugin plugin;

	
	public void onEnable()  {
        plugin = this;
        registerEvents(this, new Listeners(new HandleToken(this, new MySQL())));
        getCommand("ck").setExecutor(new Commands(this,new ItemCreate(this,new MySQL()),new MySQL()));
        this.saveDefaultConfig();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                MySQL.updateExpiredToken();
            }
        }, 0L, 72000L);
	}
	
	public void onDisable() {
	}
	
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
	
    public static Plugin getPlugin() {
        return plugin;
    }
}

