package org.ydautremay.ouist.domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.ydautremay.ouist.domain.model.game.Contract;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.game.SimpleTrick;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;
import org.ydautremay.ouist.domain.model.scoresheet.Score;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreLine;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreSheet;
import org.ydautremay.ouist.domain.policies.ScorePolicy;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class DefaultScoreService implements ScoreService {

    @Inject
    private ScorePolicy scorePolicy;

    public void computeScores(ScoreSheet scoreSheet, Round round) {
        ScoreLine scoreLine = scoreSheet.newLine(round.getRoundId());
        Map<PlayerNickName, Integer> nbTricksByPlayer = new HashMap<>();
        for (SimpleTrick trick:round.getPlayedTricks()) {
            PlayerNickName p = trick.getLeader();
            if(nbTricksByPlayer.get(p) == null){
                nbTricksByPlayer.put(p, 0);
            }
            int nb = nbTricksByPlayer.get(p);
            nbTricksByPlayer.put(p, nb + 1);
        }
        for (Contract contract:round.getContracts()) {
            PlayerNickName p = contract.getContractId().getPlayer();
            if(nbTricksByPlayer.get(p) == null){
                nbTricksByPlayer.put(p, 0);
            }
            int scoreValue;
            if(nbTricksByPlayer.get(p) == contract.getNbTricks()){
                scoreValue = scorePolicy.scoreForNotLoosing();
                scoreValue += scorePolicy.bonusPerTrick() * contract.getNbTricks();
            }else{
                scoreValue = - (scorePolicy.minusPerTrick() * (Math.abs(nbTricksByPlayer.get(p) - contract
                        .getNbTricks())));
            }
            Score score = new Score(p, scoreValue);
            scoreLine.getRoundScores().add(score);
        }
    }
}
