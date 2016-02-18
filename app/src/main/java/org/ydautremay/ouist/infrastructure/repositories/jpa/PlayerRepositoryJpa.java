package org.ydautremay.ouist.infrastructure.repositories.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.ydautremay.ouist.domain.model.player.Player;
import org.ydautremay.ouist.domain.model.player.PlayerRepository;

import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 25/01/2016.
 */
public class PlayerRepositoryJpa extends BaseJpaRepository<Player, PlayerNickName> implements PlayerRepository {
}
