package org.ydautremay.ouist.domain.model;

import org.ydautremay.ouist.domain.model.player.Player;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class Play {

    private Player player;

    private Card card;

    public Play(Player seat, Card card) {
        this.player = seat;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
