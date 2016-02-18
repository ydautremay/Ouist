package org.ydautremay.ouist.domain.model.game;

/**
 * Created by dautremayy on 17/02/2016.
 */
public enum DeckSize {

    NORMAL(22),
    BIG(44);

    private int size;

    DeckSize(int i) {
        this.size = i;
    }

    public int getSize() {
        return size;
    }
}
