package EntFix;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EntFix extends JavaPlugin {
	boolean b = false;
	public void onEnable() {
		try {
			Class.forName("org.bukkit.inventory.PlayerInventory").getDeclaredMethod("getExtraContents");
			b = true;
		} catch (Exception e) {}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EntListener(this), this);
		getLogger().info("EntFix Enabled!");
		new Scheduler(b).start();
	}
}