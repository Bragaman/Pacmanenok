package ru.bragakap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class Aegis extends BaseElement {
    public Aegis() {
        super();
        setColor(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
    }

    @Override
    public void intersection(Pacman player) {
        //todo Kap
    }

}
