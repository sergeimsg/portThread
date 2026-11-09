package edu.melnik.task.port.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Причал — может быть занят только одним кораблём.
 */
public class Berth {

    private final int id;
    private final ReentrantLock lock = new ReentrantLock(true);

    public Berth(int id) {
        this.id = id;
    }

    /**
     * Попытка занять причал (неблокирующая).
     */
    public boolean tryAcquire() {
        return lock.tryLock();
    }

    /**
     * Освободить причал.
     */
    public void release() {
        lock.unlock();
    }

    public int getId() {
        return id;
    }
}