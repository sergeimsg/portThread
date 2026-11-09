package edu.melnik.task.port.singleton;

import edu.melnik.task.port.model.Warehouse;
import edu.melnik.task.port.model.Berth;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Потокобезопасный Singleton порта.
 * Синхронизация через ReentrantLock (без volatile и без enum).
 */
public class Port {

    private static Port instance;
    private static final ReentrantLock INIT_LOCK = new ReentrantLock();

    private final Warehouse warehouse;
    private final Berth[] berths;
    private final Semaphore berthSemaphore;

    private Port(int warehouseCapacity, int warehouseInitial, int berthsCount) {
        this.warehouse = new Warehouse(warehouseCapacity, warehouseInitial);
        this.berths = new Berth[berthsCount];
        for (int i = 0; i < berthsCount; i++) {
            berths[i] = new Berth(i + 1);
        }
        // Семафор ограничивает количество кораблей, одновременно находящихся в порту
        this.berthSemaphore = new Semaphore(berthsCount, true);
    }

    /**
     * Инициализация синглтона (вызывается один раз из main).
     */
    public static void initialize(int warehouseCapacity,
                                  int warehouseInitial,
                                  int berthsCount) {
        INIT_LOCK.lock();
        try {
            if (instance == null) {
                instance = new Port(warehouseCapacity, warehouseInitial, berthsCount);
            }
        } finally {
            INIT_LOCK.unlock();
        }
    }

    /**
     * Получение экземпляра.
     */
    public static Port getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Port is not initialized. Call initialize() first.");
        }
        return instance;
    }

    public Warehouse getWarehouse()          { return warehouse; }
    public Berth[] getBerths()               { return berths; }
    public Semaphore getBerthSemaphore()     { return berthSemaphore; }
}