package me.xtimdevx.scylehavex.events;

import java.util.Date;
import java.util.TimeZone;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.xtimdevx.scylehavex.Main;
import me.xtimdevx.scylehavex.User;

public class loginEvents implements Listener{
	
	public loginEvents(Main main) {
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		User user = User.get(player);
		user.getFile().set("username", player.getName());
		user.getFile().set("uuid", player.getUniqueId().toString());
		user.getFile().set("ip", player.getAddress().getAddress().getHostAddress());
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Date date = new Date();
		
		user.getFile().set("lastlogin", date.getTime());
		user.saveFile();
	}

}
