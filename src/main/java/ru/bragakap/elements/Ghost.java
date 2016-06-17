package ru.bragakap.elements;

import ru.bragakap.StartWindow;

/**
 * Created by dmitry on 15.01.16.
 */

public class Ghost extends BaseElement {
    static {
        nameOfObject = "ghost";
    }

    public Ghost(Integer x, Integer y) {
        super(x, y);
        setColor(255, 0, 0);
    }

    private int turnToChangeDir = 0;

    @Override
    public void intersection(Pacman player) {

        player.die();
    }

    @Override
    public void move() {
        if(turnToChangeDir == 0) {
            setVx((int) (Math.round(Math.random() * 4) - 2));
            setVy((int) (Math.round(Math.random() * 4) - 2));
            turnToChangeDir =(int) Math.round(Math.random() * 100) + 10;
        } else {
            turnToChangeDir--;
        }
        super.move();

        if(this.getX() <= 0)
            this.setX(0);
        if(this.getY() < 0)
            this.setY(0);
        if(this.getX() > StartWindow.WIDTH - this.getWidth())
            this.setX(StartWindow.WIDTH - this.getWidth());
        if(this.getY() > StartWindow.HEIGHT - this.getHeight())
            this.setY(StartWindow.HEIGHT - this.getHeight());
    }
}
