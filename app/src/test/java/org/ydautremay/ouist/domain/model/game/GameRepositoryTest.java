package org.ydautremay.ouist.domain.model.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
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
    public void testChaiList() throws GameActionException {
        Game game = gameFactory.createGame();
        game.addPlayer(new PlayerNickName("nick1"));
        game.addPlayer(new PlayerNickName("nick2"));
        game.addPlayer(new PlayerNickName("nick3"));
        gameRepository.persist(game);

        Game game2 = gameRepository.load(game.getEntityId());
        List<Chair> chairs = game2.getChairs();
        Assertions.assertThat(chairs.get(0).getPlayer().getNickname()).isEqualTo("nick1");
        Assertions.assertThat(chairs.get(1).getPlayer().getNickname()).isEqualTo("nick2");
        Assertions.assertThat(chairs.get(2).getPlayer().getNickname()).isEqualTo("nick3");
    }
}
