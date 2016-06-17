package ru.bragakap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;

@Test(groups = {"ClientServerTests"})
public class ClientServerTest {
    Server server;
    Client client;

    @BeforeGroups(groups = "ClientServerTests")
    private void setUp() throws InterruptedException, IOException, ClassNotFoundException {
        server = new Server();
        client = new Client();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    server.openConnection(6789);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        Thread.sleep(100);
        client.open("localhost", 6789);
        server.sendMsg("test");
        System.out.print(client.getMsg());
    }

    @AfterGroups(groups = "ClientServerTests")
    private void tearDown() throws IOException {
        server.close();
    }

    @Test
    public void testSendGetPacman() throws Exception {
        Pacman pacman = new Pacman();
        pacman.setX(42);
        pacman.setY(42);
        client.sendPacman(pacman);
        assertEquals(pacman, server.getPacmanInfo());
    }

    @Test
    public void testSendGetMap() throws Exception {
        Pacman pacman = new Pacman();
        pacman.setX(42);
        pacman.setY(42);

        Food food = new Food();
        food.setX(23);
        food.setY(23);

        List<BaseElement> list = new ArrayList<>();

        list.add(pacman);
        list.add(food);
        GameInfoDTO info = new GameInfoDTO(true, list);
        server.sendGameInfo(info);

        assertEquals(info, client.getGameInfo());
        client.sendPacman(pacman);
        assertEquals(server.getPacmanInfo(), pacman);

        GameScoreInfoDTO gameScoreInfoDTO = new GameScoreInfoDTO(12, 42);

        server.sendScore(gameScoreInfoDTO);
        assertEquals(gameScoreInfoDTO, client.getScore());

        pacman = new Pacman();
        pacman.setX(42);
        pacman.setY(39);

        list = new ArrayList<>();

        list.add(pacman);
        //info = new GameInfoDTO(true, list);
        server.sendObj(list);

        assertEquals(list, (List<BaseElement>) client.getObj());
        client.sendPacman(pacman);
        assertEquals(server.getPacmanInfo(), pacman);
    }

    @Test
    public void testSendScore() throws Exception {
        GameScoreInfoDTO gameScoreInfoDTO = new GameScoreInfoDTO(12, 42);

        server.sendScore(gameScoreInfoDTO);
        assertEquals(gameScoreInfoDTO, client.getScore());
    }

    @Test
    public void testConnection() throws Exception {
        client.sendMsg("connected");
        System.out.print(server.getMsg());
    }
}
