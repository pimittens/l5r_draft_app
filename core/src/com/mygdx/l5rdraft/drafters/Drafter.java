package com.mygdx.l5rdraft.drafters;

import com.mygdx.l5rdraft.packs.Pack;
import com.mygdx.l5rdraft.packs.Pool;

public class Drafter {

    private Pool pool;
    private String name;
    private Pack checkedOutPack;

    public Drafter(String name) {
        pool = new Pool();
        this.name = name;
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
}
