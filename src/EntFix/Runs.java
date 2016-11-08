package EntFix;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Runs {
	static void removeEnt(boolean b) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(!p.hasPermission("unsafe.enchantments")) {
				ItemStack[] item = p.getInventory().getContents();
				ItemStack[] itemarm = p.getInventory().getArmorContents();
				Functions.chech(item, p, true);
				HashMap<String, Object> Obj = Functions.chech(itemarm, p, false);
				if((boolean) Obj.get("b")) {
					itemarm = (ItemStack[]) Obj.get("item");
					p.getInventory().setArmorContents(itemarm);
				}
				if(b) {
					ItemStack[] iteme = p.getInventory().getExtraContents();//1.9
					HashMap<String, Object> OBj = Functions.chech(iteme, p, false);
					if((boolean) OBj.get("b")) {
						iteme = (ItemStack[]) OBj.get("item");//1.9
						p.getInventory().setExtraContents(iteme);//1.9
					}
				}
			}
		}
	}
}