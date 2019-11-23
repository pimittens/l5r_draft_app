package com.mygdx.l5rdraft.drafters;

import com.mygdx.l5rdraft.packs.Pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Draft {

    private List<Drafter> drafters;
    private List<Pack> packs;
    private List<Queue<Pack>> playerQueues;

    public Draft(List<Pack> packs, List<Drafter> drafters) {
        this.packs = packs;
        this.drafters = drafters;
        playerQueues = new ArrayList<>();
        for (Drafter d : drafters) {
            playerQueues.add(new LinkedList<>());
        }
    }

    private void getMorePacks() {
        
    }

    /**
     * pushes a pack to the next drafter
     *
     * @param pack the pack
     * @param drafterName the drafter who just picked from the pack
     */
    public void pushPack(Pack pack, String drafterName) {
        for (int i = 0; i < drafters.size(); i++) {
            if (drafters.get(i).getName().equals(drafterName)) {
                int nextPos = i + 1;
                if (nextPos >= playerQueues.size()) {
                    nextPos = 0;
                }
                playerQueues.get(nextPos).add(pack);
                return;
            }
        }
        // note: if the drafter's name cannot be found the pack will be lost
    }

    /**
     * returns the next available pack for the specified drafter or null if there are no packs available
     *
     * @param drafterName the name of the drafter
     * @return the next pack or null if none are available
     */
    public Pack getNextPack(String drafterName) {
        for (int i = 0; i < drafters.size(); i++) {
            if (drafters.get(i).getName().equals(drafterName)) {
                return playerQueues.get(i).poll();
            }
        }
        return null;
    }

    // todo: check if all the packs are empty to move on to the next pack
}
