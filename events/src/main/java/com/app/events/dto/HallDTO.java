package com.app.events.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.model.Hall;
import com.app.events.model.Sector;

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
		hall.getSectors().forEach(sector->{
			Sector mappedSector = new Sector(
				sector.getId(),
				sector.getName(),
				sector.getSectorColumns(),
				sector.getSectorRows()
			);
			this.sectors.add(new SectorDTO(mappedSector));
		});   
	}

	public Hall toSimpleHall() {
		return new Hall(this.getId(),
						this.getName(), 
						this.getPlace().toSimplePlace(),
						this.getSectors()
							.stream()
							.map(sectorDTO->{
								return sectorDTO.toSimpleSector();
							})
							.collect(Collectors.toSet())
					);
	}
}


