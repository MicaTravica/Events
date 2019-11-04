package com.app.events.dto;

import java.util.HashSet;
import java.util.Set;
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

	// private Hall hall;
	private Set<PriceListDTO> priceLists = new HashSet<PriceListDTO>();
	private Set<SectorCapacityDTO> sectorCapacities = new HashSet<SectorCapacityDTO>();
	// private Set<Seat> seats;

	public SectorDTO(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.sectorRows = sector.getSectorRows();
		this.sectorColumns = sector.getSectorColumns(); 
		sector.getPriceLists().stream()
			.forEach(y-> this.priceLists.add(new PriceListDTO(y)));
		
		sector.getSectorCapacities()
			.forEach(x->this.sectorCapacities.add(new SectorCapacityDTO(x)));
			

	}



}
