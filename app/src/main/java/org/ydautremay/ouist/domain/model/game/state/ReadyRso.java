package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class ReadyRso implements GameStateOperations {
    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState betsDone(Game game) throws GameStateChangeException {
//        List<Chair> chairs = game.getChairs();
//        List<Contract> contracts = game.getCurrentRound().getContracts();
//        if(chairs.size() != contracts.size()){
//            throw new GameStateChangeException("All players must bet");
//        }
//        List<PlayerNickName> playersOnChairs = chairs.stream().map(Chair::getPlayer).collect(Collectors.toList());
//        List<PlayerNickName> contractPlayers = contracts.stream().map(c -> c.getContractId().getPlayer()).collect(Collectors.toList());
//        for(int i = 0; i < playersOnChairs.size(); i++){
//            if(!playersOnChairs.get(i).equals(contractPlayers.get(i))){
//                throw new GameStateChangeException("Not the same players on the chairs have bet !!");
//            }
//        }
        return GameState.DEALT;
    }

    @Override
    public GameState dealPlayed(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }
}
