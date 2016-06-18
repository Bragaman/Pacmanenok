package ru.bragakap.elements;

import ru.bragakap.bonuses.Immortal;

public class Aegis extends BaseElement {

    public Aegis(Integer x, Integer y) {
        super(x, y, "aegis");
        setColor(100,50,25);
    }

    @Override
    public void intersection(Pacman player) {
        player.getBonusManager().addBonus(new Immortal(player));
        die();
    }

}
