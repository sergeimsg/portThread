package edu.melnik.task.port.state;

import edu.melnik.task.port.model.Ship;

/**
 * Интерфейс состояния корабля (шаблон State).
 */
public interface ShipState {
    /**
     * Выполнить действие, соответствующее состоянию,
     * и перевести корабль в следующее состояние.
     */
    void handle(Ship ship) throws InterruptedException;

    /**
     * Является ли состояние финальным.
     */
    default boolean isFinal() {
        return false;
    }

    String getName();
}