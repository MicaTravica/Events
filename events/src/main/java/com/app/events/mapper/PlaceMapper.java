package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.PlaceDTO;
import com.app.events.model.Place;
public class PlaceMapper {

    public static PlaceDTO toDTO(Place place) {
        return new PlaceDTO(place.getId(), place.getName(), place.getAddress(), place.getLatitude(), place.getLongitude());
    }
    
    public static Place toPlace(PlaceDTO placeDTO) {
        return new Place(placeDTO.getId(),
                placeDTO.getName(),
                placeDTO.getAddress(),
                placeDTO.getLatitude(),
                placeDTO.getLongitude(),
                new HashSet<>(),
                new HashSet<>()
            );    
    }

}
