package ru.bragakap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import ru.bragakap.dto.GameInfoDTO;
import ru.bragakap.dto.GameScoreInfoDTO;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Food;
import ru.bragakap.elements.Pacman;


import static org.testng.Assert.assertEquals;

@Test(groups = {"ClientServerTests"})
public class ClientServerTest {
    Server server;
    Client client;

    @BeforeGroups(groups = "ClientServerTests")
    private void setUp() throws Exception {
        server = new Server();
        client = new Client();

        openConnections(6789);
    }

    @AfterGroups(groups = "ClientServerTests")
    private void tearDown() throws IOException {
        server.close();
    }

    @Test
    public void testSendGetPacman() throws Exception {
        Pacman pacman = new Pacman(42, 42);
        client.sendPacman(pacman);
        assertEquals(pacman, server.getPacmanInfo());
    }

    @Test
    public void testSendGetMap() throws Exception {
        Pacman pacman = new Pacman(42, 42);

        Food food = new Food(23, 23);

        List<BaseElement> list = new ArrayList<>();

        list.add(pacman);
        list.add(food);
        GameInfoDTO info = new GameInfoDTO(true, list);
        server.sendGameInfo(info);

        assertEquals(info, client.getGameInfo());
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

    private void openConnections(int port) throws InterruptedException, IOException, ClassNotFoundException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    server.openConnection(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        Thread.sleep(100);
        client.open("localhost", port);
    }
}
