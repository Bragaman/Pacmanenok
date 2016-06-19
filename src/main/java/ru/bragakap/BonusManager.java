package ru.bragakap;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class BonusManager implements Serializable {
    private List<Bonus> activeBonuses = new ArrayList<>();

    private Integer timeOfBonus;

    public void addBonus(Bonus bonus) {
        bonus.doAtBegin();
        activeBonuses.add(bonus);
    }

    public void tick() {
        Iterator<Bonus> iterator = activeBonuses.iterator();
        while (iterator.hasNext()){
            Bonus bonus = iterator.next();
            bonus.decrTime();
            if(bonus.isActive()) {
                bonus.doOnTick();
            } else {
                bonus.doAtEnd();
                iterator.remove();
            }
        }
    }

    public void deathOfPacman() {
        for(Bonus bonus : activeBonuses) {
            bonus.decrTime();
            if(bonus.isActive()) {
                bonus.doAtDeath();
            } else {
                bonus.doAtEnd();
                activeBonuses.remove(bonus);
            }
        }
    }

    public boolean hasBonuses() {
        return !activeBonuses.isEmpty();
    }
}
