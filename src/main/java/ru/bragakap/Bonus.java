package ru.bragakap;

import java.io.Serializable;
import java.util.Objects;

import ru.bragakap.elements.Pacman;

public class Bonus implements Serializable {
    protected Integer timeLeft = 100;
    protected Pacman pacman;

    public Bonus(Pacman pacman) {
        this.pacman = pacman;
    }

    public Boolean isActive() {
        return timeLeft != 0;
    }

    public void decrTime() {
        this.timeLeft --;
    }

    public void doAtBegin() {

    }

    public void doAtDeath() {

    }

    public void doOnTick() {

    }

    public void doAtEnd() {

    }
}
