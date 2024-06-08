package com.adam.dark.util;

import com.adam.dark.dto.ActorDTO;
import com.adam.dark.entity.ActorEntity;
import com.adam.dark.vo.ActorVO;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author adamboov
 */
@Mapper
public interface MapstructUtil {

    MapstructUtil INSTANCE = Mappers.getMapper(MapstructUtil.class);

    @InheritInverseConfiguration
    ActorVO actorEntityToVO(ActorEntity actorEntity,
                            @Context CycleAvoidingMappingContext context);

    ActorEntity actorVOToEntity(ActorVO actorVO
            , @Context CycleAvoidingMappingContext context);


    @InheritInverseConfiguration
    ActorDTO actorEntityToDTO(ActorEntity actorEntity,
                              @Context CycleAvoidingMappingContext context);

    ActorEntity actorDTOToEntity(ActorDTO actorDTO
            , @Context CycleAvoidingMappingContext context);


    @InheritInverseConfiguration
    ActorDTO actorVOToDTO(ActorVO actorVO,
                          @Context CycleAvoidingMappingContext context);

    ActorVO actorDTOToVO(ActorDTO actorDTO
            , @Context CycleAvoidingMappingContext context);
}
