package me.birajrai.core.ranks;

import me.birajrai.core.FastCore;
import me.birajrai.core.grants.Grant;
import me.birajrai.core.management.PermissionManagement;
import me.birajrai.core.permissions.Permissions;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Rank {

    private static final Map<String, Rank> ranks;

    static {
        ranks = new LinkedHashMap<>();
    }

    private final FastCore plugin = FastCore.getInstance();
    private final PermissionManagement permissionManagement = new PermissionManagement();
    private String ID;
    private String prefix;
    private String color;
    private String display;
    private String tabIndex;
    private ItemStack grantItem;
    private boolean isDefault;

    public Rank(String ID, String prefix, String color, String display, String tabIndex, ItemStack grantItem, boolean isDefault) {
        this.ID = ID;
        this.prefix = prefix;
        this.color = color;
        this.display = display;
        this.tabIndex = tabIndex;
        this.grantItem = grantItem;
        this.isDefault = isDefault;
    }

    public static Map<String, Rank> getRanks() {
        return ranks;
    }

    public static Rank getRank(String name) {
        return getRanks().get(name.toUpperCase());
    }

    public String getID() {
        return ID;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        plugin.getConfig().set("Ranks." + getID() + ".Prefix", getPrefix());
        plugin.saveConfig();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        plugin.getConfig().set("Ranks." + getID() + ".Color", getColor());
        plugin.saveConfig();
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
        plugin.getConfig().set("Ranks." + getID() + ".Display", getDisplay());
        plugin.saveConfig();
    }

    public String getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(String tabIndex) {
        this.tabIndex = tabIndex;
        plugin.getConfig().set("Ranks." + getID() + ".TabIndex", getTabIndex());
        plugin.saveConfig();
    }

    public ItemStack getGrantItem() {
        return grantItem;
    }

    public void setGrantItem(ItemStack grantItem) {
        this.grantItem = grantItem;
        plugin.getConfig().set("Ranks." + getID() + ".GrantItem", Utils.getColoredItemData(getGrantItem(), getGrantItem().getType().toString()));
        plugin.saveConfig();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getGrantPermission() {
        return "yocore.grant." + getID().toLowerCase();
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
        plugin.getConfig().set("Ranks." + getID() + ".Default", isDefault());
        plugin.saveConfig();
    }

    public void create() {
        plugin.getConfig().set("Ranks." + getID() + ".ID", getID());
        plugin.getConfig().set("Ranks." + getID() + ".Prefix", getPrefix());
        plugin.getConfig().set("Ranks." + getID() + ".Color", getColor());
        plugin.getConfig().set("Ranks." + getID() + ".Display", getDisplay());
        plugin.getConfig().set("Ranks." + getID() + ".TabIndex", getTabIndex());
        plugin.getConfig().set("Ranks." + getID() + ".GrantItem", Utils.getColoredItemData(getGrantItem(), getGrantItem().getType().toString()));
        plugin.getConfig().set("Ranks." + getID() + ".Default", isDefault());
        plugin.saveConfig();

        Permission permission = new Permission("yocore.grant." + getID().toLowerCase());
        permission.setDescription("Permission");

        if (!Permissions.getAllServerPermissions().contains(permission))
            plugin.getServer().getPluginManager().addPermission(permission);

        plugin.permissionsData.config.set("Ranks." + getID() + ".Permissions", new ArrayList<>());
        plugin.permissionsData.saveData();

        for (Player players : Bukkit.getOnlinePlayers()) {
            for (Team team : players.getScoreboard().getTeams())
                players.getScoreboard().getTeam(team.getName()).unregister();
        }

        getRanks().put(getID(), this);
    }

    public void delete() {
        Rank lowestRank = Rank.getRank("default");
        for (Map.Entry<String, Rank> entry : Rank.getRanks().entrySet()) {
            if (entry.getValue().isDefault())
                lowestRank = entry.getValue();
        }

        for (String player : plugin.playerData.config.getKeys(false)) {
            yoPlayer yoPlayer = new yoPlayer(UUID.fromString(player));

            if (yoPlayer.getRank() == this)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + yoPlayer.getPlayer().getName() + " " + lowestRank.getID());
        }

        for (String player : plugin.grantData.config.getKeys(false)) {
            if (plugin.grantData.config.contains(player + ".Grants")) {
                yoPlayer yoPlayer = new yoPlayer(UUID.fromString(player));

                for (Map.Entry<Integer, Grant> entry : Grant.getGrants(yoPlayer).entrySet()) {
                    if (entry.getValue().getGrant().equalsIgnoreCase(getID())) {
                        plugin.grantData.config.set(player + ".Grants." + entry.getValue().getID() + ".Status", "Expired");
                        plugin.grantData.config.set(player + ".Grants." + entry.getValue().getID() + ".Grant", "(Removed Rank)");
                    }
                }
            }
        }

        plugin.getConfig().set("Ranks." + getID(), null);
        plugin.saveConfig();

        plugin.getServer().getPluginManager().removePermission("yocore.grants." + getID().toLowerCase());

        plugin.permissionsData.config.set("Ranks." + getID(), null);
        plugin.permissionsData.saveData();

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Team team : player.getScoreboard().getTeams())
                player.getScoreboard().getTeam(team.getName()).unregister();
        }

        getRanks().remove(getID());
    }

    public Permissions permissions() {
        return new Permissions(this);
    }
}
