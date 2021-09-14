package uom.cp.runtime;

import lombok.Getter;
import uom.cp.base.Bus;
import uom.cp.base.Rider;
import uom.cp.generator.BusGenerator;
import uom.cp.generator.RiderGenerator;

import java.util.ArrayList;
import java.util.List;

public class BusStop {

    private final static int RIDER_MEAN_SPAWN_RATE = 3_000;
    private final static int RIDERS = 1000;

    @Getter
    private final static List<Rider> riders = new ArrayList<>(RIDERS);

    private final static int BUS_MEAN_SPAWN_RATE = 120_000;
    private final static int BUSES = 10;

    @Getter
    private final static List<Bus> buses = new ArrayList<>(BUSES);

    public static void main(String[] args) {

        Thread riderGenerator = new Thread(new RiderGenerator(RIDER_MEAN_SPAWN_RATE, RIDERS, riders));
        riderGenerator.start();

        Thread busGenerator = new Thread(new BusGenerator(BUS_MEAN_SPAWN_RATE, BUSES, buses));
        busGenerator.start();
    }
}
