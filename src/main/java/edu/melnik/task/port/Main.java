package edu.melnik.task.port;

import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.singleton.Port;

import edu.melnik.task.port.config.PortConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Start");
        try {
            // 1. Чтение конфигурации
            String configFile = "D:\\IT_education\\Blinov_2026\\portThread\\src\\main\\resources\\port_config.txt";
            main.java.edu.melnik.task.port.config.PortConfig config = PortConfigReader.read(configFile);

            logger.info("Конфигурация загружена: склад={}/{}, причалов={}, кораблей={}",
                    config.getWarehouseInitial(),
                    config.getWarehouseCapacity(),
                    config.getBerthsCount(),
                    config.getShips().size());

            // 2. Инициализация синглтона Port
            Port.initialize(
                    config.getWarehouseCapacity(),
                    config.getWarehouseInitial(),
                    config.getBerthsCount()
            );

            // 3. Создание потоков-кораблей (Callable)
            List<Ship> ships = new ArrayList<>();
            for (main.java.edu.melnik.task.port.config.PortConfig.ShipConfig sc : config.getShips()) {
                System.out.println(ships);
                ships.add(new Ship(
                        sc.getName(),
                        sc.getCapacity(),
                        sc.getContainersToUnload(),
                        sc.getContainersToLoad()
                ));
            }

            // 4. Запуск через ExecutorService
            ExecutorService executor = Executors.newFixedThreadPool(ships.size());
            List<Future<Void>> futures = new ArrayList<>();
            for (Ship ship : ships) {
                futures.add(executor.submit(ship));
            }

            // 5. Ожидание завершения всех кораблей
            for (Future<Void> f : futures) {
                f.get();
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            // 6. Вывод итогов
            logger.info("========================================");
            logger.info("Все корабли обслужены!");
            logger.info("Контейнеров на складе: {}/{}",
                    Port.getInstance().getWarehouse().getContainers(),
                    Port.getInstance().getWarehouse().getCapacity());
            logger.info("========================================");

        } catch (Exception e) {
            logger.error("Ошибка выполнения: ", e);
        }
    }
}