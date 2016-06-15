package ru.bragakap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.util.List;


/**
 * Created by dmitry on 12.01.16.
 */
public class Painter extends Canvas {

    private static final int DELAY = 8;
//    private final int WIDTH = 900;
//    private final int HEIGHT = 900;


    private Shell shell;
    private Display display;

    private boolean inGame;
    private Runnable runnable;
    private List<BaseElement> elements;

    public Painter(Shell shell) {
        super(shell, SWT.NULL);
        this.shell = shell;

        initPainter();

        startGame();

    }

    /**
     * Добавление слушателей и инициализация общих параметров
     */
    private void initPainter() {
        display = shell.getDisplay();
        addListener(SWT.Paint, this::doPainting);
        addListener(SWT.KeyDown, this::onKeyDown);
        addListener(SWT.Dispose, event -> {
            //// TODO: 15.01.16 действие по Dispose
        });
    }

    /**
     * Запуск игры
     */
    private void startGame() {
        int x = 25;
        int y = 20;
        StartWindow.WIDTH  = x * MapGenerator.SIZE_OBJ;
        StartWindow.HEIGHT = y * MapGenerator.SIZE_OBJ;
        initGame(MapGenerator.generateMap(1, 8, x, y));
    }

    /**
     * Инит самой игры
     * @param elements список элементов в игре
     */
    private void initGame(List<BaseElement> elements) {
        forceFocus();
        this.elements = elements;
        EventLooper eventLooper = new EventLooper();
        eventLooper.setElements(this.elements);
        eventLooper.setCountOfPlayers(1);
        inGame = true;
        runnable = new Runnable() {
            @Override
            public void run() {

                if(inGame) {
                   inGame = eventLooper.loop();
                }
                display.timerExec(DELAY, this);
                redraw();
            }
        };
        display.timerExec(DELAY, runnable);
    }

    /**
     * Обработчик событий нажатия кнопок управления
     * @param e событие нажатия кнопки
     */
    private void onKeyDown(Event e) {
        int key = e.keyCode;
        //todo Определить пакмана

        if(key == SWT.ARROW_LEFT) {
            ((Pacman)elements.get(0)).moveLeft();
        } else if (key == SWT.ARROW_RIGHT) {
            ((Pacman)elements.get(0)).moveRight();
        } else if (key == SWT.ARROW_UP) {
            ((Pacman)elements.get(0)).moveUp();
        } else if (key == SWT.ARROW_DOWN) {
            ((Pacman)elements.get(0)).moveDown();
        }

        if(key == SWT.SPACE)
            if(!inGame)
                startGame();


    }

    /**
     * Отрисовка
     * @param e событие перерисовки
     */
    private void doPainting(Event e) {
        GC gc = e.gc;

        setBackground(display.getSystemColor(SWT.COLOR_BLACK));

        gc.setAntialias(SWT.ON);

        if(inGame) {
            drawObjects(e);
        } else {
            gameOver(e);
        }
    }

    /**
     * Отрисовка игровых элементов
     * @param e
     */
    private void drawObjects(Event e) {
        GC gc =  e.gc;


        for (BaseElement element : elements) {
            element.paintMe(gc);
        }
        int score  = ((Pacman)elements.get(0)).getScore();
        String scoreString = "Score: " + score;
        gc.drawText(scoreString, 5, 5 );
    }

    /**
     * Отрисовка окончания игры
     * @param e
     */
    private void gameOver(Event e) {
        GC gc = e.gc;

        String msg = "Game Over";
        int score  = ((Pacman)elements.get(0)).getScore();
        String scoreString = "Score: " + score;

        String helpString = "For start new game press \"Space\"";

        Font font = new Font(e.display, "Helvetica", 18, SWT.NORMAL);
        Color color = new Color(e.display, 255, 255, 255);
        setBackground(display.getSystemColor(SWT.COLOR_BLACK));
        gc.setForeground(color);
        gc.setFont(font);

        Point size = gc.textExtent(msg);

        gc.drawText(msg, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) / 4);
        gc.drawText(scoreString, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) / 2 );
        gc.drawText(helpString, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) *3 / 4);

        font.dispose();
        color.dispose();

        display.timerExec(-1, runnable);
    }
}
