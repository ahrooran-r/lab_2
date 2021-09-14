package uom.cp.generator;

import lombok.SneakyThrows;
import uom.cp.base.Rider;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RiderGenerator extends Generator<Rider> {

    public RiderGenerator(int meanSpawnRate, int maxThreads, List<Rider> riders) {
        super(meanSpawnRate, maxThreads, riders);
    }

    @Override
    void generate() {
        Rider rider = new Rider();
        super.getStore().add(rider);

        // bus will not depart until its seats are filled
        rider.boardBus();
    }

    @SneakyThrows
    @Override
    public void run() {

        long sleepInterval = (long) (-(Math.log(Math.random())) * super.MEAN_SPAWN_RATE);

        ExecutorService executorService = Executors.newFixedThreadPool(super.MAX_THREADS);

        for (int i = 0; i < MAX_THREADS; i++) {
            executorService.execute(this::generate);
            Thread.sleep(sleepInterval);
        }

        executorService.shutdown();
    }
}
