package uom.cp.base;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import uom.cp.runtime.BusStop;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Rider {

    private static final AtomicInteger count = new AtomicInteger(0);

    @Getter
    private final String id;

    @Getter
    @Setter
    private volatile boolean isBoarded = false;

    public Rider() {
        this.id = generateId();
    }

    @SneakyThrows
    public void boardBus() {

        boolean alreadyPrinted = false;

        while (true) {

            Bus bus = searchBus();

            // practically only one rider can make into bus at a time
            // so acquire bus's `lock` to only allow a single rider
            if (bus.getDoor().tryLock(100, TimeUnit.MILLISECONDS)) {

                // because rider boarding bus is very fast, to emulate real life scenario
                // sleeping a little bit
                Thread.sleep(500);

                try {
                    bus.getAvailableSeats().countDown();
                    this.setBoarded(true);
                    System.out.printf("%s boarded %s. Available seats: %d\n", this.getId(), bus.getId(), bus.getAvailableSeats().getCount());
                    return;

                } finally {
                    bus.getDoor().unlock();
                }

            } else {
                if (!alreadyPrinted) {
                    System.out.printf("%s could not board %s. Trying again.\n", this.getId(), bus.getId());
                    alreadyPrinted = true;
                }
                Thread.sleep(100);
            }
        }
    }

    private String generateId() {
        return this.getClass().getSimpleName() + "-" + count.incrementAndGet();
    }

    @SneakyThrows
    private Bus searchBus() {

        boolean alreadyPrinted = false;

        while (true) {

            // because these are final variables, read operations don't need synchronization
            List<Bus> buses = BusStop.getBuses().stream().filter(bus -> !bus.isDeparted()).collect(Collectors.toList());
            int bound = buses.size();

            // wait if there are no buses
            if (bound <= 0) {
                if (!alreadyPrinted) {
                    System.out.printf("%s is waiting\n", getId());
                    alreadyPrinted = true;
                }
                Thread.sleep(1000);

            } else {
                int index = ThreadLocalRandom.current().nextInt(bound);
                Bus bus = buses.get(index);

                if (!bus.isDeparted()) return bus;

                // wait for sometime before searching again
                Thread.sleep(100);
            }
        }
    }
}
