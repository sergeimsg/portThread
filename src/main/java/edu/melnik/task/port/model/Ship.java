package edu.melnik.task.port.model;

import edu.melnik.task.port.state.ShipState;
import edu.melnik.task.port.state.impl.ArrivingState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.Callable;

/**
 * Корабль — поток (Callable), использующий шаблон State.
 */
public class Ship implements Callable<Void> {

    private static final Logger log = LogManager.getLogger(Ship.class);

    private final String name;
    private final int capacity;
    private int containersToUnload;
    private int containersToLoad;
    private int loadedContainers = 0;

    private ShipState currentState;
    private Berth assignedBerth;

    public Ship(String name, int capacity, int containersToUnload, int containersToLoad) {
        this.name = name;
        this.capacity = capacity;
        this.containersToUnload = containersToUnload;
        this.containersToLoad = containersToLoad;
        this.currentState = new ArrivingState();
    }

    @Override
    public Void call() throws Exception {
        log.info("=== Корабль [{}] запущен как поток ===", name);
        while (!currentState.isFinal()) {
            currentState.handle(this);
        }
        log.info("=== Корабль [{}] завершил работу ===", name);
        return null;
    }

    // --- Геттеры / сеттеры ---

    public String getName()                 { return name; }
    public int getCapacity()                { return capacity; }
    public int getContainersToUnload()      { return containersToUnload; }
    public int getContainersToLoad()        { return containersToLoad; }
    public int getLoadedContainers()        { return loadedContainers; }
    public Berth getAssignedBerth()         { return assignedBerth; }
    public ShipState getCurrentState()      { return currentState; }

    public void setState(ShipState state)   { this.currentState = state; }
    public void setAssignedBerth(Berth b)   { this.assignedBerth = b; }

    public void removeUnloadedContainer()   { this.containersToUnload--; }
    public void addLoadedContainer()        { this.loadedContainers++; }


}