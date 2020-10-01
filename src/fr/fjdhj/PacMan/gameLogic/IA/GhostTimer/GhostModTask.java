package fr.fjdhj.PacMan.gameLogic.IA.GhostTimer;

public class GhostModTask{

	 private Thread ghostTimerThread;
	
	public GhostModTask(Thread ghostTimerThread) {
		this.ghostTimerThread = ghostTimerThread;
		
	}

	public void run() {
		System.out.println("[Execution Tache]Execution finie");
		synchronized (ghostTimerThread) {
			ghostTimerThread.notify();
		}
	}

}
