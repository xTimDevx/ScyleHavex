package me.xtimdevx.scylehavex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.xtimdevx.scylehavex.User;
import me.xtimdevx.scylehavex.User.Rank;
import me.xtimdevx.scyleuhc.utils.Prefix;

public class GamemodeCommand implements CommandExecutor, TabCompleter{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		User user = User.get(p);
		if(cmd.getName().equalsIgnoreCase("gamemode")) {
			if(user.getRank() != Rank.PLAYER && user.getRank() != Rank.VIP) {
				p.sendMessage(Prefix.NO_PERM);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Prefix.PREFIX + "");
			}
		}
		return true;
	}

}
