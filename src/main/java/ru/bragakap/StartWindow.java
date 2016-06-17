package ru.bragakap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import ru.bragakap.util.Settings;

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
//                btnCreateServer.setText("Waiting players");
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
//                btnConnect.setText("Waiting players");
            }
            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {}
        });
    }

    /**
     * Создание компогнентов игрового окна
     */
    private void initGameUI(int poz) {
        preInitUI();

        FillLayout fillLayout = new FillLayout();
        startWindow.setLayout(fillLayout);
        Painter painter = new Painter(startWindow, poz);
    }

    /**
     * Функция установаливает заголовок, размеры и гнепосредственно показывает окно
     */
    void showWindow() {
        startWindow.setText("Pacman");

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
        initGameUI(0);

        startWindow.pack();
        setSize();
    }

    private void onBtnConnectClicked(){
        for (Control kid : startWindow.getChildren()) {
            kid.dispose();
        }
        initGameUI(1);

        startWindow.pack();
        setSize();
        // TODO wait asnwer from server and set size and map from widget
    }


    public static void main(String[] args) {
        if(args.length > 0) {
            Settings.initSettings(args[0]);
        } else {
            Settings.initSettings(null);
        }
        Display display = new Display();
        StartWindow startWindow = new StartWindow(display);
        display.dispose();
    }
}