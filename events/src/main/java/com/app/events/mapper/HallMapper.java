package com.app.events.mapper;

import java.util.HashSet;
import java.util.stream.Collectors;

import com.app.events.dto.HallDTO;
import com.app.events.model.Hall;
import com.app.events.model.Place;

public class HallMapper {

    public static HallDTO toDTO(Hall hall) {    
        return new HallDTO(hall.getId(), hall.getName(),
            PlaceMapper.toDTO(hall.getPlace()),
            hall.getSectors().stream()
                .map(sector-> SectorMapper.toDTO(sector))
                .collect(Collectors.toSet())
        );
    }
    
    public static Hall toHall(HallDTO hallDTO) {
        return new Hall(hallDTO.getId(),
                        hallDTO.getName(), 
                        new Place(hallDTO.getPlace().getId()),
                        new HashSet<>()
					);
    }

}
