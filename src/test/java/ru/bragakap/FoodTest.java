package ru.bragakap;

import org.testng.annotations.Test;
import ru.bragakap.elements.Food;
import ru.bragakap.elements.Pacman;


import static org.testng.Assert.*;

public class FoodTest {

    @Test
    public void testIntersection() throws Exception {
        Pacman pacman = new Pacman(0, 0);
        Food food = new Food(0, 0);

        food.intersection(pacman);

        assertEquals(pacman.getScore(), 1);
    }
}