package app;

public class Progress extends Thread {

	private static char[] loading = new char[] { '|', '/', '-', '\\' };
	private static Thread progress = new Thread(new Progress());

	private Progress() {
		super.setDaemon(true);
	}

	public static void runner() {
		progress = new Thread(new Progress());
		progress.start();
	}

	public static void stall() {
		progress.suspend();
		progress.stop();
	}

	@Override
	public void run() {
		int i = 0;
		while (i < loading.length)
			try {
				progress.sleep(200);
				System.out.print("Estamos trabajando " + loading[i] + loading[i] + loading[i] + "\r");
				i++;
				if(loading.length == i)
					i = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}
}
