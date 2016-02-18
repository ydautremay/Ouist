package org.ydautremay.ouist.domain.model;

import java.util.ArrayList;
import java.util.Collection;

import org.ydautremay.ouist.domain.model.player.Player;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class Hand {

    private Player player;

    private Collection<Card> cards;

    Hand() {
    }

    public Hand(Player player) {
        this.cards = new ArrayList<Card>();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Collection<Card> getCards() {
        return cards;
    }
}
