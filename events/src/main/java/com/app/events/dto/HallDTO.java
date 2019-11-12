package com.app.events.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.Hall;
import com.app.events.model.Sector;

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

}


