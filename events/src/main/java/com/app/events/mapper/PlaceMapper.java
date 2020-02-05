package com.app.events.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.dto.PlaceDTO;
import com.app.events.model.Hall;
import com.app.events.model.Place;
public class PlaceMapper {

    public static PlaceDTO toDTO(Place place) {
        return new PlaceDTO(place.getId(),
        		place.getName(),
        		place.getAddress(),
        		place.getLatitude(),
        		place.getLongitude()        		);
    }
    
    public static Place toPlace(PlaceDTO placeDTO) {
        return new Place(placeDTO.getId(),
                placeDTO.getName(),
                placeDTO.getAddress(),
                placeDTO.getLatitude(),
                placeDTO.getLongitude(),
                placeDTO.getHalls().stream().map(HallMapper::toHall).collect(Collectors.toSet())
            );    
    }

    public static PlaceDTO toDTOWithHalls(Place place) {
		  return new PlaceDTO(place.getId(),
	        		place.getName(),
	        		place.getAddress(),
	        		place.getLatitude(),
	        		place.getLongitude(),
	        		place.getHalls().stream().map(HallMapper::toDTO).collect(Collectors.toSet())
	        		);
	}


}
