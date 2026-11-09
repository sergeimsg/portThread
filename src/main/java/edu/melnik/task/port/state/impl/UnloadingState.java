package edu.melnik.task.port.state.impl;

import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.singleton.Port;
import edu.melnik.task.port.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.TimeUnit;

public class UnloadingState implements ShipState {

    private static final Logger log = LogManager.getLogger(UnloadingState.class);

    @Override
    public void handle(Ship ship) throws InterruptedException {
        int toUnload = ship.getContainersToUnload();
        log.info("[{}] Начинает разгрузку {} контейнеров на причале №{}",
                ship.getName(), toUnload, ship.getAssignedBerth().getId());

        for (int i = 0; i < toUnload; i++) {
            Port.getInstance().getWarehouse().addContainer();
            ship.removeUnloadedContainer();
            log.debug("[{}] Разгружен контейнер {}/{}", ship.getName(), i + 1, toUnload);
            TimeUnit.MILLISECONDS.sleep(150); // Имитация работы крана
        }

        log.info("[{}] Разгрузка завершена.", ship.getName());

        if (ship.getContainersToLoad() > 0) {
            ship.setState(new LoadingState());
        } else {
            ship.setState(new DepartingState());
        }
    }

    @Override
    public String getName() { return "UNLOADING"; }
}