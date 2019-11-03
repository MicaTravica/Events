package com.app.events.dto;

import com.app.events.model.SectorCapacity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorCapacityDTO {

    private Long id;

    //private Set<Ticket> tickets;
    private SectorDTO sector;

    private int capacity;
	private int free;

	public SectorCapacityDTO(SectorCapacity sectorCapacity) {
        this.id = sectorCapacity.getId();
        //this.sector = new SectorDTO(sectorCapacity.getSector());
        this.capacity = sectorCapacity.getCapacity();
        this.free = sectorCapacity.getFree();
	}

}
