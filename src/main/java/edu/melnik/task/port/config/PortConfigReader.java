package edu.melnik.task.port.config;

import main.java.edu.melnik.task.port.config.PortConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Читает данные инициализации из текстового файла.
 */
public class PortConfigReader {

    private static final Logger logger = LogManager.getLogger();

    public static PortConfig read(String filename) throws IOException {

        int warehouseCapacity = 0;
        int warehouseInitial = 0;
        int berthsCount = 0;
        List<PortConfig.ShipConfig> ships = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                line = line.trim();
                logger.info(line);
//                System.out.println(line);
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("warehouse_capacity=")) {
                    warehouseCapacity = parseInt(line, "warehouse_capacity=");
                } else if (line.startsWith("warehouse_initial=")) {
                    warehouseInitial = parseInt(line, "warehouse_initial=");
                } else if (line.startsWith("berths=")) {
                    berthsCount = parseInt(line, "berths=");
                } else if (line.startsWith("ship:")) {
                    ships.add(parseShip(line));
                }
            }
        }
        return new PortConfig(warehouseCapacity, warehouseInitial, berthsCount, ships);
    }

    private static int parseInt(String line, String prefix) {
        return Integer.parseInt(line.substring(prefix.length()).trim());
    }

    private static PortConfig.ShipConfig parseShip(String line) {
        // ship:Name:capacity:unload:load
        String[] parts = line.split(":");
        System.out.println(Arrays.toString(parts));
        return new PortConfig.ShipConfig(
                parts[1].trim(),
                Integer.parseInt(parts[2].trim()),
                Integer.parseInt(parts[3].trim()),
                Integer.parseInt(parts[4].trim())
        );
    }
}