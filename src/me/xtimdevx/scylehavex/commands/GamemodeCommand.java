package me.xtimdevx.scylehavex.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.xtimdevx.scylehavex.User;
import me.xtimdevx.scylehavex.User.Rank;
import me.xtimdevx.scyleuhc.utils.Prefix;

public class GamemodeCommand implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		User user = User.get(p);
		if(cmd.getName().equalsIgnoreCase("gamemode")) {
			if(user.getRank() != Rank.PLAYER && user.getRank() != Rank.VIP) {
				p.sendMessage(Prefix.NO_PERM);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Prefix.PREFIX + "Usage: /gamemode <mode> <player>");
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
					p.sendMessage(Prefix.PREFIX + "Your gamemode was set to §bCREATIVE§f.");
					p.setGameMode(GameMode.CREATIVE);
				}
				if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
					p.sendMessage(Prefix.PREFIX + "Your gamemode was set to §bSURVIVAL§f.");
					p.setGameMode(GameMode.SURVIVAL);
				}
				if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
					p.sendMessage(Prefix.PREFIX + "Your gamemode was set to §bSPECTATOR§f.");
					p.setGameMode(GameMode.SPECTATOR);
				}
				if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
					p.sendMessage(Prefix.PREFIX + "Your gamemode was set to §bADVENTURE§f.");
					p.setGameMode(GameMode.ADVENTURE);
				}
			}
		} 
		return true;
	}

}
