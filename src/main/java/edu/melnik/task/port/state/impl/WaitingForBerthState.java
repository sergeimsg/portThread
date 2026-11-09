package edu.melnik.task.port.state.impl;

import edu.melnik.task.port.model.Berth;
import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.singleton.Port;
import edu.melnik.task.port.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.TimeUnit;

public class WaitingForBerthState implements ShipState {

    private static final Logger log = LogManager.getLogger(WaitingForBerthState.class);

    @Override
    public void handle(Ship ship) throws InterruptedException {
        Port port = Port.getInstance();
        log.info("[{}] Ожидает свободный причал...", ship.getName());

        // Ждём разрешение войти в порт (ограничение по числу причалов)
        port.getBerthSemaphore().acquire();

        // Ищем свободный причал
        Berth assigned = null;
        while (assigned == null) {
            for (Berth berth : port.getBerths()) {
                if (berth.tryAcquire()) {
                    assigned = berth;
                    break;
                }
            }
            if (assigned == null) {
                TimeUnit.MILLISECONDS.sleep(50);
            }
        }

        ship.setAssignedBerth(assigned);
        log.info("[{}] Пришвартовался к причалу №{}", ship.getName(), assigned.getId());

        // Определяем следующее состояние
        if (ship.getContainersToUnload() > 0) {
            ship.setState(new UnloadingState());
        } else if (ship.getContainersToLoad() > 0) {
            ship.setState(new LoadingState());
        } else {
            ship.setState(new DepartingState());
        }
    }

    @Override
    public String getName() { return "WAITING_FOR_BERTH"; }
}