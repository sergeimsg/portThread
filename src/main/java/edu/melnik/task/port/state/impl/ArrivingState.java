package edu.melnik.task.port.state.impl;

import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.TimeUnit;

public class ArrivingState implements ShipState {

    private static final Logger log = LogManager.getLogger(ArrivingState.class);

    @Override
    public void handle(Ship ship) throws InterruptedException {
        log.info("[{}] Приближается к порту...", ship.getName());
        TimeUnit.SECONDS.sleep(1);
        log.info("[{}] Достиг порта.", ship.getName());
        ship.setState(new WaitingForBerthState());
    }

    @Override
    public String getName() { return "ARRIVING"; }
}