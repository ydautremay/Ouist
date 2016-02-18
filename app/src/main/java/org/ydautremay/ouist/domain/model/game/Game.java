package org.ydautremay.ouist.domain.model.game;

import static org.ydautremay.ouist.domain.model.game.state.GameAction.CHANGE_PLAYERS;
import static org.ydautremay.ouist.domain.model.game.state.GameAction.NEW_CONTRACT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.identity.UUIDHandler;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.game.exceptions.PlayerNotInGameException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Game extends BaseAggregateRoot<UUID> {

    @Id
    @Identity(handler = UUIDHandler.class)
    private UUID gameId;

    @OneToMany(mappedBy = "roundId.gameId", cascade= CascadeType.ALL)
    @OrderColumn(name = "roundNb")
    private List<Round> rounds;

    private GameState gameState;

    @ElementCollection
    @OrderColumn(name="index")
    private List<Chair> chairs;

    private DeckSize size = DeckSize.NORMAL;

    private int maxTricks;

    private int currentTrickAmount;

    private boolean ascending;

    private Date date;

    Game(){
        this.chairs = new ArrayList<>();
        this.rounds = new ArrayList<Round>();
        gameState = GameState.NEW;
    }

    Game(Date date) {
        this();
        this.date = date;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Chair> getChairs() {
        return Collections.unmodifiableList(chairs);
    }

    public void addPlayer(PlayerNickName player) throws GameActionException {
        CHANGE_PLAYERS.checkActionState(this);
        if(chairs.stream().noneMatch(c -> c.getPlayer().equals(player))){
            Chair chair = new Chair(player);
            chairs.add(chair);
        }
    }

    public void removePlayer(PlayerNickName nick) throws GameActionException {
        CHANGE_PLAYERS.checkActionState(this);
        chairs.removeIf(chair -> chair.getPlayer().equals(nick));
    }

    public UUID getGameId() {
        return gameId;
    }

    @Override
    public UUID getEntityId() {
        return gameId;
    }

    public void startGame() throws GameStateChangeException {
        gameState = gameState.ready(this);
        newRound();
        maxTricks = size.getSize()/chairs.size();
        currentTrickAmount = maxTricks;
        ascending = false;
    }

    private Round newRound(){
        Round round = new Round(gameId, rounds.size());
        rounds.add(round);
        return round;
    }

    public void nextContract(int nbTricks) throws GameActionException,
            PlayerNotInGameException {
        NEW_CONTRACT.checkActionState(this);
        Round round = rounds.get(rounds.size() - 1);
        int nbContracts = round.getContracts().size();
        if(nbContracts == chairs.size()){
            throw new GameActionException("Every player already made a contract");
        }
        if(nbContracts == chairs.size() - 1) {
            //Sum all contracts to verify it is different from number of tricks
            int existingTricks = round.getContracts().stream().collect(Collectors.summingInt
                    (Contract::getNbTricks));
            if (existingTricks + nbTricks == currentTrickAmount) {
                throw new GameActionException("Last player cannot bet " + nbTricks);
            }
        }
        PlayerNickName player = chairs.get(nbContracts + 1).getPlayer();
        ContractId contractId = new ContractId(round.getRoundId(), player);
        Contract contract = new Contract(contractId, nbTricks);
        round.getContracts().add(contract);
    }



    public GameState getGameState() {
        return gameState;
    }
}
