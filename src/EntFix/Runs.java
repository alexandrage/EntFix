package EntFix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Runs {
	static void removeEnt(Object[] obj) {
		ExecutorService ex = Executors.newFixedThreadPool(20);
		for(Player p : Bukkit.getServer().getOnlinePlayers()) ex.submit(new Executor(p, obj));
		ex.shutdown();
	}

	static void onKick(Object[] obj) {
		Player p = (Player) obj[0];
		p.kickPlayer("§4Креатив хак запрещен!");
		Bukkit.getServer().broadcastMessage("§6"+p.getName()+" §4Кикнут с сервера, причина:Креатив хак");
	}
}