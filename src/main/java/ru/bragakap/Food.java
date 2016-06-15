package ru.bragakap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

/**
 * Created by dmitry on 15.01.16.
 */
public class Food extends BaseElement {
    public Food() {
    }

    public void setDisplayColor() {
        Device device = Display.getCurrent();
        Color red = new Color (device, 255, 144, 0);
        setColor(red);
    }

    @Override
    public void intersection(Pacman player) {
        player.incScore();
        this.die();
    }


}

