package ru.bragakap;

import org.junit.Assert;
import org.testng.annotations.Test;


import static org.testng.Assert.*;

public class GhostTest {

    @Test
    public void testIntersection() throws Exception {
        Pacman pacman = new Pacman();
        Ghost ghost = new Ghost();

        ghost.intersection(pacman);
        assertFalse(pacman.isExist());
    }

    @Test
    public void testMove() throws Exception {
        Ghost ghost = new Ghost();
        ghost.setX(500);
        ghost.setY(500);

        Integer maxVx = 0;
        Integer minVx = 0;

        Integer maxVy = 0;
        Integer minVy = 0;

        for(int i = 0; i < 100000; i++) {
            ghost.move();

            maxVx = maxVx < ghost.getVx() ? ghost.getVx() : maxVx;
            maxVy = maxVy < ghost.getVy() ? ghost.getVy() : maxVy;
            minVx = minVx > ghost.getVx() ? ghost.getVx() : minVx;
            minVy = minVy > ghost.getVy() ? ghost.getVy() : minVy;
        }

        System.out.println(maxVx);

        assertTrue(maxVx <= 2 && maxVy <= 2 && minVx >= -2 && minVy >= -2);
    }
}