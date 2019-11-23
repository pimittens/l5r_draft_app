package com.mygdx.l5rdraft.drafters;

import com.mygdx.l5rdraft.packs.Pool;

public class Drafter {

    private Pool pool;
    private String name;

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
}
