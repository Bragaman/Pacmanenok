package ru.bragakap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by dmbragin on 16/06/16.
 */
public class Core {
    private static Core ourInstance = new Core();

    public static Core getInstance() {
        return ourInstance;
    }
    public final static int xCount = 15;
    public final static int yCount = 15;

    private boolean connectingNow = false;

    private EventLooper eventLooper = null;
    private Server server = null;
    private Client client = null;
    private List<BaseElement> elements;
    private int indexClient = 1;
    private int indexServer = 0;

    public List<BaseElement> getElements(int index) {
        if (index == indexServer) {
            return eventLooper.getElements();
        } else {
            return elements;
        }
    }

    public void initMultGame(int index) throws IOException, ClassNotFoundException {

        if (!connectingNow) {
            connectingNow = true;
            if (index == indexServer) {
                initServerPlayer();
            } else {
                initClientPlayer();
            }

        }
    }

    public void closeMultGame() throws IOException {
        if (server != null) {
            server.close();
            server = null; //TODO can I write so?
         }
        if (client != null) {
            client.close();
            client = null;
        }
        connectingNow = false;
    }

    private void initServerPlayer() throws IOException {
        server = new Server();
        server.openConnection(6789);
        eventLooper = new EventLooper();
        eventLooper.setElements(MapGenerator.generateMap(2, 0, xCount, yCount));
        eventLooper.setCountOfPlayers(2);
        server.sendGameInfo(new GameInfoDTO(true, eventLooper.getElements()));
    }

    private void initClientPlayer() throws IOException, ClassNotFoundException {
        client = new Client();
        client.open("localhost", 6789);
        GameInfoDTO info = client.getGameInfo();
        elements = info.getElements();
    }

    public GameInfoDTO makeStepAction() throws IOException, ClassNotFoundException {
        GameInfoDTO info;
        if (eventLooper != null) {
            // it is server
            boolean inGame = eventLooper.serverLoop();
            info =  new GameInfoDTO(inGame, eventLooper.getElements());
        } else {
//            System.out.println(elements.get(indexClient).getVx());
            Pacman pacman = (Pacman) elements.get(indexClient);
//            Random r = new Random();
//            int x = r.nextInt(100);
//            if (x%10 == 9)
//                pacman.setX(x);
            client.sendPacman(pacman);
            info = client.getGameInfo();
            System.out.println(info.getElements().get(0).getX());
            elements = info.getElements();
        }
        if (!info.isInGame())
            closeMultGame();
        return info;
    }

    private Core() {
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
