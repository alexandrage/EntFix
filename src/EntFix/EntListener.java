package EntFix;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EntListener implements Listener {
	public EntFix plugin;

	public EntListener(EntFix instance) {
		this.plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onEntityDamage(EntityDamageEvent e) {
		Entity ent = e.getEntity();
		if ((ent instanceof Player)) {
			Player p = (Player)ent;
			if(!p.hasPermission("unsafe.enchantments")) {
				for(ItemStack item : p.getInventory().getArmorContents()) {
					Functions.removeEnt(item);
					if(ReflectFunctions.checkAttributes(item)) {
						p.getInventory().remove(item);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPickupItem(PlayerPickupItemEvent e) {
		if(!e.getPlayer().hasPermission("unsafe.enchantments")) {
			Item item = e.getItem();
			if(ReflectFunctions.checkAttributes(item.getItemStack()) || Functions.removeEnt(item.getItemStack())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void InventoryClick(InventoryClickEvent e) {
		if(!e.getWhoClicked().hasPermission("unsafe.enchantments")) {
			if (e.getCurrentItem() != null) {
				ItemStack item = e.getCurrentItem();
				if(ReflectFunctions.checkAttributes(item) || Functions.removeEnt(item)) {
					e.getWhoClicked().getInventory().remove(item);
					e.setCancelled(true);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!e.getPlayer().hasPermission("unsafe.enchantments")) {
			if (e.getItem() != null) {
				ItemStack item = e.getItem();
				if(ReflectFunctions.checkAttributes(item) || Functions.removeEnt(item)) {
					e.setCancelled(true);
					if(this.plugin.b) {
						e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));//1.9++
						e.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));//1.9++
					} else {
						e.getPlayer().setItemInHand(new ItemStack(Material.AIR));//1.8
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onItemDrop(PlayerDropItemEvent e) {
		if(!e.getPlayer().hasPermission("unsafe.enchantments")) {
			Item item = e.getItemDrop();
			if(ReflectFunctions.checkAttributes(item.getItemStack()) || Functions.removeEnt(item.getItemStack())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onBlockDispenseEvent(BlockDispenseEvent e) {
		if(ReflectFunctions.checkAttributes(e.getItem())) {
			e.setCancelled(true);
		}
	}
	
	HashMap<String, Long> player = new HashMap<String, Long>();
	HashMap<String, Long> playertime = new HashMap<String, Long>();
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void on(PlayerInteractEvent e) {
    	String p = e.getPlayer().getName();
    	if(player.get(p) == null) {
    		player.put(p, 0l);
    		playertime.put(p, System.currentTimeMillis()+1000);
    	} else {
    		player.put(p, player.get(p)+1);
    		if(playertime.get(p)<System.currentTimeMillis()) {
    			if(player.get(p)>12) {
    				e.getPlayer().kickPlayer("");
    			}
	    		player.remove(p);
	    		playertime.remove(p);
    		}
    	}
    }
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void on(PlayerQuitEvent e) {
		String p = e.getPlayer().getName();
		if(player.get(p) != null) {
    		player.remove(p);
    		playertime.remove(p);
		}
	}
}