package org.ydautremay.rest;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.seedstack.business.assembler.modelmapper.ModelMapperAssembler;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yves on 28/04/2016.
 */
public class GameModelMapperAssembler extends ModelMapperAssembler<Game, GameRepresentation> {
    @Override
    protected void configureAssembly(ModelMapper modelMapper) {
        modelMapper.addConverter(new ChairConverter());
        modelMapper.addMappings(new PropertyMap<Game, GameRepresentation>() {
            @Override
            protected void configure() {
                map(source.getChairs()).setPlayers(null);
            }
        });
    }

    @Override
    protected void configureMerge(ModelMapper modelMapper) {

    }

    private static class ChairConverter extends AbstractConverter<Chair, String>{

        @Override
        protected String convert(Chair source) {
            return source.getPlayer().getNickname();
        }
    }
}
