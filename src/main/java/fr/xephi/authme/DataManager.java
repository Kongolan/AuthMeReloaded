package fr.xephi.authme;

import java.io.File;
import java.util.List;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.settings.Settings;

public class DataManager extends Thread {

    public AuthMe plugin;
    public DataSource database;

    public DataManager(AuthMe plugin, DataSource database) {
        this.plugin = plugin;
        this.database = database;
    }

    public void run() {
    }

    public OfflinePlayer getOfflinePlayer(String name) {
        OfflinePlayer result = null;
        try {
            for (OfflinePlayer op : Bukkit.getOfflinePlayers())
                if (op.getName().equalsIgnoreCase(name)) {
                    result = op;
                    break;
                }
        } catch (Exception e) {
        }
        return result;
    }

    public void purgeAntiXray(List<String> cleared) {
        int i = 0;
        for (String name : cleared) {
            try {
                org.bukkit.OfflinePlayer player = getOfflinePlayer(name);
                if (player == null)
                    continue;
                String playerName = player.getName();
                File playerFile = new File("." + File.separator + "plugins" + File.separator + "AntiXRayData" + File.separator + "PlayerData" + File.separator + playerName);
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
            } catch (Exception e) {
            }
        }
        ConsoleLogger.info("AutoPurgeDatabase : Remove " + i + " AntiXRayData Files");
    }

    public void purgeLimitedCreative(List<String> cleared) {
        int i = 0;
        for (String name : cleared) {
            try {
                org.bukkit.OfflinePlayer player = getOfflinePlayer(name);
                if (player == null)
                    continue;
                String playerName = player.getName();
                File playerFile = new File("." + File.separator + "plugins" + File.separator + "LimitedCreative" + File.separator + "inventories" + File.separator + playerName + ".yml");
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
                playerFile = new File("." + File.separator + "plugins" + File.separator + "LimitedCreative" + File.separator + "inventories" + File.separator + playerName + "_creative.yml");
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
                playerFile = new File("." + File.separator + "plugins" + File.separator + "LimitedCreative" + File.separator + "inventories" + File.separator + playerName + "_adventure.yml");
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
            } catch (Exception e) {
            }
        }
        ConsoleLogger.info("AutoPurgeDatabase : Remove " + i + " LimitedCreative Survival, Creative and Adventure files");
    }

    public void purgeDat(List<String> cleared) {
        int i = 0;
        for (String name : cleared) {
            try {
                org.bukkit.OfflinePlayer player = getOfflinePlayer(name);
                if (player == null)
                    continue;
                String playerName = player.getName();
                File playerFile = new File(plugin.getServer().getWorldContainer() + File.separator + Settings.defaultWorld + File.separator + "players" + File.separator + playerName + ".dat");
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
            } catch (Exception e) {
            }
        }
        ConsoleLogger.info("AutoPurgeDatabase : Remove " + i + " .dat Files");
    }

    public void purgeEssentials(List<String> cleared) {
        int i = 0;
        for (String name : cleared) {
            try {
                File playerFile = new File(plugin.ess.getDataFolder() + File.separator + "userdata" + File.separator + name + ".yml");
                if (playerFile.exists()) {
                    playerFile.delete();
                    i++;
                }
            } catch (Exception e) {
            }
        }
        ConsoleLogger.info("AutoPurgeDatabase : Remove " + i + " EssentialsFiles");
    }

    @SuppressWarnings("deprecation")
    public void purgePermissions(List<String> cleared, Permission permission) {
        int i = 0;
        for (String name : cleared) {
            try {
                OfflinePlayer p = Bukkit.getOfflinePlayer(name);
                for (String group : permission.getPlayerGroups((Player) p)) {
                    permission.playerRemoveGroup(null, p, group);
                }
                i++;
            } catch (Exception e) {
            }
        }
        ConsoleLogger.info("AutoPurgeDatabase : Remove " + i + " Permissions");
    }
}
