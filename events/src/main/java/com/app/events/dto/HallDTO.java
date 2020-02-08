package com.app.events.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {

	private Long id;
	private String name;
	private PlaceDTO place;
	private Set<SectorDTO> sectors;
	
	public HallDTO(Long id) {
		super();
		this.id = id;
	}


}


