package com.mygdx.l5rdraft.cards;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class PackFactory {

    public static Pack createPack(List<Card> cards) {
        Pack ret = new Pack();
        for (Card c : cards) {
            ret.addCard(c);
        }
        return ret;
    }

    public static List<Pack> createPacks(List<Card> cards) {
        List<Pack> ret = new ArrayList<>();
        List<Card> commons = new ArrayList<>(), rares = new ArrayList<>();
        int count;
        for (Card c : cards) {
            if (c.getRarity() == Card.RARITY.RARE) {
                rares.add(c.copy());
            } else {
                switch (c.getRarity()) {
                    case SUPER_COMMON:
                        count = 6;
                        break;
                    case COMMON:
                        count = 3;
                        break;
                    case PROVINCE:
                        count = 1;
                        break;
                    default:
                        count = 0;
                        break;
                }
                for (int i = 0; i < count; i++) {
                    commons.add(c.copy());
                }
            }
        }
        Pack nextPack;
        while (rares.size() >= 4 && commons.size() >= 12) {
            nextPack = new Pack();
            for (int i = 0; i < 12; i++) {
                nextPack.addCard(commons.remove(MathUtils.random(commons.size() - 1)));
            }
            for (int j = 0; j < 4; j++) {
                nextPack.addCard(rares.remove(MathUtils.random(rares.size() - 1)));
            }
            ret.add(nextPack);
        }
        return ret;
    }
}
