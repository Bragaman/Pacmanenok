package ru.bragakap;

import org.testng.annotations.Test;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Pacman;


import static org.testng.Assert.*;

public class BaseElementTest {

    @Test
    public void testDieAndExist() throws Exception {
        BaseElement baseElement = createBaseElement();

        assertTrue(baseElement.isExist());

        baseElement.die();

        assertFalse(baseElement.isExist());
    }

    @Test
    public void testIsInsideShouldBeInside() throws Exception {
        BaseElement baseElement = createBaseElement();

        baseElement.setX(42);
        baseElement.setY(42);

        baseElement.setHeight(30);
        baseElement.setWidth(30);

        BaseElement baseElementInside = createBaseElement();

        baseElementInside.setX(44);
        baseElementInside.setY(44);

        baseElementInside.setHeight(3);
        baseElementInside.setWidth(3);

        assertTrue(baseElementInside.isInside(baseElement));
    }

    @Test
    public void testIsInsideShouldNotBeInside() throws Exception {
        BaseElement baseElement = createBaseElement();

        baseElement.setX(42);
        baseElement.setY(42);

        baseElement.setHeight(1);
        baseElement.setWidth(1);

        BaseElement baseElementInside = createBaseElement();

        baseElementInside.setX(50);
        baseElementInside.setY(50);

        baseElementInside.setHeight(3);
        baseElementInside.setWidth(3);

        assertFalse(baseElementInside.isInside(baseElement));
    }

    @Test
    public void testMove() throws Exception {
        BaseElement baseElement = createBaseElement();

        baseElement.setX(42);
        baseElement.setY(42);

        baseElement.setVx(1);
        baseElement.setVy(-1);

        baseElement.move();

        assertEquals(baseElement.getX(), 43);
        assertEquals(baseElement.getY(), 41);
    }

    private BaseElement createBaseElement() {
        return new BaseElement(0, 0) {
            @Override
            public void intersection(Pacman player) {

            }
        };
    }
}