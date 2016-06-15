package ru.bragakap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Created by dmitry on 12.01.16.
 */
public class StartWindow {
    public static int WIDTH = 400;
    public static int HEIGHT = 300;
    private Shell startWindow;
    private Display display;

    /**
     * Конструктор стартового окна
     * @param display
     */
    public StartWindow(Display display) {
        this.display = display;

        startWindow =  new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        initMenuUI();
        showWindow();
    }

    /**
     * удаление старого содержимого виджета
     */
    private void preInitUI() {
        for (Control kid : startWindow.getChildren()) {
            kid.dispose();
        }
    }

    /**
     * Создание компонентов видимого стартового окна
     */
    private void initMenuUI() {
        preInitUI();

        FillLayout fillLayout = new FillLayout();
        startWindow.setLayout(fillLayout);

        Button startSingleGame = new Button(startWindow, SWT.PUSH);
        startSingleGame.setText("Start new single game");
        startSingleGame.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                onStartSingleGameClicked();
            }
            public void widgetDefaultSelected(SelectionEvent event) {}
        });

        Button startMultGame = new Button(startWindow, SWT.PUSH);
        startMultGame.setText("Start new multiple game");
    }

    /**
     * Создание компогнентов игрового окна
     */
    private void initGameUI() {
        preInitUI();

        FillLayout fillLayout = new FillLayout();
        startWindow.setLayout(fillLayout);
        Painter painter = new Painter(startWindow);

    }

    /**
     * Функция установаливает заголовок, размеры и гнепосредственно показывает окно
     */
    void showWindow() {
        startWindow.setText("ru.bragakap.Pacman");

        setSize();

        startWindow.open();

        while (!startWindow.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private void setSize() {
        int borW = startWindow.getSize().x - startWindow.getClientArea().width;
        int borH = startWindow.getSize().y - startWindow.getClientArea().height;
        startWindow.setSize(WIDTH + borW, HEIGHT + borH);
    }

    private void onStartSingleGameClicked() {
        for (Control kid : startWindow.getChildren()) {
            kid.dispose();
        }
        initGameUI();

        startWindow.pack();
        setSize();
    }


    public static void main(String[] args) {

        Display display = new Display();
        StartWindow startWindow = new StartWindow(display);
        display.dispose();
    }
}