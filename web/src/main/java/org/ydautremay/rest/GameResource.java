package org.ydautremay.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.business.assembler.modelmapper.ModelMapperAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.finder.Result;
import org.seedstack.business.view.PaginatedView;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameFactory;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

@Path("/games")
public class GameResource {

    @Inject
    private GameFactory gameFactory;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private GameModelMapperAssembler gameAssembler;

    @Context
    UriInfo ui;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public Response list(@QueryParam("pageIndex") Long pageIndex,
                         @QueryParam("pageSize") Integer pageSize,
                         @QueryParam("chunkOffset") Long chunkOffset,
                         @QueryParam("chunkSize") Integer chunkSize){
        List<Game> games = entityManager.createQuery("select g from Game g order by g.date desc", Game.class).getResultList();
        List<GameRepresentation> reps = new ArrayList<>();
        for (Game game : games) {
            GameRepresentation rep = gameAssembler.assembleDtoFromAggregate(game);
            reps.add(rep);
        }
        if(pageIndex != null) {
            Result<GameRepresentation> result = new Result<>(reps, 0, reps.size());
            PaginatedView<GameRepresentation> paginatedView = new PaginatedView<>(
                    result, pageSize, pageIndex);
            return Response.ok(paginatedView).build();
        }else{
            return Response.ok(reps).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public Response createGame(GameRepresentation rep) throws GameActionException{
        Game game = gameFactory.createGame();
        for(String player : rep.getPlayers()) {
            game.addPlayer(new PlayerNickName(player));
        }
        gameRepository.persist(game);
        return Response.created(ui.getRequestUriBuilder().path(game.getEntityId().toString()).build()).build();
    }

    @DELETE
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    @Path("{id}")
    public void deleteGame(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        gameRepository.delete(uuid);
    }
}
