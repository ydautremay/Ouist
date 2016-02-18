package org.ydautremay.ouist.domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.ydautremay.ouist.domain.model.Deal;
import org.ydautremay.ouist.domain.model.Trick;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.game.Score;
import org.ydautremay.ouist.domain.model.player.Player;
import org.ydautremay.ouist.domain.model.game.Contract;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;
import org.ydautremay.ouist.domain.policies.ScorePolicy;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class DefaultScoreService implements ScoreService {

    @Inject
    private ScorePolicy scorePolicy;

    public void computeScores(Round round) {
        Deal deal = round.getDeal();
        Map<Player, Integer> nbTricksByPlayer = new HashMap<>();
        for (Trick trick:deal.getPlayedTricks()) {
            Player p = trick.getLeader();
            if(nbTricksByPlayer.get(p) == null){
                nbTricksByPlayer.put(p, 0);
            }
            int nb = nbTricksByPlayer.get(p);
            nbTricksByPlayer.put(p, nb + 1);
        }
        for (Contract contract:round.getContracts()) {
            PlayerNickName p = contract.getContractId().getPlayer();
            Score score = new Score(p);
            int scoreValue;
            if(nbTricksByPlayer.get(p) == contract.getNbTricks()){
                scoreValue = scorePolicy.scoreForNotLoosing();
                scoreValue += scorePolicy.bonusPerTrick() * contract.getNbTricks();
            }else{
                scoreValue = - (scorePolicy.minusPerTrick() * (Math.abs(nbTricksByPlayer.get(p) - contract
                        .getNbTricks())));
            }
            score.setValue(scoreValue);
            round.getScores().add(score);
        }
    }
}
