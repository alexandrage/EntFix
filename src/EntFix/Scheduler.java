package EntFix;

import java.lang.reflect.Method;

import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
	Object[] obj;
	String s;

	public Scheduler(String str, Object[] o) {
		this.obj = o;
		this.s = str;
	}

	@Override
	public void run() {
		try {
			Class<?> c = Class.forName("EntFix.Runs");
			Method m = c.getDeclaredMethod(s,Object[].class);
			m.invoke(c.newInstance(), new Object[]{this.obj});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}