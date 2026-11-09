src/
├── port/
│   ├── Main.java
│   ├── config/
│   │   ├── PortConfig.java
│   │   └── PortConfigReader.java
│   ├── model/
│   │   ├── Berth.java
│   │   ├── Warehouse.java
│   │   └── Ship.java
│   ├── state/
│   │   ├── ShipState.java
│   │   ├── ArrivingState.java
│   │   ├── WaitingForBerthState.java
│   │   ├── UnloadingState.java
│   │   ├── LoadingState.java
│   │   ├── DepartingState.java
│   │   └── DepartedState.java
│   └── singleton/
│       └── Port.java
├── log4j2.xml
└── port_config.txt


12:00:01.123 [pool-1-thread-3] INFO  port.model.Ship - === Корабль [Neptune] запущен как поток ===
12:00:01.125 [pool-1-thread-3] INFO  p.state.ArrivingState - [Neptune] Приближается к порту...
12:00:01.130 [pool-1-thread-1] INFO  p.state.WaitingForBerthState - [Poseidon] Ожидает свободный причал...
12:00:02.140 [pool-1-thread-3] INFO  p.state.WaitingForBerthState - [Neptune] Пришвартовался к причалу №1
12:00:02.145 [pool-1-thread-3] INFO  p.state.UnloadingState - [Neptune] Начинает разгрузку 20 контейнеров...
...
12:00:45.890 [pool-1-thread-5] INFO  port.model.Ship - === Корабль [Titan] завершил работу ===
12:00:45.892 [main] INFO  port.Main - ========================================
12:00:45.893 [main] INFO  port.Main - Все корабли обслужены!
12:00:45.894 [main] INFO  port.Main - Контейнеров на складе: 60/100
12:00:45.895 [main] INFO  port.Main - ========================================
