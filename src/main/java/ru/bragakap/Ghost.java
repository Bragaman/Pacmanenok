package ru.bragakap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

/**
 * Created by dmitry on 15.01.16.
 */

public class Ghost extends BaseElement {
    public Ghost() {
        super();
        Device device = Display.getCurrent ();
        Color red = new Color (device, 255, 0, 0);
        setColor(red);
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
