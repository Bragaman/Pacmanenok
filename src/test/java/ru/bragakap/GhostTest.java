package ru.bragakap;

import org.testng.annotations.Test;
import ru.bragakap.elements.Ghost;
import ru.bragakap.elements.Pacman;


import static org.testng.Assert.*;

public class GhostTest {

    @Test
    public void testIntersection() throws Exception {
        Pacman pacman = new Pacman(0, 0);
        Ghost ghost = new Ghost(0, 0);

        ghost.intersection(pacman);
        assertFalse(pacman.isExist());
    }

    @Test
    public void testMove() throws Exception {
        Ghost ghost = new Ghost(500, 500);

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