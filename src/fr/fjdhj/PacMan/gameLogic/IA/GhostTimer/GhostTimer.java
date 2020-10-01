package fr.fjdhj.PacMan.gameLogic.IA.GhostTimer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import fr.fjdhj.PacMan.MainClass;

public class GhostTimer implements Runnable{
	private Thread GhostTimerThread;
	private Thread timerThread;
	private Thread interuptTimerThread;
	
	private final Lock pauseTimerLock = new ReentrantLock();
	
	private GhostTimer ghostTimer = this;
	
	private boolean inPause = false;
	
	private long pauseTime = 0l;
	private long pauseStop = -1l;
	private long time = 0l;
	private long stop = -1l;
	private int levelInWave = 0;
	
	public GhostTimer() {
		interuptTimer();
	}
	
	
	private void timer() {
		/*
		 * Ici il faut prendre en compte plusieur cas :
		 * -Aucune intéruption du Timer : donc l'execution se passe bien
		 * -Une intéruption du Timer (cas ou super PacGomme mangé) : Il faut mettre sur pause l'execution (on récupère 
		 * 			le temps d'execution et on le soustrais au temps total pour le temps restant) lancer une autre pour le mod
		 * 			Fightned et on fini l'autre
		 * -Si on mange deux pac gomme alors on reset juste le timer frightned
		 */
		
		timerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				MainClass.verbose("[Timer] Lancement pour : "+time);
				do {
					long start = System.currentTimeMillis();
					try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
					
					if(stop != -1l) {
						time = time - (stop - start);
						stop = -1l;
						MainClass.verbose("[Timer] Pause détecter, temps restant : "+time);
					}else {
						MainClass.verbose("[Timer] Aucune pause détecter");
						time = 0;
					}
					
					if(inPause) {
						synchronized (timerThread) {
							MainClass.verbose("[Timer] Attente de la fin de la pause via wait()");
							try {timerThread.wait();} catch (InterruptedException e) {e.printStackTrace();}
							MainClass.verbose("[Timer] Fin de la pause détecter via notify()");
						}
					}
				}while(time != 0);
				new GhostModTask(GhostTimerThread).run();
				
			}
		});
		
		timerThread.start();
		/*On mets le thread sur pause, comme ça on attends la fin du timer*/
		synchronized (GhostTimerThread) {
			try {
				GhostTimerThread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

				

		
	}
	
	public void interuptTimer() {
		interuptTimerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(time != -1l) {
					pauseTimerLock.lock();			
					//Si on appel pause(), alors qu'un timer est en cour il faut bloquer le tread
					if(inPause)
						synchronized (interuptTimerThread) {
							pauseTimerLock.unlock();
							try {interuptTimerThread.wait();} catch (InterruptedException e) {e.printStackTrace();}
							 
					//On attends tant que l'attente = 0	
					}else if(pauseTime == 0l) {
						synchronized (interuptTimerThread) {
							pauseTimerLock.unlock();
							try {interuptTimerThread.wait();} catch (InterruptedException e) {e.printStackTrace();}
						}
					}else {
						
						//On met sur pause le timer si il ne l'ai pas déjà
						if(pauseTime != 0l)
							stop = System.currentTimeMillis();
						
						long pauseStart = System.currentTimeMillis();
						inPause = true;
						MainClass.verbose("[Interuption Timer] Timer interompue pendant "+pauseTime);
		
						Timer interuptTimer = new Timer();
						interuptTimer.schedule(new TimerTask() {
							@Override
							public void run() {
								synchronized (ghostTimer) {
									if(pauseTime == 0l) {
										MainClass.verbose("[Interuption Timer] Pause fini");
										synchronized (timerThread) {
											timerThread.notify();
										}
									}else {
										MainClass.verbose("[Interuption Timer] Pause non fini, method pause() a été appelé");
										pauseTime = pauseTime-(pauseStop - pauseStart);
									}
									inPause = false;
									synchronized (interuptTimerThread) {
										interuptTimerThread.notify();
									}
								}
							}
						}, pauseTime);
						pauseTime = 0l;
						pauseTimerLock.unlock();
					}
				}
			}
			
		});
		interuptTimerThread.start();
		
		
	}
	/**
	 * Mets sur pause le timer, si le timer étais déjà sur pause, alors 
	 * le timer seras mis sur pause le temps de la nouvelle valeur (si celle si est supèrieur ou égale a la précédente)
	 * @param millis
	 * @return 
	 */
	public void pause(long millis) {
		MainClass.verbose("[GhosTimer.Pause] Nouvelle pause de : " + Long.toString(millis));
		pauseTimerLock.lock();
		pauseTime = millis;
		pauseStop = System.currentTimeMillis();
		synchronized (interuptTimerThread) {
			interuptTimerThread.notify();
		}	
		pauseTimerLock.unlock();
		
	}

	@Override
	public void run() {
		GhostTimerThread = Thread.currentThread();
		
		//Pour tester
		time = 2000l;
		this.timer();
		time = 10000l;
		this.timer();		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		GhostTimer gtimer = new GhostTimer();
		Thread t = new Thread(gtimer);
		t.start();
		
		Thread.sleep(1000);
		MainClass.verbose("[Main] Lancement pause");
		gtimer.pause(5000l);
		Thread.sleep(1000);
		MainClass.verbose("[Main] Lancement pause");
		gtimer.pause(5000l);
	}
	
	/*
	 		if(time!=-1) {
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("timer good");
					if(!mod.get().equals(IAmod.FRIGHTNED))
						round++;
					
					switch(mod.get()) {
					case CHASE:
						mod.set(IAmod.SCATTER);
						break;
					case FRIGHTNED:
						mod.set(storMod);
						break;
					case SCATTER:
						mod.set(IAmod.CHASE);
						break;
					case ELROY1:
						break;
					case ELROY2:
						break;
					
					}
					switch(ghost.getDirection().get()) {
					case DOWN:
						ghost.setDirection(Direction.UP);
						break;
					case LEFT:
						ghost.setDirection(Direction.RIGHT);
						break;
					case RIGHT:
						ghost.setDirection(Direction.LEFT);
						break;
					case UP:
						ghost.setDirection(Direction.DOWN);
						break;
					default:
						break;
					
					}
					
				}
				
			}, time);
			return timer;
		}
		return null;
	 * */

}
