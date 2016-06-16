package ru.bragakap;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by dmbragin on 16/06/16.
 */
public class GameInfoDTO implements Serializable {
    private List<BaseElement> elements;
    private boolean inGame;

    private static final long serialVersionUID = 1L;

    public List<BaseElement> getElements() {
        return elements;
    }

    public  boolean isInGame() { return inGame;}

    public GameInfoDTO(boolean inGame, List<BaseElement> elements) {
        this.inGame = inGame;
        this.elements = elements;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInfoDTO)) return false;
        GameInfoDTO that = (GameInfoDTO) o;
        return Objects.equals(elements, that.getElements()) &&
                Objects.equals(inGame, that.isInGame());
    }

    @Override
    public int hashCode() {
        return Objects.hash(inGame, elements);
    }

}
