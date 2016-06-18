package ru.bragakap.elements;

import java.util.List;

public class Feeder extends Ghost {

    private Integer timeoutOfFeed = 30;

    public Feeder(Integer x, Integer y) {
        super(x, y, "feeder");
        setColor(255, 255, 255);
    }

    @Override
    public void turn(List<BaseElement> elements) {
        if(timeoutOfFeed == 0) {
            Food food = new Food(getX(), getY());
            food.setDisplayColor();

            elements.add(food);
            timeoutOfFeed = 30;
        } else {
            timeoutOfFeed--;
        }
    }
}
