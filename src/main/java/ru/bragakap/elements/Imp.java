package ru.bragakap.elements;

import java.util.List;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Fireball;
import ru.bragakap.elements.Ghost;

public class Imp extends Ghost {
    private Integer timeoutOfShoot = 10;


    protected String nameOfObject = "imp";

    public Imp(Integer x, Integer y) {
        super(x, y);
        setColor(255, 0, 255);
    }

    @Override
    public void turn(List<BaseElement> elements) {
        if(timeoutOfShoot == 0) {
            Fireball fireball = new Fireball(this.getX(), this.getY());
            elements.add(fireball);
            timeoutOfShoot = 10;
        } else {
            timeoutOfShoot--;
        }
    }
}
