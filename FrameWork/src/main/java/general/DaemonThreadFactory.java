package general;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;



  final class DaemonThreadFactory extends ReschedulingDailyTask implements ThreadFactory { 

  public DaemonThreadFactory(ScheduledExecutorService service, Runnable task,
			int hour) {
		super(service, task, hour);
		// TODO Auto-generated constructor stub
	}



//  @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Schedule Thread");
        thread.setDaemon(true);
        return thread;
    }



public static void main(String[] args) throws InterruptedException {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new DaemonThreadFactory(service, task, hour));
    Calendar cal = Calendar.getInstance();
    System.out.println("Time now: " + cal.getTimeInMillis());
    cal.add(Calendar.SECOND, 5);
    cal.set(Calendar.MILLISECOND, 0);
    System.out.println("Execute at: " + cal.getTimeInMillis());
    int hour = cal.get(Calendar.HOUR);
    int min = cal.get(Calendar.MINUTE);
    int sec = cal.get(Calendar.SECOND);
    final Object lock = new Object();
    Runnable task = new Runnable() {

       @Override
        public void run() {
            System.out.println("Time is " + Calendar.getInstance().getTimeInMillis());
            synchronized(lock) {
                lock.notifyAll();
            }
        }

    };

    ReschedulingDailyTask daily = new ReschedulingDailyTask(service, task, hour, min, sec);
    daily.enable();

    System.out.println("Waiting");
    synchronized (lock) {
        lock.wait();
    }
    System.out.println("Done");
}
  }
  
  
  
  /*final class DaemonThreadFactory implements ThreadFactory {

  //  @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Schedule Thread");
        thread.setDaemon(true);
        return thread;
    }



public static void main(String[] args) throws InterruptedException {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());
    Calendar cal = Calendar.getInstance();
    System.out.println("Time now: " + cal.getTimeInMillis());
    cal.add(Calendar.SECOND, 5);
    cal.set(Calendar.MILLISECOND, 0);
    System.out.println("Execute at: " + cal.getTimeInMillis());
    int hour = cal.get(Calendar.HOUR);
    int min = cal.get(Calendar.MINUTE);
    int sec = cal.get(Calendar.SECOND);
    final Object lock = new Object();
    Runnable task = new Runnable() {

     //   @Override
        public void run() {
            System.out.println("Time is " + Calendar.getInstance().getTimeInMillis());
            synchronized(lock) {
                lock.notifyAll();
            }
        }

    };

    ReschedulingDailyTask daily = new ReschedulingDailyTask(service, task, hour, min, sec);
    daily.enable();

    System.out.println("Waiting");
    synchronized (lock) {
        lock.wait();
    }
    System.out.println("Done");
}
}
}*/