package ru.bragakap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * Created by dmitry on 15.01.16.
 */

public class Wall extends BaseElement {
    public Wall() {
        Device device = Display.getCurrent ();
        Color red = new Color (device, 144, 0, 144);
        setColor(red);
    }

    @Override
    public void intersection(Pacman player) {
        if(player.getVx() > 0)
            player.setX(player.getX() - 1);
        if(player.getVx() < 0)
            player.setX(player.getX() + 1);
        if(player.getVy() > 0)
            player.setY(player.getY() - 2);
        if(player.getVy() < 0)
            player.setY(player.getY() + 2);
        player.setVx(0);
        player.setVy(0);
    }

    @Override
    public void paintMe(GC gc) {
        gc.setBackground(getColor());
        gc.fillRectangle(getX(), getY(), getWidth(), getHeight());
    }
}