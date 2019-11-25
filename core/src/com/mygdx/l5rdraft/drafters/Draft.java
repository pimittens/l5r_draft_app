package com.mygdx.l5rdraft.drafters;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.l5rdraft.packs.Card;
import com.mygdx.l5rdraft.packs.Pack;
import com.mygdx.l5rdraft.packs.Pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Draft {

    private List<Drafter> drafters;
    private List<Pack> packs;
    private List<Queue<Pack>> playerQueues;

    private boolean draftIsOver;
    private int roundNumber;

    public Draft(List<Pack> packs, List<Drafter> drafters) {
        draftIsOver = false;
        roundNumber = 1;
        this.packs = packs;
        this.drafters = drafters;
        playerQueues = new ArrayList<>();
        for (int i = 0; i < drafters.size(); i++) {
            playerQueues.add(new LinkedList<>());
        }
    }

    public void startDraft() {
        getMorePacks();
    }

    public boolean draftIsOver() {
        return draftIsOver;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Pool getPool(String drafterName) {
        for (Drafter d : drafters) {
            if (d.getName().equals(drafterName)) {
                return d.getPool();
            }
        }
        return null;
    }

    /**
     * updates the state of the draft
     * should be called each time before a pack is pushed or pulled
     */
    public void update() {
        // ai actions
        for (Drafter d : drafters) {
            if (d.isAi()) {
                d.act(this);
            }
        }
        // check if the round is over
        if (roundIsOver()) {
            roundNumber += 1;
            // try to get more packs. if none are left the draft is over
            if (!getMorePacks()) {
                draftIsOver = true;
            }
        }
    }

    /**
     * checks if the round is over
     * the round is over when no player has a checked out pack and all player queues are empty
     *
     * @return whether the round is over
     */
    public boolean roundIsOver() {
        for (Queue<Pack> q : playerQueues) {
            if (!q.isEmpty()) {
                return false;
            }
        }
        for (Drafter d : drafters) {
            if (d.hasCheckedOutPack()) {
                return false;
            }
        }
        return true;
    }

    /**
     * adds more packs to the player queues. returns whether there were enough packs left
     * if there aren't enough packs left for another round, the draft should end
     *
     * @return whether there were enough packs left for another round
     */
    private boolean getMorePacks() {
        if (packs.size() < drafters.size()) {
            return false;
        }
        for (Queue<Pack> q : playerQueues) {
            q.add(packs.remove(MathUtils.random(packs.size() - 1)));
        }
        return true;
    }

    /**
     * pushes a pack to the next drafter
     *
     * @param pack        the pack after a card was picked
     * @param card        the card that was picked
     * @param drafterName the drafter who just picked from the pack
     */
    public void pushPack(Pack pack, Card card, String drafterName) {
        for (int i = 0; i < drafters.size(); i++) {
            if (drafters.get(i).getName().equals(drafterName)) {
                // todo: validate the pack maybe
                drafters.get(i).getPool().addCard(card);
                drafters.get(i).checkInPack();
                if (pack.size() < 1) {
                    // pack has no cards left
                    return;
                }
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
                Pack p = playerQueues.get(i).poll();
                drafters.get(i).checkOutPack(p);
                return p;
            }
        }
        return null;
    }
}
