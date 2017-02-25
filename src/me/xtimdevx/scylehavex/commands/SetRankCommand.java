package me.xtimdevx.scylehavex.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.xtimdevx.scylehavex.Main;
import me.xtimdevx.scylehavex.User;
import me.xtimdevx.scylehavex.User.Rank;
import me.xtimdevx.scyleuhc.utils.Prefix;

public class SetRankCommand implements CommandExecutor, TabCompleter{ 
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		User u = User.get(p);
		if (u.getRank() != Rank.OWNER && u.getRank() != Rank.DEV && !p.getName().equalsIgnoreCase("xTimmyDevx")) {
			sender.sendMessage(Prefix.NO_PERM);
			return true;
		}
		
		if (args.length < 2) {
			sender.sendMessage(Prefix.PREFIX + "Usage: /setrank <player> <newrank>");
			return true;
		}
		
		Rank rank;
		
		try {
			rank = Rank.valueOf(args[1].toUpperCase());
		} catch (Exception e) {
			sender.sendMessage(Prefix.PREFIX + args[1] + " is not a vaild rank.");
			return true;
		}
		
		Player target = Bukkit.getServer().getPlayer(args[0]);
		@SuppressWarnings("deprecation")
		OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
		
		if (target == null) {
			File folder = new File(Main.plugin.getDataFolder() + File.separator + "users" + File.separator);
	        boolean found = false;
			
	        if (folder.exists()) {
	    		for (File file : folder.listFiles()) {
	    			if (file.getName().substring(0, file.getName().length() - 4).equals(offline.getUniqueId().toString())) {
	    				found = true;
	    				break;
	    			}
	    		}
	        }
			
			if (!found) {
				sender.sendMessage(Prefix.PREFIX + args[0] + " has never joined this server.");
				return true;
			}
			
			Bukkit.broadcastMessage(Prefix.PREFIX + "§b" + offline.getName() + " §fhas been given §b" + rank.name()+ " §frank.");
			User.get(offline).setRank(rank);
			return true;
		}
		
		Bukkit.broadcastMessage(Prefix.PREFIX + "§b" + target.getName() + " §fhas been given §b" + rank.name() + " §frank.");
		User.get(target).setRank(rank);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("uhc.rank")) {
			return null;
		}
		
		ArrayList<String> toReturn = new ArrayList<String>();
		
		if (args.length == 1) {
			if (args[0].equals("")) {
        		for (Player online : Bukkit.getOnlinePlayers()) {
    				toReturn.add(online.getName());
        		}
        	} else {
        		for (Player online : Bukkit.getOnlinePlayers()) {
        			if (online.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
        				toReturn.add(online.getName());
        			}
        		}
        	}
        }
		
		if (args.length == 2) {
			if (args[1].equals("")) {
        		for (Rank rank : Rank.values()) {
    				toReturn.add(rank.name().toLowerCase());
        		}
        	} else {
        		for (Rank rank : Rank.values()) {
        			if (rank.name().toLowerCase().startsWith(args[1].toLowerCase())) {
        				toReturn.add(rank.name().toLowerCase());
        			}
        		}
        	}
        }
		
		return toReturn;
}

}
