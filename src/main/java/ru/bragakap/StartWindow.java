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

        Button btnCreateServer = new Button(startWindow, SWT.PUSH);
        btnCreateServer.setText("Create game server");
        btnCreateServer.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                onBtnCreateServerClicked();
            }
            public void widgetDefaultSelected(SelectionEvent event) {}
        });

        Button btnConnect = new Button(startWindow, SWT.PUSH);
        btnConnect.setText("Connect to server");
        btnConnect.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                onBtnConnectClicked();
            }
            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {}
        });
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

    private void onBtnCreateServerClicked() {
        for (Control kid : startWindow.getChildren()) {
            kid.dispose();
        }
        initGameUI();

        startWindow.pack();
        setSize();
    }

    private void onBtnConnectClicked() {
        // TODO wait asnwer from server and set size and map from widget
    }


    public static void main(String[] args) {
        Display display = new Display();
        StartWindow startWindow = new StartWindow(display);
        display.dispose();
    }
}