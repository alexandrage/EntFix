package EntFix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Kick extends BukkitRunnable {
	Player p;

	public Kick(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		if(EntFix.kick) {
			this.p.kickPlayer("§4Креатив хак запрещен!");
		}
		if(EntFix.broadcast) {
			Bukkit.getServer().broadcastMessage("§6"+p.getName()+" §4Кикнут с сервера, причина:Креатив хак");
		}
	}
}