package me.xtimdevx.scylehavex.utils;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.xtimdevx.scylehavex.Main;
import me.xtimdevx.scylehavex.User;
import me.xtimdevx.scylehavex.User.Rank;

public class PermsUtils {
	
	public static void addPermissions(Player player) {
		File folder = new File(Main.plugin.getDataFolder() + File.separator + "users" + File.separator);
        boolean found = false;
		
        if (folder.exists()) {
    		for (File file : folder.listFiles()) {
    			if (file.getName().substring(0, file.getName().length() - 4).equals(player.getUniqueId().toString())) {
    				found = true;
    				break;
    			}
    		}
        }
		
		if (!found) {
			return;
		}
		
		if (Main.permissions.get(player.getName()) == null) {
			Main.permissions.put(player.getName(), player.addAttachment(Main.plugin));
		}

		PermissionAttachment perm = Main.permissions.get(player.getName());
		
		User user = User.get(player);
		Rank rank = user.getRank();
		
		if (rank == Rank.PLAYER) {
			return;
		}
		
		if (rank == Rank.ADMIN || rank == Rank.OWNER || rank == Rank.DEV) {
			player.setOp(true);
			return;
		}

		perm.setPermission("uhc.spectate", true);
		perm.setPermission("uhc.prelist", true);
		
		if (rank == Rank.JRMOD || rank == Rank.MOD) {
			perm.setPermission("uhc.ban", true);
			perm.setPermission("uhc.broadcast", true);
			perm.setPermission("uhc.fly", true);
			perm.setPermission("uhc.info", true);
			perm.setPermission("uhc.invsee", true);
			perm.setPermission("uhc.kick", true);
			perm.setPermission("uhc.mute", true);
			perm.setPermission("uhc.pvp", true);
			perm.setPermission("uhc.scenario", true);
			perm.setPermission("uhc.sethealth", true);
			perm.setPermission("uhc.setmaxhealth", true);
			perm.setPermission("uhc.spectate", true);
			perm.setPermission("uhc.seemsg", true);
			perm.setPermission("uhc.cmdspy", true);
			perm.setPermission("uhc.staff", true);
			perm.setPermission("uhc.admin", true);
			perm.setPermission("uhc.team", true);
			perm.setPermission("uhc.tempban", true);
			perm.setPermission("uhc.info", true);
			perm.setPermission("uhc.tp", true);
			perm.setPermission("uhc.whitelist", true);
		}
	}
	
	/**
	 * Handle the permissions for the given player if he leaves.
	 * 
	 * @param player the player.
	 */
	public static void removePermissions(Player player) {
		if (Main.permissions.get(player.getName()) == null) {
			return;
		}
		
		try {
			player.removeAttachment(Main.permissions.get(player.getName()));
		} catch (Exception e) {
			// uhh...?
		}
		
		Main.permissions.remove(player.getName());
}

}
