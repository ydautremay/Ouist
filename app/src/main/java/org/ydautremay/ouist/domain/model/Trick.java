package org.ydautremay.ouist.domain.model;

import java.util.ArrayList;
import java.util.Collection;

import org.ydautremay.ouist.domain.model.player.Player;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class Trick {

    private Player leader;

    private Collection<Play> plays;

    public Trick(Player leader) {
        this.plays = new ArrayList<>();
        this.leader = leader;
    }

    public Collection<Play> getPlays() {
        return plays;
    }

    public Player getLeader() {
        return leader;
    }
}
