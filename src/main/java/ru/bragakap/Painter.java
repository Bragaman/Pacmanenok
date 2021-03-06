package ru.bragakap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.io.IOException;
import java.util.List;
import ru.bragakap.dto.GameInfoDTO;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Pacman;


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
    private boolean waitConnection;
    private Runnable runnable;
    private List<BaseElement> elements;


    private int poz ;

    public Painter(Shell shell, int poz)  {
        super(shell, SWT.NULL);

        waitConnection = true;
        inGame = false;

        this.shell = shell;
        this.poz = poz;
        initPainter();

        startGame(poz);

    }

    /**
     * Добавление слушателей и инициализация общих параметров
     */
    private void initPainter(){
        display = shell.getDisplay();
        addListener(SWT.Paint, this::doPainting);
        addListener(SWT.KeyDown, this::onKeyDown);
        addListener(SWT.Dispose, event -> {
            //// TODO: 15.01.16 действие по Dispose
        });
    }

    private void playGame() {
        System.out.println("play game func call");
        try {
            GameInfoDTO info = Core.getInstance().makeStepAction();
            elements = info.getElements();
            inGame = info.isInGame();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запуск игры
     */
    private void startGame(int poz) {
        StartWindow.WIDTH  = Core.xCount * MapGenerator.SIZE_OBJ;
        StartWindow.HEIGHT = Core.yCount * MapGenerator.SIZE_OBJ;
        forceFocus();
        waitConnection = true;
        inGame = true;
        startConnections();

        runnable = new Runnable() {
                @Override
                public void run() {
                    if (!waitConnection) {
                        if (inGame) playGame();
                    }
                    display.timerExec(DELAY, this);
                    redraw();
                }
            };
            display.timerExec(DELAY, runnable);

            inGame = true;
            redraw();

    }

    private void startConnections() {
        waitConnection = false;
        System.out.println("start init game");
        try {
            Core.getInstance().initMultGame(poz);
            System.out.println("finish init multGame settings");
            elements = Core.getInstance().getElements(poz);
            System.out.println("Set start map");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("server not found");
            waitConnection = true;
        }
        System.out.println("Can start draw");
    }

    /**
     * Обработчик событий нажатия кнопок управления
     * @param e событие нажатия кнопки
     */
    private void onKeyDown(Event e) {
        int key = e.keyCode;
        //todo Определить пакмана

        if(key == SWT.ARROW_LEFT) {
            ((Pacman)elements.get(poz)).moveLeft();
        } else if (key == SWT.ARROW_RIGHT) {
            ((Pacman)elements.get(poz)).moveRight();
        } else if (key == SWT.ARROW_UP) {
            ((Pacman)elements.get(poz)).moveUp();
        } else if (key == SWT.ARROW_DOWN) {
            ((Pacman)elements.get(poz)).moveDown();
        }

        if(key == SWT.SPACE) {
            if (!inGame)
                startGame(poz);
            if (waitConnection) {
                try {
                    Core.getInstance().closeMultGame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }



    }

    /**
     * Отрисовка
     * @param e событие перерисовки
     */
    private void doPainting(Event e) {
//        System.out.println("paint event");
        GC gc = e.gc;

        setBackground(display.getSystemColor(SWT.COLOR_BLACK));

        gc.setAntialias(SWT.ON);

        if (waitConnection) {
            drawWaitPlayer(e);
        } else if(inGame) {
            drawObjects(e);
        } else {
            drawGameOver(e);
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
        int score  = ((Pacman)elements.get(poz)).getScore();
        String scoreString = "Your score: " + score;
        gc.drawText(scoreString, 5, 5);

        int scoreEnemy  = ((Pacman)elements.get((poz+1) %2)).getScore();
        String scoreStringEnemy = "Enemy score: " + scoreEnemy;
        gc.drawText(scoreStringEnemy, 5 + 120, 5);

        if (((Pacman)elements.get(poz)).hasBonuses() ) {
            String bonusString = "IT IS SPAAAARTAAAAAA!!!!";
            gc.drawText(bonusString, 5 + 220, 5);
        }


    }

    /**
     * Отрисовка окончания игры
     * @param e
     */
    private void drawGameOver(Event e) {
        GC gc = e.gc;

        String msg = "Game Over";
        int score  = ((Pacman)elements.get(poz)).getScore();
        String scoreString = "You score: " + score;

        String helpString = " Wait server and for start new game press \"Space\"";

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

    private void drawWaitPlayer(Event e) {
        GC gc = e.gc;

        String msg = "No server found";
//        int score  = ((Pacman)elements.get(0)).getScore();
//        String scoreString = "Score: " + score;

        String helpString = "Please restart application";

        Font font = new Font(e.display, "Helvetica", 18, SWT.NORMAL);
        Color color = new Color(e.display, 255, 255, 255);
        setBackground(display.getSystemColor(SWT.COLOR_BLACK));
        gc.setForeground(color);
        gc.setFont(font);

        Point size = gc.textExtent(msg);

        gc.drawText(msg, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) / 4);
//        gc.drawText(scoreString, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) / 2 );
        gc.drawText(helpString, (StartWindow.WIDTH - size.x) / 3, (StartWindow.HEIGHT - size.y) *3 / 4);

        font.dispose();
        color.dispose();

        display.timerExec(-1, runnable);
    }
}
