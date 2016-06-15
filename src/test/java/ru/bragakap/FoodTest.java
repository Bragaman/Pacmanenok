package ru.bragakap;

import org.testng.annotations.Test;


import static org.testng.Assert.*;

public class FoodTest {

    @Test
    public void testIntersection() throws Exception {
        Pacman pacman = new Pacman();
        Food food = new Food();

        food.intersection(pacman);

        assertEquals(pacman.getScore(), 1);
    }
}