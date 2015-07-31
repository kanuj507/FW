package general;


import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class ReschedulingDailyTask implements Runnable {

    private final ScheduledExecutorService service;

    protected final Runnable task;
    private final int hour;
    private final int min;
    private final int sec;

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final AtomicBoolean scheduled = new AtomicBoolean(false);

    public ReschedulingDailyTask(final ScheduledExecutorService service,
            final Runnable task, final int hour) {
        this (service, task, hour, 0, 0);
    }

    public ReschedulingDailyTask(final ScheduledExecutorService service,
            final Runnable task, final int hour, final int min, final int sec) {
        this.service = service;
        this.task = task;
        this.hour = hour;
        this.min = min;
        this.sec = sec;

    }

    public void enable() {
        if (!active.getAndSet(true)) {
            // was not enabled:
            reSchedule();
        }
    }

    public void disable() {
        active.getAndSet(false);
    }

    private void reSchedule() {
        if (!scheduled.getAndSet(true)) {
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, sec);
            calendar.set(Calendar.MILLISECOND, 0);
            while (calendar.getTimeInMillis() < now) {
                // scheduled in the past, go forward one day....
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // for debug, if needed....
            //System.out.println("Reschedule for " + calendar.getTimeInMillis() 
            //        + " (in " + (calendar.getTimeInMillis() - now) + "ms)" );

            // set ourselves up to run at a given time.
            service.schedule(this, calendar.getTimeInMillis() - now, TimeUnit.MILLISECONDS);
        }

    }

 //  @Override
    public void run() {
        // since we are running, we are no longer scheduled...
        scheduled.set(false);

        // we may have been disabled after we were enabled ...
        // you can't cancel the schedule, but you can ignore the task...
        if (!active.get()) {
            return;
        }

        // we were active, and we run the task, and force the reschedule.
        try {
            task.run();
        } finally {
            reSchedule();
        }
    }

}