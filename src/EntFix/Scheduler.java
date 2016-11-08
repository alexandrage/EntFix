package EntFix;

public class Scheduler extends Thread {
	boolean b;

	public Scheduler(boolean b) {
		this.b = b;
	}

	@Override
	public void run() {
		for(;;) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			Runs.removeEnt(this.b);
		}
	}
}