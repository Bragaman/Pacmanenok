package ru.bragakap;

import java.io.IOException;
import java.util.List;

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
                elements.get(1).move();
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
//                    System.out.print("asd");
                    if (!elements.get(i).isExist() && elements.get(i) instanceof Food) {
                        elements.remove(i);
                        i--;
                    }
                }
            }
        }
        boolean inGame = true;
        if(countOfAlivePlayers == 0) {
            inGame = false;
        }

        for(BaseElement i : elements) {
            i.move();
        }
        if (countOfPlayers == 2) {
            try {
                System.out.println(elements.size());
                System.out.println(elements.get(0).getX());
                Core.getInstance().getServer().sendGameInfo(new GameInfoDTO(inGame, elements));
            } catch (IOException e) {
                //Проблемы с соединением
                return false;
            }
        }
        return inGame;
    }
}
