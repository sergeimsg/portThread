package edu.melnik.task.port.state.impl;

import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.singleton.Port;
import edu.melnik.task.port.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.TimeUnit;

public class DepartingState implements ShipState {

    private static final Logger log = LogManager.getLogger(DepartingState.class);

    @Override
    public void handle(Ship ship) throws InterruptedException {
        log.info("[{}] Отходит от причала №{}...",
                ship.getName(), ship.getAssignedBerth().getId());

        TimeUnit.SECONDS.sleep(1);

        // Освобождаем причал и семафор
        ship.getAssignedBerth().release();
        Port.getInstance().getBerthSemaphore().release();

        log.info("[{}] Покинул порт.", ship.getName());
        ship.setState(new DepartedState());
    }

    @Override
    public String getName() { return "DEPARTING"; }
}