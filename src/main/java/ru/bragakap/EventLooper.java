package ru.bragakap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        if (countOfPlayers == 2) {
            try {
                elements.set(1, Core.getInstance().getServer().getPacmanInfo());
//                elements.get(1).move();
            } catch (IOException | ClassNotFoundException e) {
                return true; //TODO why????
            }
        }

        for(int j = 0; j < countOfPlayers; j++) {
            Pacman player = (Pacman)elements.get(j);
            if(player.isExist())
                countOfAlivePlayers++;
            for (int i = countOfPlayers; i < elements.size(); i++) {
                if (elements.get(i).isInside(player)) {
                    elements.get(i).intersection(player);
                }

                if (!elements.get(i).isExist() && !(elements.get(i) instanceof Pacman)) {
                    elements.remove(i);
                    i--;
                }
            }
        }
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
