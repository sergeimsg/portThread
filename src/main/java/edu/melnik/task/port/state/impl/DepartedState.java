package edu.melnik.task.port.state.impl;


import edu.melnik.task.port.model.Ship;
import edu.melnik.task.port.state.ShipState;

public class DepartedState implements ShipState {

    @Override
    public void handle(Ship ship) {
        // Финальное состояние — ничего не делаем
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public String getName() { return "DEPARTED"; }
}