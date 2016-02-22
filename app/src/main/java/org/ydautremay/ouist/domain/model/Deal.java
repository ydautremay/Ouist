package org.ydautremay.ouist.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class Deal {

    private List<Trick> playedTricks;

    private Collection<Hand> hands;

    public Deal() {
        this.playedTricks = new ArrayList<>();
        this.hands = new ArrayList<>();
    }

    public List<Trick> getPlayedTricks() {
        return playedTricks;
    }

    public Collection<Hand> getHands() {
        return hands;
    }
}
