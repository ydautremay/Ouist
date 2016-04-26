package org.ydautremay.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.business.finder.Result;
import org.seedstack.business.view.PaginatedView;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameFactory;

@Path("/games")
public class GameResource {

    @Inject
    private GameFactory gameFactory;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("pageIndex") Long pageIndex,
                         @QueryParam("pageSize") Integer pageSize,
                         @QueryParam("chunkOffset") Long chunkOffset,
                         @QueryParam("chunkSize") Integer chunkSize){
        List<Game> games = new ArrayList<>();
        Game game = gameFactory.createGame();
        Game game2 = gameFactory.createGame();
        games.add(game);
        games.add(game2);
        Result<Game> result = new Result<>(games, 0, 2);
        PaginatedView<Game> paginatedView = new PaginatedView<>(
                result, pageSize, pageIndex);
        return Response.ok(paginatedView).build();
    }
}
