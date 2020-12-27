package ch.dempsey.pointsapi;

import org.bukkit.plugin.java.JavaPlugin;

import ch.dempsey.pointsapi.api.API;
import ch.dempsey.pointsapi.event.Join;

public class Main extends JavaPlugin{

	private static API api;
	
	@Override
	public void onEnable() {
		getLogger().info("Loaded version: " + getDescription().getVersion());
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		api = new API(getConfig().getString("host"), getConfig().getInt("port"), getConfig().getString("username"), getConfig().getString("password"), getConfig().getString("database"));
		
		getServer().getPluginManager().registerEvents(new Join(api), this);
	}
	
	public static API getAPIInstance() {
		return api;
	}
	
}
