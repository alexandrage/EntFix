package EntFix;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Runs {
	void removeEnt(final Object[] obj) {
		ExecutorService ex = Executors.newFixedThreadPool(20);
		for(final Player p : Bukkit.getServer().getOnlinePlayers()) ex.submit(new Runnable() {
			@Override
			public void run() {
			if(!p.hasPermission("unsafe.enchantments")) {
				ItemStack[] item = p.getInventory().getContents();
				ItemStack[] itemarm = p.getInventory().getArmorContents();
				Functions.chech(item, p, true);
				HashMap<String, Object> Obj = Functions.chech(itemarm, p, false);
				if((boolean) Obj.get("b")) {
					itemarm = (ItemStack[]) Obj.get("item");
					p.getInventory().setArmorContents(itemarm);
				}
				if((boolean) obj[0]) {
					ItemStack[] iteme = p.getInventory().getExtraContents();//1.9
					HashMap<String, Object> OBj = Functions.chech(iteme, p, false);
					if((boolean) OBj.get("b")) {
						iteme = (ItemStack[]) OBj.get("item");//1.9
						p.getInventory().setExtraContents(iteme);//1.9
					}
				}
			}
		}});
		ex.shutdown();
	}

	void onKick(Object[] obj) {
		Player p = (Player) obj[0];
		p.kickPlayer("§4Креатив хак запрещен!");
		Bukkit.getServer().broadcastMessage("§6"+p.getName()+" §4Кикнут с сервера, причина:Креатив хак");
	}
}