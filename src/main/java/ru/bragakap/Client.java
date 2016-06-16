package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void sendPacman(Pacman pacman) throws IOException {
        outToServer.writeObject(pacman);
    }

    public GameInfoDTO getGameInfo() throws IOException, ClassNotFoundException {
        Object ans = inFromServer.readObject();
        if(ans == null) {
            return null;
        }
        return (GameInfoDTO) ans;
    }

    public GameScoreInfoDTO getScore() throws IOException, ClassNotFoundException {
        return (GameScoreInfoDTO) inFromServer.readObject();
    }

    public String getMsg() throws IOException, ClassNotFoundException {
        return (String) inFromServer.readObject();
    }

    public void sendMsg(String msg) throws IOException, ClassNotFoundException {
        outToServer.writeObject(msg);
    }

    public void close() throws IOException {
        clientSocket.close();
    }
}
