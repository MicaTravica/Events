package com.app.events.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.Hall;
import com.app.events.model.Sector;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HallDTO {

	@Autowired
	private SectorMapper sectorMapper;

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
	
	public HallDTO(Long id){
		this.id = id;
	}
	
	public HallDTO(Long id2, String name2) {
		this.id = id2;
		this.name = name2;
	}

	public Hall toSimpleHall() {
		return new Hall(this.getId(),
						this.getName(), 
						this.getPlace().toSimplePlace(),
						this.getSectors()
							.stream()
							.map(sectorDTO->{
								return sectorMapper.toSector(sectorDTO);
							})
							.collect(Collectors.toSet())
					);
	}
}


