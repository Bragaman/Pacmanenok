package ru.bragakap;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BonusManager implements Serializable {
    private Set<Bonus> activeBonuses = new HashSet<>();

    private Integer timeOfBonus;

    public void addBonus(Bonus bonus) {
        bonus.doAtBegin();
        activeBonuses.add(bonus);
    }

    public void tick() {
        for(Bonus bonus : activeBonuses) {
            bonus.decrTime();
            if(bonus.isActive()) {
                bonus.doOnTick();
            } else {
                bonus.doAtEnd();
                activeBonuses.remove(bonus);
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
