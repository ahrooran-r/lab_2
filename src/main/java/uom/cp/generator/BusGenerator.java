package uom.cp.generator;

import lombok.SneakyThrows;
import uom.cp.base.Bus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusGenerator extends Generator<Bus> {

    public BusGenerator(int meanSpawnRate, int maxThreads, List<Bus> buses) {
        super(meanSpawnRate, maxThreads, buses);
    }

    @Override
    void generate() {
        Bus bus = new Bus();
        super.getStore().add(bus);

        // bus will not depart until its seats are filled
        bus.depart();
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
