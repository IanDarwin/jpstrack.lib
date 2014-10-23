package jpstrack.model;


public class Recorder implements Runnable {

	public static final int SECONDS = 2;
	
	private int seconds = SECONDS;
	
	boolean done = false;

	// XXX avoid coupling; set by injection.
	private DataGetter plugin = new GpsdDataGetter();
	
	public void run() {
		while (!done) {
			try {
				Thread.sleep(seconds * 1000); // msec
				plugin.getData();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void close() {
		this.done = true;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

}
