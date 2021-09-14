package uom.cp.generator;

import lombok.Getter;

import java.util.List;

public abstract class Generator<T> implements Runnable {

    protected final int MEAN_SPAWN_RATE;
    protected final int MAX_THREADS;

    @Getter
    private final List<T> store;

    public Generator(int meanSpawnRate, int maxThreads, List<T> store) {
        this.MEAN_SPAWN_RATE = meanSpawnRate;
        this.MAX_THREADS = maxThreads;
        this.store = store;
        System.out.println(this.getClass().getSimpleName() + " started");
    }

    abstract void generate();
}
