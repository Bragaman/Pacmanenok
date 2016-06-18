package ru.bragakap.elements;

import java.util.Objects;
import org.eclipse.swt.graphics.GC;
import ru.bragakap.BonusManager;

/**
 * Created by Kapitonenko on 15.01.16.
 */
public class Pacman extends BaseElement {

    private BonusManager bonusManager = new BonusManager();

    public Pacman(Integer x, Integer y) {
        super(x, y, "pacman");
        setSpeed(1);

    }

    public BonusManager getBonusManager() {
        return bonusManager;
    }

    public void setBonusManager(BonusManager bonusManager) {
        this.bonusManager = bonusManager;
    }

    public void setDisplayColor() {
        setColor(150,150,0);
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
        if(this.isExist()) {
            super.move();
            bonusManager.tick();
        }
    }

    @Override
    public void paintMe(GC gc) {
        if(this.isExist())
            super.paintMe(gc);
    }

    @Override
    public void die() {
        super.die();
        bonusManager.deathOfPacman();
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