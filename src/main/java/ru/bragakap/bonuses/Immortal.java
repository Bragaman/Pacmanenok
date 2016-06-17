package ru.bragakap.bonuses;

import ru.bragakap.Bonus;
import ru.bragakap.elements.Pacman;

public class Immortal extends Bonus {

    public Immortal(Pacman pacman) {
        super(pacman);
    }

    @Override
    public void doAtDeath() {
        pacman.ressurect();
    }
}
