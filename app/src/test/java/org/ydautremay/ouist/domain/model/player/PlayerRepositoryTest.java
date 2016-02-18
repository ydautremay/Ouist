package org.ydautremay.ouist.domain.model.player;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.transaction.Transactional;

/**
 * Created by dautremayy on 25/01/2016.
 */
public class PlayerRepositoryTest extends AbstractSeedIT{

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerFactory playerFactory;

    @Test
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public void testRepository(){
        final String nickname = "nickname";
        Player player = playerFactory.createPlayer(nickname);
        playerRepository.persist(player);

        Player player2 = playerRepository.load(new PlayerNickName(nickname));
        assertThat(player2.getNickname().getNickname()).isEqualTo(nickname);
    }
}
