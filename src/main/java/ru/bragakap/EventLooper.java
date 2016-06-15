package ru.bragakap;

import java.io.IOException;
import java.util.List;

public class EventLooper {
    public void setElements(List<BaseElement> elements) {
        this.elements = elements;
    }

    private List<BaseElement> elements;

    private Server server;


    public void setCountOfPlayers(int countOfPlayers) {
        this.countOfPlayers = countOfPlayers;
    }

    private int countOfPlayers;

    public boolean loop()  {
        int countOfAlivePlayers = 0;
        for(int j = 0; j < countOfPlayers; j++) {
            Pacman player = (Pacman)elements.get(j);
            if(player.isExist())
                countOfAlivePlayers++;
            for (int i = countOfPlayers; i < elements.size(); i++) {
                if (elements.get(i).isInside(player)) {
                    elements.get(i).intersection(player);
//                    System.out.print("asd");
                    if (!elements.get(i).isExist() && elements.get(i) instanceof Food) {
                        elements.remove(i);
                        i--;
                    }

                }
            }
        }

        if(countOfAlivePlayers == 0) {
            return false;
        }

        for(BaseElement i : elements) {
            i.move();
        }

        try {
            elements.set(1, server.getPacmanInfo());
        } catch (IOException | ClassNotFoundException e) {
            return true;
        }

        try {
            server.sendMapInfo(elements);
        } catch (IOException e) {
            //Проблемы с соединением
            return false;
        }

        return true;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
