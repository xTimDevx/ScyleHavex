package me.xtimdevx.scylehavex.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.xtimdevx.scylehavex.Main;
import me.xtimdevx.scylehavex.User;
import me.xtimdevx.scylehavex.User.Rank;

public class chatEvents implements Listener{
	
	public chatEvents(Main main) {
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = (Player) e.getPlayer();
		User user = User.get(p);
		e.setCancelled(true);
		if(user.getRank() == Rank.PLAYER) {
			Bukkit.broadcastMessage("§7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.ADMIN) {
			Bukkit.broadcastMessage("§8[§cAdmin§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.OWNER) {
			Bukkit.broadcastMessage("§8[§4Owner§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.DEV) {
			Bukkit.broadcastMessage("§8[§cD§4e§cv§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.JRMOD) {
			Bukkit.broadcastMessage("§8[§aJr.Mod§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.MOD) {
			Bukkit.broadcastMessage("§8[§aMod§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
		if(user.getRank() == Rank.VIP) {
			Bukkit.broadcastMessage("§8[§5Vip§8] §7" + p.getName() + " §8» §7" + e.getMessage());
			return;
		}
	}

}
