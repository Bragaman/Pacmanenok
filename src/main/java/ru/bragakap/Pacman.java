package ru.bragakap;

import java.util.Objects;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * Created by Kapitonenko on 15.01.16.
 */
public class Pacman extends BaseElement{
    public Pacman() {
        setSpeed(1);
    }

    public void setDisplayColor() {
        Device device = Display.getCurrent();
        Color color =  (device.getSystemColor(SWT.COLOR_YELLOW));
        setColor(color);
    }

    private int score = 0;

    public int getScore() {
        return score;
    }

    @Override
    public void intersection(Pacman player) {

    }

    @Override
    public void move() {
        if(this.isExist())
            super.move();
    }

    @Override
    public void paintMe(GC gc) {
        if(this.isExist())
            super.paintMe(gc);
    }


    public void incScore() {
        score++;
    }

    public void moveLeft() {
        setVx(-1* getSpeed());
        setVy(0);
    }

    public void moveRight() {
        setVx(getSpeed());
        setVy(0);
    }

    public void moveDown() {
        setVx(0);
        setVy(getSpeed());
    }

    public void moveUp() {
        setVx(0);
        setVy(-1*getSpeed());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacman)) return false;
        if (!super.equals(o)) return false;
        Pacman pacman = (Pacman) o;
        return Objects.equals(score, pacman.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), score);
    }
}