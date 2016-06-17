package ru.bragakap.elements;

import java.util.List;

public class Fireball extends BaseElement {
    private Integer durationOfLive = 50;

    static {
        nameOfObject = "fireball";
    }

    public Fireball(Integer x, Integer y) {
        super(x, y);
        setDisplayColor();

        setVx((int) (Math.round(Math.random() * 2) - 2) * 2);
        setVy((int) (Math.round(Math.random() * 2) - 2) * 2);
    }

    public void setDisplayColor() {
        setColor(255, 0, 0);
    }

    @Override
    public void intersection(Pacman player) {
        player.die();
    }

    @Override
    public void turn(List<BaseElement> elements) {
        if(durationOfLive == 0) {
            die();
        } else {
            durationOfLive--;
        }
    }
}
