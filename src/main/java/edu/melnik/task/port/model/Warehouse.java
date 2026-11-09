package edu.melnik.task.port.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Склад порта — разделяемый ресурс.
 * Синхронизация через ReentrantLock + Condition.
 */
public class Warehouse {


    private static final Logger log = LogManager.getLogger();

    private final int capacity;
    private int containers;
    private final ReentrantLock lock = new ReentrantLock(true); // fair
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Warehouse(int capacity, int initialContainers) {
        this.capacity = capacity;
        this.containers = initialContainers;
    }

    /**
     * Добавить один контейнер на склад.
     * Если склад полон — поток ждёт.
     */
    public void addContainer() throws InterruptedException {
        lock.lock();
        try {
            while (containers >= capacity) {
                notFull.await();
            }
            containers++;
            log.debug("Контейнер добавлен на склад. Всего: {}/{}", containers, capacity);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Забрать один контейнер со склада.
     * Если склад пуст — поток ждёт.
     */
    public void removeContainer() throws InterruptedException {
        lock.lock();
        try {
            while (containers <= 0) {
                notEmpty.await();
            }
            containers--;
            log.debug("Контейнер забран со склада. Всего: {}/{}", containers, capacity);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getContainers() {
        lock.lock();
        try {
            return containers;
        } finally {
            lock.unlock();
        }
    }

    public int getCapacity() {
        return capacity;
    }
}