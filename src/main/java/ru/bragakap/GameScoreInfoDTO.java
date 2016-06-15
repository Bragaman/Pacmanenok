package ru.bragakap;

import java.io.Serializable;
import java.util.Objects;

public class GameScoreInfoDTO implements Serializable {
    private Integer firstPlayerScore;
    private Integer secondPlayerScore;

    public GameScoreInfoDTO(Integer firstPlayerScore, Integer secondPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public Integer getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(Integer firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public Integer getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(Integer secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameScoreInfoDTO)) return false;
        GameScoreInfoDTO that = (GameScoreInfoDTO) o;
        return Objects.equals(firstPlayerScore, that.firstPlayerScore) &&
                Objects.equals(secondPlayerScore, that.secondPlayerScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayerScore, secondPlayerScore);
    }
}
