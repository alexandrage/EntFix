package EntFix;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Functions {
	public static boolean removeEnt(ItemStack item) {
		boolean b = false;
		if(item==null) {
			return false;
		}
		for(Entry<Enchantment, Integer> ench : item.getEnchantments().entrySet()) {
			Enchantment Enchant = (Enchantment) ench.getKey();
			if(ench.getValue() > Enchant.getMaxLevel() || ench.getValue() < 0) {
			ItemMeta meta = item.getItemMeta();
			meta.removeEnchant(Enchant);
			item.setItemMeta(meta);
				b=true;
			}
		}
		return b;
	}

	public static HashMap<String, Object> chech (ItemStack[] item, Player p, boolean a) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		boolean b = false;
		int i=-1;
		for (ItemStack it : item) {
			i++;
			if(ReflectFunctions.checkAttributes(it) || removeEnt(it)) {
				b = true;
				if(a) {
					p.getInventory().remove(it);
				} else {
					item[i] = new ItemStack(Material.AIR, 0);
				}
				new Scheduler("onKick", new Object[]{p}).runTaskLater(EntFix.getPlugin(EntFix.class), 5);
			}
		}
		obj.put("b", b);
		obj.put("item", item);
		return obj;
	}
}