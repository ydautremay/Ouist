package org.ydautremay.ouist.application;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.ydautremay.ouist.domain.model.game.Game;

/**
 * Created by dautremayy on 06/02/2016.
 */
public class SessionSecurityImpl implements Session {

    public static final String GAME_ID = "game_id";

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;


    public UUID getCurrentGameId() {
        org.apache.shiro.session.Session session = SecurityUtils.getSubject().getSession();
        return (UUID) session.getAttribute(GAME_ID);
    }

    public void setCurrentGameId(UUID gameId) {
        SecurityUtils.getSubject().getSession().setAttribute(GAME_ID, gameId);
    }
}
