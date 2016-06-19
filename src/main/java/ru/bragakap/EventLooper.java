package ru.bragakap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ru.bragakap.elements.Aegis;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Pacman;

public class EventLooper {
    public void setElements(List<BaseElement> elements) {
        this.elements = elements;
    }

    public  List<BaseElement> getElements(){
        return elements;
    }
    private List<BaseElement> elements;

    public void setCountOfPlayers(int countOfPlayers) {
        this.countOfPlayers = countOfPlayers;
    }

    private int countOfPlayers;


    public boolean serverLoop()  {
        int countOfAlivePlayers = 0;

//        ExecutorService service = Executors.newCachedThreadPool();

        if (countOfPlayers == 2) {
            try {
                elements.set(1, Core.getInstance().getServer().getPacmanInfo());
//                elements.get(1).move();
            } catch (IOException | ClassNotFoundException e) {
                return true; //TODO why????
            }
        }

        for(int j = 0; j < countOfPlayers; j++) {
            final Pacman player = (Pacman)elements.get(j);
            if(player.isExist())
                countOfAlivePlayers++;
//            service.submit(() -> {
//                synchronized (this) {
                    elements.parallelStream()
                            .skip(countOfPlayers)
                            .filter($ -> $.isInside(player))
                            .forEach($ -> $.intersection(player));
//                }
//            });
        }

//        try {
//            service.shutdown();
//            service.awaitTermination(8, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            e.printStackTrace();
//        }


        elements = elements.parallelStream().filter(element -> element.isExist() || (element instanceof Pacman))
                .collect(Collectors.toList());


        boolean inGame = true;
        if(countOfAlivePlayers == 0) {
            inGame = false;
        }

        List<BaseElement> tempElements = new ArrayList<>();
        for(BaseElement i : elements) {
            i.move();
            i.turn(tempElements);
        }
        elements.addAll(tempElements);
        Random random = new Random();
        int tmp = random.nextInt(1000);
        if (tmp % 100 == 1) {
            Aegis aegis = new Aegis(220, 220);
            elements.add(aegis);
        }
//        elements.add()


        if (countOfPlayers == 2) {
            try {
                Core.getInstance().getServer().sendInGameInfo(inGame);
                Core.getInstance().getServer().sendMap( elements);
            } catch (IOException e) {
                //Проблемы с соединением
                return false;
            }
        }
        return inGame;
    }
}
