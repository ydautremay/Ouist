package org.ydautremay.ouist.domain.model.game;

import static org.ydautremay.ouist.domain.model.game.state.GameAction.CHANGE_PLAYERS;
import static org.ydautremay.ouist.domain.model.game.state.GameAction.NEW_CONTRACT;
import static org.ydautremay.ouist.domain.model.game.state.GameAction.NEW_TRICK;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.ydautremay.ouist.domain.model.game.exceptions.CannotBetException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
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

    private UUID scoreSheetId;

    private boolean jump;

    Game() {
        this.chairs = new ArrayList<>();
        this.rounds = new ArrayList<>();
        gameState = GameState.NEW;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Chair> getChairs() {
        return chairs;
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
        jump = (maxTricks - 1) % chairs.size() == 0;
        return gameState;
    }

    public GameState finish() throws GameStateChangeException{
        return gameState.finish(this);
    }

    private Round newRound() {
        Round round = new Round(gameId, rounds.size());
        if(rounds.isEmpty()){
            round.setDealer(chairs.get(0).getPlayer());
        }else{
            PlayerNickName lastDealer = getCurrentRound().getDealer();
            round.setDealer(getNextPlayer(lastDealer));
            if(jump){
                round.setDealer(getNextPlayer(lastDealer));
            }
        }
        rounds.add(round);
        return round;
    }

    public GameState nextContract(int nbTricks) throws GameActionException,
            GameStateChangeException {
        if(nbTricks < 0){
            throw new GameActionException("Cannot bet a negative");
        }
        NEW_CONTRACT.checkActionState(this);
        Round round = getCurrentRound();
        int nbContracts = round.getContracts().size();
        if (nbContracts == chairs.size() - 1) {
            //Sum all contracts to verify it is different from number of tricks
            int existingTricks = round.getContracts().stream().collect(Collectors.summingInt
                    (Contract::getNbTricks));
            if (existingTricks + nbTricks == currentTrickAmount) {
                throw new CannotBetException("Last player cannot bet " + nbTricks);
            }
            //Change state
            this.gameState = gameState.betsDone(this);
        }else if(nbContracts == chairs.size() - 2){
            //Change state
            this.gameState = gameState.lastBet(this);
        }
        PlayerNickName player = getPlayerToBet(round);
        ContractId contractId = new ContractId(round.getRoundId(), player);
        Contract contract = new Contract(contractId, nbTricks);
        round.getContracts().add(contract);
        return gameState;
    }

    public PlayerNickName getPlayerToBet(Round round) {
        int nbContracts = round.getContracts().size();
        int dealerIndex = IntStream.range(0, chairs.size()).filter(i -> chairs.get(i).getPlayer().equals
                (round.getDealer())).findFirst().getAsInt();
        int nextContractIndex = (dealerIndex + nbContracts + 1)%chairs.size();
        return chairs.get(nextContractIndex).getPlayer();
    }

    public GameState nextTrick(PlayerNickName leader) throws GameActionException, GameStateChangeException {
        if(chairs.stream().noneMatch(c -> c.getPlayer().equals(leader))){
            throw new PlayerNotInGameException();
        }
        NEW_TRICK.checkActionState(this);
        Round round = getCurrentRound();
        int nbPlayedTricks = round.getPlayedTricks().size();
        if (nbPlayedTricks == currentTrickAmount - 1) {
            //Change state
            gameState = gameState.dealPlayed(this);
            //change amount of tricks for next deal
            updateTrickAmount();
            newRound();
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

    public Round getLastRound() {
        return rounds.get(rounds.size() - 2);
    }

    public int getNextForbiddenBet(){
        if(gameState != GameState.LAST_BET){
            return -1;
        }else{
            Round round = getCurrentRound();
            int existingTricks = round.getContracts().stream().collect(Collectors.summingInt
                    (Contract::getNbTricks));
            return currentTrickAmount - existingTricks;
        }
    }

    public PlayerNickName getNextPlayer(PlayerNickName p){
        int chairIndex = IntStream.range(0, chairs.size()).filter(i -> chairs.get(i).getPlayer().equals
                (p)).findFirst().getAsInt();
        if(chairIndex == chairs.size() - 1){
            chairIndex = 0;
        }else{
            chairIndex ++;
        }
        return chairs.get(chairIndex).getPlayer();
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getCurrentTrickAmount() {
        return currentTrickAmount;
    }

    public UUID getScoreSheetId() {
        return scoreSheetId;
    }

    public void setScoreSheetId(UUID scoreSheetId) {
        this.scoreSheetId = scoreSheetId;
    }
}
