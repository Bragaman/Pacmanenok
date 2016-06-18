package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import ru.bragakap.dto.GameInfoDTO;
import ru.bragakap.dto.GameScoreInfoDTO;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Pacman;


public class Client {
    private Socket clientSocket = null;

    private ObjectOutputStream outToServer = null;
    private ObjectInputStream inFromServer = null;

    public void open(String ip, int port) throws IOException, ClassNotFoundException {
        System.out.println("client Open");
        Socket clientSocket = new Socket(ip, port);

        outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("client connected");
    }

    public List<BaseElement> getMap() throws IOException, ClassNotFoundException {
        Object ans = inFromServer.readObject();
        if(ans == null) {
            return null;
        }
        return (List<BaseElement>) ans;
    }

    public boolean getInGameInfo() throws IOException, ClassNotFoundException {
        Object ans = inFromServer.readObject();
        if(ans == null) {
            return false;
        }
        return (boolean) ans;
    }

    public void sendPacman(Pacman pacman) throws IOException {
        outToServer.reset();
        outToServer.writeObject(pacman);
    }

    public GameInfoDTO getGameInfo() throws IOException, ClassNotFoundException {
        Object ans = inFromServer.readObject();
        if(ans == null) {
            return null;
        }
        GameInfoDTO info = (GameInfoDTO) ans;
        System.out.println(info.getElements().size());

        return info;
    }

    public GameScoreInfoDTO getScore() throws IOException, ClassNotFoundException {
        return (GameScoreInfoDTO) inFromServer.readObject();
    }

    public String getMsg() throws IOException, ClassNotFoundException {
        return (String) inFromServer.readObject();
    }

    public void sendMsg(String msg) throws IOException, ClassNotFoundException {
        outToServer.reset();
        outToServer.writeObject(msg);
    }

    public void close() throws IOException {
        clientSocket.close();
    }

    public Object getObj() throws IOException, ClassNotFoundException {
        return inFromServer.readObject();
    }
}
