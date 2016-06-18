package ru.bragakap.elements;

/**
 * Created by dmitry on 15.01.16.
 */
public class Food extends BaseElement {

    public Food(Integer x, Integer y) {
        super(x, y, "food");
    }

    public void setDisplayColor() {
        setColor(255, 144, 0);
    }

    @Override
    public void intersection(Pacman player) {
        player.incScore();
        this.die();
    }


}

