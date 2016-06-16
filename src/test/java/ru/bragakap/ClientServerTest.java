package ru.bragakap;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
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
                    server.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        Thread.sleep(100);
        client.open();
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
        food.setY(23);

        List<BaseElement> list = new ArrayList<>();

        list.add(pacman);
        list.add(food);

        server.sendMapInfo(list);
        assertEquals(list, client.getMap());
    }

    @Test
    public void testSendScore() throws Exception {
        GameScoreInfoDTO gameScoreInfoDTO = new GameScoreInfoDTO(12, 42);

        server.sendScore(gameScoreInfoDTO);
        assertEquals(gameScoreInfoDTO, client.getScore());
    }
}
