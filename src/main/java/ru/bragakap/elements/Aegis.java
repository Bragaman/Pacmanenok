package ru.bragakap.elements;

import ru.bragakap.bonuses.Immortal;

public class Aegis extends BaseElement {
    static {
        nameOfObject = "aegis";
    }

    public Aegis(Integer x, Integer y) {
        super(x, y);
        setColor(100,50,25);
    }

    @Override
    public void intersection(Pacman player) {
        player.getBonusManager().addBonus(new Immortal(player));
        die();
    }

}
