package EntFix;

import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
	Object[] obj;
	int b = 0;

	public Scheduler(int b, Object[] o) {
		this.b = b;
		this.obj = o;
	}

	@Override
	public void run() {
		if(b==1) {
			Runs.removeEnt(this.obj);
		}
		if(b==2) {
			Runs.onKick(this.obj);
		}
	}
}