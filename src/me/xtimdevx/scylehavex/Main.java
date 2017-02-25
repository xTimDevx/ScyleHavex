package me.xtimdevx.scylehavex;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.xtimdevx.scylehavex.commands.SetRankCommand;
import me.xtimdevx.scylehavex.events.chatEvents;
import me.xtimdevx.scylehavex.events.loginEvents;

public class Main extends JavaPlugin{
	
	public static Plugin plugin;
	public static HashMap<String, PermissionAttachment> permissions = new HashMap<String, PermissionAttachment>();
	
	public void onEnable() {
		registerCommands();
		registerListeners();
		plugin = this;
	}
	
	public void registerCommands() {
		getCommand("setrank").setExecutor(new SetRankCommand());
	}
	
	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new chatEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new loginEvents(this), this);
	}

}
