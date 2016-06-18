package ru.bragakap;

import java.util.ArrayList;
import java.util.List;
import ru.bragakap.elements.Aegis;
import ru.bragakap.elements.BaseElement;
import ru.bragakap.elements.Feeder;
import ru.bragakap.elements.Food;
import ru.bragakap.elements.Ghost;
import ru.bragakap.elements.Imp;
import ru.bragakap.elements.Pacman;
import ru.bragakap.elements.Wall;

/**
 * Created by Kapitonenko (Щечки) on 25.12.15.
 */
public class MapGenerator {

    public static final int SIZE_OBJ = 30;
    private static int mapY;
    private static int mapX;

    static List<BaseElement> generateMap(int countOfPlayer, int countOfGhost, int xSize, int ySize) {

        List<BaseElement> list = new ArrayList<BaseElement>();
        mapX = xSize;
        mapY = ySize;

        int sizeFood = 5;

        for(int i=0; i < countOfPlayer; ++i) {
            Pacman player = new Pacman(76 * (i+1), 76 * (i+1));
            player.setColor(0, (130 + 75 * (i)) % 255, 0);
            list.add( player);
        }

        for(int i = 1; i < mapX - 1; i++) {
            for(int j = 1; j < mapY - 1; j++) {
                Food food = new Food(i * SIZE_OBJ + SIZE_OBJ/2 - sizeFood/2, j * SIZE_OBJ + SIZE_OBJ/2 - sizeFood/2);
                food.setDisplayColor();
                list.add(food);
            }
        }

        createMaze(list);

        for(int i = 0; i < countOfGhost; i++) {
            Ghost ghost = new Ghost(mapX / 2 * SIZE_OBJ, mapY / 2 * SIZE_OBJ);
            list.add(ghost);
        }

        Imp imp = new Imp(mapX / 2 * SIZE_OBJ, mapY / 2 * SIZE_OBJ);
        list.add(imp);

        Feeder feeder = new Feeder(mapX / 2 * SIZE_OBJ, mapY / 2 * SIZE_OBJ);
        list.add(feeder);

        Aegis aegis = new Aegis(50, 50);
        list.add(aegis);

        return list;
    }

    /**
     * Создание комнаты
     * @param x координаты левого вернего угла
     * @param y координаты левого вернего угла
     * @param width ширина комнаты = пиксели / wallSize
     * @param height высота комнаты в штуках стенок
     * @param wallSize размер стенки
     * @param list OUT
     */
    public static void createRoom(int x, int y, int width, int height, int wallSize, List<BaseElement> list) {
        for(int i = x; i <x + width + 1; i++) {
            Wall wall = new Wall(i * wallSize, y * wallSize);
            list.add(wall);
        }
        for(int i = x; i < x + width + 1; i++) {
            Wall wall = new Wall(i * wallSize, (y  + height) * wallSize);
            list.add(wall);
        }
        for(int i = (y); i < y + (height); i++) {
            Wall wall = new Wall(x * wallSize, i * wallSize);
            list.add(wall);
        }
        for(int i = (y); i < y + (height); i++) {
            Wall wall = new Wall((x + width) * wallSize, i * wallSize);
            list.add(wall);
        }
    }

    /**
     * Создание лабиринта
     * @param list
     */
    public static void createMaze(List<BaseElement> list) {
        createRoom(0, 0, mapX - 1, mapY - 1, SIZE_OBJ, list);
        for (int j = 0; j < mapY-4; j += 6) {
            for (int i = 0; i < mapX-4; i += 6) {
                createRoom(i, j, 4, 4, SIZE_OBJ, list);
                //todo create maze
            }
        }

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Wall && list.get(i).getY() % 3 == 0 && list.get(i).getY() != 0 &&
            list.get(i).getY() != (mapY -1) * SIZE_OBJ && list.get(i).getX() != 0
                    && list.get(i).getX() != (mapX-1) * SIZE_OBJ)
            list.remove(i);
        }
    }
}

