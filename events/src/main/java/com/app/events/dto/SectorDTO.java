package com.app.events.dto;

import com.app.events.model.Hall;
import com.app.events.model.Sector;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorDTO {

	private Long id;
	private String name;

	private int sectorRows;
	private int sectorColumns;
	private HallDTO hall;

	public SectorDTO(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.sectorRows = sector.getSectorRows();
		this.sectorColumns = sector.getSectorColumns(); 
		this.hall = this.getHallInfo(sector.getHall());
	}

	public HallDTO getHallInfo(Hall hall)
	{
		return hall != null ? new HallDTO(hall.getId(),hall.getName()): null;
	}

}
