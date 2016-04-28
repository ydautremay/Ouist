package org.ydautremay.rest;

import org.seedstack.business.assembler.DtoOf;
import org.ydautremay.ouist.domain.model.game.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yves on 28/04/2016.
 */
public class GameRepresentation {

    private String gameId;

    private Date date;

    private List<String> chairsPlayer;

    public GameRepresentation(){}

    public GameRepresentation(String id, Date date){
        this.gameId = id;
        this.date = date;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String id) {
        this.gameId = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getPlayers() {
        return chairsPlayer;
    }

    public void setPlayers(List<String> players) {
        this.chairsPlayer = players;
    }

}
