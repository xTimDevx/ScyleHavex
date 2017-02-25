package me.xtimdevx.scylehavex;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import me.xtimdevx.scylehavex.utils.PermsUtils;

public class User {
	
	private Player player;
	private String uuid;
	
	private FileConfiguration config;
	private File file;
    
    private boolean creating = false;
	
	/**
	 * Gets the data of the given player.
	 * <p>
	 * If the data doesn't exist it will create a new data file and threat the player as a newly joined one.
	 * 
	 * @param player the player.
	 * @return the data instance for the player.
	 */
	public static User get(Player player) {
		return new User(player, player.getUniqueId().toString());
	}

	/**
	 * Gets the data of the given OFFLINE player.
	 * <p>
	 * If the data doesn't exist it will create a new data file and threat the player as a newly joined one.
	 * 
	 * @param offline the offline player.
	 * @return the data instance for the player.
	 */
	public static User get(OfflinePlayer offline) {
		return new User(offline.getPlayer(), offline.getUniqueId().toString());
	}
	
	/**
	 * Constuctor for player data.
	 * <p>
	 * This will set up the data for the player and create missing data.
	 * 
	 * @param uuid the player.
	 * @param uuid the uuid of the player.
	 */
	private User(Player player, String uuid) {
        if (!Main.plugin.getDataFolder().exists()) {
        	Main.plugin.getDataFolder().mkdir();
        }
        
        File folder = new File(Main.plugin.getDataFolder() + File.separator + "users" + File.separator);
        
        if (!folder.exists()) {
        	folder.mkdir(); 
        }
        
        file = new File(folder, uuid + ".yml");
        
        if (!file.exists()) {
        	try {
        		file.createNewFile();
        		creating = true;
        	} catch (Exception e) {
        		Main.plugin.getLogger().severe(ChatColor.RED + "Could not create " + uuid + ".yml!");
        	}
        }
               
        config = YamlConfiguration.loadConfiguration(file);
        
        this.player = player;
        this.uuid = uuid;
        
        if (creating) {
        	if (player != null) {
            	config.set("username", player.getName());
            	config.set("uuid", player.getUniqueId().toString());
            	config.set("ip", player.getAddress().getAddress().getHostAddress());
        	}

        	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        	
        	config.set("firstjoined", new Date().getTime());
        	config.set("lastlogin", new Date().getTime());
        	config.set("lastlogoff", -1l);
        	config.set("rank", Rank.PLAYER.name());
        	
			config.set("muted.status", false);
			config.set("muted.reason", "NOT_MUTED");
			config.set("muted.time", -1);
			
        	saveFile();
        }
	}
	
	/**
	 * Check if the user hasn't been welcomed to the server.
	 * 
	 * @return True if he hasn't, false otherwise.
	 */
	public boolean isNew() {
		return creating;
	}
	
	/**
	 * Get the player class for the user.
	 * 
	 * @return The player class.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Get the uuid for the user.
	 * 
	 * @return The uuid.
	 */
	public String getUUID() {
		return uuid;
	}
	
	/**
	 * Get the configuration file for the player.
	 * 
	 * @return The configuration file.
	 */
	public FileConfiguration getFile() {
		return config;
	}
	
	/**
	 * Save the config file.
	 */
	public void saveFile() {
		try {
			config.save(file);
		} catch (Exception e) {
    		Main.plugin.getLogger().severe(ChatColor.RED + "Could not save " + file.getName() + "!");
		}
	}
	
	/**
	 * Reload the config file.
	 */
	public void reloadFile() {
        config = YamlConfiguration.loadConfiguration(file);
	}
	
	/**
	 * Set the rank for the player.
	 * 
	 * @param rank The new rank.
	 */
	public void setRank(Rank rank) {
		config.set("rank", rank.name());
		saveFile();
		
		if (player != null) {
			PermsUtils.removePermissions(player);
			PermsUtils.addPermissions(player);
		}
	}

	/**
	 * Get the rank of the player.
	 * 
	 * @return the rank.
	 */
	public Rank getRank() {
		return Rank.valueOf(config.getString("rank", "USER"));
	}
	
	/**
	 * Mute the user.
	 * 
	 * @param reason The reason of the mute.
	 * @param unmute The date of unmute, null if permanent.
	 */
	public void mute(String reason, Date unmute) {
		config.set("muted.status", true);
		config.set("muted.reason", reason);
		
		if (unmute == null) {
			config.set("muted.time", -1);
		} else {
			config.set("muted.time", unmute.getTime());
		}
		
		saveFile();
	}
	
	/**
	 * Unmute the user.
	 */
	public void unmute() {
		config.set("muted.status", false);
		config.set("muted.reason", "NOT_MUTED");
		config.set("muted.time", -1);
		saveFile();
	}
	
	/**
	 * Check if the player is muted.
	 * 
	 * @return <code>true</code> if the player is muted, <code>false</code> otherwise.
	 */
	public boolean isMuted() {
		return config.getBoolean("muted.status", false);
	}
	
	/**
	 * Get the reason the player is muted.
	 * 
	 * @return The reason of the mute, null if not muted.
	 */
	public String getMutedReason() {
		if (!isMuted()) {
			return "NOT_MUTED";
		}
		
		return config.getString("muted.reason", "NOT_MUTED");
	}
	
	/**
	 * Get the time in milliseconds for the unmute.
	 * 
	 * @return The unmute time.
	 */
	public long getUnmuteTime() {
		if (!isMuted()) {
			return -1;
		}
	
		return config.getLong("muted.time", -1);
	}
	
	/**
	 * Increase the given stat by 1.
	 * 
	 * @param stat the stat increasing.
	 */
	
	/**
	 * Reset the players health, food, xp, inventory and effects.
	 */
	public void reset() {
        resetHealth();
        resetFood();
        resetExp();
        resetInventory();
        resetEffects();
    }

	/**
	 * Reset the players effects.
	 */
    public void resetEffects() {
        Collection<PotionEffect> effects = player.getActivePotionEffects();

        for (PotionEffect effect : effects) {
            player.removePotionEffect(effect.getType());
        }
    }

	/**
	 * Reset the players health.
	 */
    public void resetHealth() {
        player.setHealth(player.getMaxHealth());
    }

	/**
	 * Reset the players food.
	 */
    public void resetFood() {
        player.setSaturation(5.0F);
        player.setExhaustion(0F);
        player.setFoodLevel(20);
    }

	/**
	 * Reset the players xp.
	 */
    public void resetExp() {
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0F);
    }

	/**
	 * Reset the players inventory.
	 */
    public void resetInventory() {
        PlayerInventory inv = player.getInventory();

        inv.clear();
        inv.setArmorContents(null);
        player.setItemOnCursor(new ItemStack(Material.AIR));

        InventoryView openInventory = player.getOpenInventory();
        
        if (openInventory.getType() == InventoryType.CRAFTING) {
            openInventory.getTopInventory().clear();
        }
    }
	
	/**
	 * The ranking enum class.
	 * 
	 * @author LeonTG77
	 */
	public enum Rank {
		PLAYER, VIP, JRMOD, MOD, BUILDER, ADMIN, DEV, OWNER;
}

}
