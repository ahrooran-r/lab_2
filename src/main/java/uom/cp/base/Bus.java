package uom.cp.base;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import uom.cp.runtime.BusStop;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bus {

    private static final int AVAILABLE_SEATS = 50;

    private static final AtomicInteger count = new AtomicInteger(0);

    @Getter
    private final String id;

    @Getter
    private final Lock door = new ReentrantLock(true);

    @Getter
    private final CountDownLatch availableSeats;

    @Getter
    @Setter
    private volatile boolean isDeparted = false;

    public Bus() {
        this.id = generateId();
        this.availableSeats = new CountDownLatch(AVAILABLE_SEATS);
    }

    /**
     * Runs in a separate thread
     * Once latch hits 0 execute depart() method
     */
    @SneakyThrows
    public void depart() {

        if (!areRidersWaiting()) System.out.printf("%s departing without riders\n", this.getId());
        else {
            availableSeats.await();
            System.out.printf("%s departing\n", this.getId());
        }

        this.setDeparted(true);
    }

    private String generateId() {
        return this.getClass().getSimpleName() + "-" + count.incrementAndGet();
    }

    private boolean areRidersWaiting() {
        List<Rider> riders = BusStop.getRiders();
        for (Rider rider : riders) {
            if (!rider.isBoarded()) return true;
        }
        return false;
    }
}
