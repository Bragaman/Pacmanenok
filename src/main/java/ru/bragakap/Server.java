package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    private Socket connectionSocket;

    private ObjectOutputStream outToClient = null;
    private ObjectInputStream inFromClient = null;


    public void openConnection(int port) throws IOException {
        System.out.println("Server start");
        ServerSocket welcomeSocket = new ServerSocket(port);
//
//        while(true)
//        {
            connectionSocket = welcomeSocket.accept();
            inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
//        }
    }

    public Pacman getPacmanInfo() throws IOException, ClassNotFoundException {
        return (Pacman) inFromClient.readObject();
    }

    public void sendGameInfo(GameInfoDTO info) throws IOException {
        outToClient.writeObject(info);
    }

    public void sendScore(GameScoreInfoDTO scoreInfoDTO) throws IOException {
        outToClient.writeObject(scoreInfoDTO);
    }

    public void sendMsg(String msg) throws IOException {
        outToClient.writeObject(msg);
    }

    public String getMsg() throws IOException, ClassNotFoundException {
        return (String) inFromClient.readObject();
    }

    public void sendObj(Object obj) throws IOException {
        outToClient.writeObject(obj);
    }

    public Object getObj() throws IOException, ClassNotFoundException {
        return inFromClient.readObject();
    }

    public void close() throws IOException {
        connectionSocket.close();
    }
}
