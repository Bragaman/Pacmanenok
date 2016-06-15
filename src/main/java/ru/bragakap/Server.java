package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private Socket connectionSocket;

    private ObjectOutputStream outToClient = null;
    private ObjectInputStream inFromClient = null;

    public void openConnection() throws IOException {
        System.out.println("Server start");
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true)
        {
            connectionSocket = welcomeSocket.accept();
            inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        }
    }

    public Pacman getPacmanInfo() throws IOException, ClassNotFoundException {
        return (Pacman) inFromClient.readObject();
    }

    public void sendMapInfo(List<BaseElement> elements) throws IOException {
        outToClient.writeObject(elements);
    }

    public void sendScore(GameScoreInfoDTO scoreInfoDTO) throws IOException {
        outToClient.writeObject(scoreInfoDTO);
    }

    public void close() throws IOException {
        connectionSocket.close();
    }
}
