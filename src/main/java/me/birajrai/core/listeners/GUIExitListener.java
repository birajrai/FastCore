package me.birajrai.core.listeners;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIExitListener implements Listener {

    private final FastCore plugin;

    public GUIExitListener() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&aSelect Punishment Type.")))
            plugin.selected_history.remove(event.getPlayer().getUniqueId());
    }
}
