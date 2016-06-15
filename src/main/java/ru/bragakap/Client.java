package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket clientSocket = null;

    private ObjectOutputStream outToServer = null;
    private ObjectInputStream inFromServer = null;

    public void open() throws IOException, ClassNotFoundException {
        System.out.println("client Open");
        Socket clientSocket = new Socket("localhost", 6789);

        outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        inFromServer = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void sendPacman(Pacman pacman) throws IOException {
        outToServer.writeObject(pacman);
    }

    public List<BaseElement> getMap() throws IOException, ClassNotFoundException {
        Object ans = inFromServer.readObject();
        if(ans == null) {
            return null;
        }
        return (List<BaseElement>) ans;
    }

    public GameScoreInfoDTO getScore() throws IOException, ClassNotFoundException {
        return (GameScoreInfoDTO) inFromServer.readObject();
    }

    public void close() throws IOException {
        clientSocket.close();
    }
}
