package ch.dempsey.pointsapi.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.dempsey.pointsapi.api.API;

public class Join implements Listener{

	private API api;
	
	public Join(API api) {
		this.api = api;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		api.registerPlayer(e.getPlayer());
	}
	
}
