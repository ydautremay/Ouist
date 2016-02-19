package org.ydautremay.ouist.domain.model.game;

import static org.ydautremay.ouist.domain.model.game.state.GameAction.CHANGE_PLAYERS;
import static org.ydautremay.ouist.domain.model.game.state.GameAction.NEW_CONTRACT;
import static org.ydautremay.ouist.domain.model.game.state.GameAction.NEW_TRICK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.identity.UUIDHandler;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Game extends BaseAggregateRoot<UUID> {

    @Id
    @Identity(handler = UUIDHandler.class)
    private UUID gameId;

    @OneToMany(mappedBy = "roundId.gameId", cascade = CascadeType.ALL)
    @OrderBy("roundNb")
    private List<Round> rounds;

    private GameState gameState;

    @ElementCollection
    @OrderColumn(name = "chairIndex")
    private List<Chair> chairs;

    private DeckSize size = DeckSize.NORMAL;

    private int maxTricks;

    private int currentTrickAmount;

    private boolean ascending;

    Game() {
        this.chairs = new ArrayList<>();
        this.rounds = new ArrayList<Round>();
        gameState = GameState.NEW;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Chair> getChairs() {
        return Collections.unmodifiableList(chairs);
    }

    public void addPlayer(PlayerNickName player) throws GameActionException {
        CHANGE_PLAYERS.checkActionState(this);
        if (chairs.stream().noneMatch(c -> c.getPlayer().equals(player))) {
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

    public GameState startGame() throws GameStateChangeException {
        gameState = gameState.ready(this);
        newRound();
        maxTricks = size.getSize() / chairs.size();
        currentTrickAmount = maxTricks;
        ascending = false;
        return gameState;
    }

    private Round newRound() {
        Round round = new Round(gameId, rounds.size());
        rounds.add(round);
        return round;
    }

    public GameState nextContract(int nbTricks) throws GameActionException,
            GameStateChangeException {
        NEW_CONTRACT.checkActionState(this);
        Round round = getCurrentRound();
        int nbContracts = round.getContracts().size();
        if (nbContracts == chairs.size()) {
            throw new GameActionException("Every player already made a contract");
        }
        if (nbContracts == chairs.size() - 1) {
            //Sum all contracts to verify it is different from number of tricks
            int existingTricks = round.getContracts().stream().collect(Collectors.summingInt
                    (Contract::getNbTricks));
            if (existingTricks + nbTricks == currentTrickAmount) {
                throw new GameActionException("Last player cannot bet " + nbTricks);
            }
            //Change state
            this.gameState = gameState.betsDone(this);
            newRound();
        }
        PlayerNickName player = chairs.get(nbContracts).getPlayer();
        ContractId contractId = new ContractId(round.getRoundId(), player);
        Contract contract = new Contract(contractId, nbTricks);
        round.getContracts().add(contract);
        return gameState;
    }

    public GameState nextTrick(PlayerNickName leader) throws GameActionException, GameStateChangeException {
        NEW_TRICK.checkActionState(this);
        Round round = getCurrentRound();
        int nbPlayedTricks = round.getPlayedTricks().size();
        if (nbPlayedTricks == currentTrickAmount) {
            throw new GameActionException("All tricks already played");
        }
        if (nbPlayedTricks == currentTrickAmount - 1) {
            //Change state
            gameState = gameState.dealPlayed(this);
            //change amount of tricks for next deal
            updateTrickAmount();
        }
        SimpleTrick newTrick = new SimpleTrick(round.getRoundId(), leader);
        round.getPlayedTricks().add(newTrick);
        return gameState;
    }

    private void updateTrickAmount() {
        if(currentTrickAmount == maxTricks){
            ascending = false;
        }
        if(currentTrickAmount == 1){
            ascending = true;
        }
        if(ascending){
            currentTrickAmount++;
        }else{
            currentTrickAmount--;
        }
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }


    public GameState getGameState() {
        return gameState;
    }
}
