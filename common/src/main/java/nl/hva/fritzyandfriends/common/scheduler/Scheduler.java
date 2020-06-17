package nl.hva.fritzyandfriends.common.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public static void loop(Function function) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(function::execute, 0, 10, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            scheduledFuture.cancel(true);
            scheduler.shutdown();
        }, 1, TimeUnit.MINUTES);

    }

}