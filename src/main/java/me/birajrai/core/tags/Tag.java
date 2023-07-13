package me.birajrai.core.tags;

import me.birajrai.core.FastCore;

import java.util.HashMap;
import java.util.Map;

public class Tag {

    private static final Map<String, Tag> tags;

    static {
        tags = new HashMap<>();
    }

    private final FastCore plugin = FastCore.getInstance();
    private String ID;
    private String prefix;
    private String display;

    public Tag(String ID, String prefix, String display) {
        this.ID = ID;
        this.prefix = prefix;
        this.display = display;
    }

    public static Map<String, Tag> getTags() {
        return tags;
    }

    public static Tag getTag(String name) {
        return getTags().get(name.toUpperCase());
    }

    public String getID() {
        return ID;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        plugin.getConfig().set("Tags." + getID() + ".Prefix", getPrefix());
        plugin.saveConfig();
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
        plugin.getConfig().set("Tags." + getID() + ".Display", getDisplay());
        plugin.saveConfig();
    }

    public String getPermission() {
        return "fcore.tags." + getID().toLowerCase();
    }

    public void create() {
        plugin.getConfig().set("Tags." + getID() + ".ID", getID());
        plugin.getConfig().set("Tags." + getID() + ".Prefix", getPrefix());
        plugin.getConfig().set("Tags." + getID() + ".Display", getDisplay());
        plugin.saveConfig();

        getTags().put(getID(), this);
    }

    public void delete() {
        plugin.getConfig().set("Tags." + getID(), null);
        plugin.saveConfig();

        getTags().remove(getID());
    }

}
