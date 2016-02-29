package org.ydautremay.ouist.domain.model.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 25/01/2016.
 */
public class GameRepositoryTest extends AbstractSeedIT {

    @Inject
    private GameFactory gameFactory;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Inject
    private EntityManager entityManager;

    @Test
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public void testRepository(){
        Game game = gameFactory.createGame();
        gameRepository.persist(game);
        assertThat(game.getGameId()).isNotNull();
    }

    @Test
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public void testChainList() throws Exception {
        Game game = gameFactory.createGame();
        game.setScoreSheetId(UUID.randomUUID());

        PlayerNickName player1 = new PlayerNickName("nick1");
        PlayerNickName player2 = new PlayerNickName("nick2");
        PlayerNickName player3 = new PlayerNickName("nick3");

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        gameRepository.persist(game);

        game.startGame();
        game.nextContract(2);
        game.nextContract(3);
        game.nextContract(1);

        game.nextTrick(player2);
        game.nextTrick(player2);
        game.nextTrick(player2);
        game.nextTrick(player2);
        game.nextTrick(player2);
        game.nextTrick(player2);
        game.nextTrick(player2);

        game.nextContract(5);
        game.nextContract(4);
        game.nextContract(6);

        game.nextTrick(player1);
        game.nextTrick(player2);
        game.nextTrick(player3);

        gameRepository.save(game);

        Game game2 = gameRepository.load(game.getEntityId());
        assertThat(game2.getScoreSheetId()).isNotNull();
        List<Chair> chairs = game2.getChairs();
        Assertions.assertThat(chairs.get(0).getPlayer().getNickname()).isEqualTo("nick1");
        Assertions.assertThat(chairs.get(1).getPlayer().getNickname()).isEqualTo("nick2");
        Assertions.assertThat(chairs.get(2).getPlayer().getNickname()).isEqualTo("nick3");
    }
}
