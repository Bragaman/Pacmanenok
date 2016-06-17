package ru.bragakap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import ru.bragakap.dto.GameInfoDTO;
import ru.bragakap.dto.GameScoreInfoDTO;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Pacman;

public class Server {
    private Socket connectionSocket;
    private ServerSocket welcomeSocket;

    private ObjectOutputStream outToClient = null;
    private ObjectInputStream inFromClient = null;


    public void openConnection(int port) throws IOException {
        System.setProperty("sun.net.useExclusiveBind", "false");
        System.out.println("Server start");
        welcomeSocket = new ServerSocket();
        welcomeSocket.setReuseAddress(true);
        welcomeSocket.bind(new InetSocketAddress(port));
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
        System.out.println(info.getElements().size());
        outToClient.reset();
        outToClient.writeObject(info);
    }

    public void sendMap(List<BaseElement> map) throws IOException {
        outToClient.reset();
        outToClient.writeObject(map);
    }

    public void sendInGameInfo(boolean inGame) throws IOException {
        outToClient.reset();
        outToClient.writeObject(inGame);
    }

    public void sendScore(GameScoreInfoDTO scoreInfoDTO) throws IOException {
        outToClient.reset();
        outToClient.writeObject(scoreInfoDTO);
    }

    public void sendMsg(String msg) throws IOException {
        outToClient.reset();
        outToClient.writeObject(msg);
    }

    public String getMsg() throws IOException, ClassNotFoundException {
        return (String) inFromClient.readObject();
    }

    public void sendObj(Object obj) throws IOException {
        outToClient.reset();
        outToClient.writeObject(obj);
    }

    public Object getObj() throws IOException, ClassNotFoundException {
        return inFromClient.readObject();
    }

    public void close() throws IOException {
        connectionSocket.close();
        welcomeSocket.close();
    }
}
