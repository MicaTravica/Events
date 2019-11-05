package com.app.events.dto;

import java.util.Set;

import com.app.events.model.Hall;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HallDTO {

	private Long id;
	private String name;
	private PlaceDTO place;
	private Set<SectorDTO> sectors;

	  public HallDTO(Hall hall) {
	        this.id = hall.getId();
	        this.name = hall.getName();
	        
	        
	    }

}


