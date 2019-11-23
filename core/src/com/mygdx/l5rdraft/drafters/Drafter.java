package com.mygdx.l5rdraft.drafters;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.l5rdraft.packs.Card;
import com.mygdx.l5rdraft.packs.Pack;
import com.mygdx.l5rdraft.packs.Pool;

public class Drafter {

    private Pool pool;
    private String name;
    private Pack checkedOutPack;

    private boolean isAi;

    public Drafter(String name) {
        pool = new Pool();
        this.name = name;
        isAi = name.contains("_ai");
    }

    public boolean isAi() {
        return isAi;
    }

    public Pool getPool() {
        return pool;
    }

    public String getName() {
        return name;
    }

    public void checkOutPack(Pack p) {
        checkedOutPack = p;
    }

    public void checkInPack() {
        checkedOutPack = null;
    }

    public boolean hasCheckedOutPack() {
        return checkedOutPack != null;
    }

    /**
     * called on ai drafters
     * currently just picks at random
     * todo: more advanced picking
     *
     * @param draft the draft
     */
    public void act(Draft draft) {
        // if no pack is checked out try to get a new pack
        if (!hasCheckedOutPack()) {
            draft.getNextPack(name);
        }
        // if a pack is checked out pick a card
        if (hasCheckedOutPack()) {
            Card pick = checkedOutPack.popCard(MathUtils.random(checkedOutPack.size() - 1));
            draft.pushPack(checkedOutPack, pick, name);
        }
    }
}
