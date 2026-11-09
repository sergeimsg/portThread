package main.java.edu.melnik.task.port.config;


import java.util.List;

/**
 * POJO-класс, хранящий параметры порта и список кораблей.
 */
public class PortConfig {
    private final int warehouseCapacity;
    private final int warehouseInitial;
    private final int berthsCount;
    private final List<ShipConfig> ships;

    public PortConfig(int warehouseCapacity, int warehouseInitial,
                      int berthsCount, List<ShipConfig> ships) {
        this.warehouseCapacity = warehouseCapacity;
        this.warehouseInitial = warehouseInitial;
        this.berthsCount = berthsCount;
        this.ships = ships;
    }

    public int getWarehouseCapacity()      { return warehouseCapacity; }
    public int getWarehouseInitial()       { return warehouseInitial; }
    public int getBerthsCount()            { return berthsCount; }
    public List<ShipConfig> getShips()     { return ships; }

    /**
     * Описание одного корабля.
     */
    public static class ShipConfig {
        private final String name;
        private final int capacity;
        private final int containersToUnload;
        private final int containersToLoad;

        public ShipConfig(String name, int capacity,
                          int containersToUnload, int containersToLoad) {
            this.name = name;
            this.capacity = capacity;
            this.containersToUnload = containersToUnload;
            this.containersToLoad = containersToLoad;
        }

        public String getName()                { return name; }
        public int getCapacity()               { return capacity; }
        public int getContainersToUnload()     { return containersToUnload; }
        public int getContainersToLoad()       { return containersToLoad; }
    }
}
