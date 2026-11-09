package edu.melnik.task.port.state.impl;

import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.singleton.Port;
import edu.melnik.task.port.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.TimeUnit;

public class LoadingState implements ShipState {

    private static final Logger log = LogManager.getLogger(LoadingState.class);

    @Override
    public void handle(Ship ship) throws InterruptedException {
        int toLoad = ship.getContainersToLoad();
        log.info("[{}] Начинает загрузку {} контейнеров на причале №{}",
                ship.getName(), toLoad, ship.getAssignedBerth().getId());

        for (int i = 0; i < toLoad; i++) {
            Port.getInstance().getWarehouse().removeContainer();
            ship.addLoadedContainer();
            log.debug("[{}] Загружен контейнер {}/{}", ship.getName(), i + 1, toLoad);
            TimeUnit.MILLISECONDS.sleep(150);
        }

        log.info("[{}] Загрузка завершена.", ship.getName());
        ship.setState(new DepartingState());
    }

    @Override
    public String getName() { return "LOADING"; }
}