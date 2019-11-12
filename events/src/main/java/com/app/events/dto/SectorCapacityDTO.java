package com.app.events.dto;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.SectorCapacity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorCapacityDTO {

    private Long id;
    private SectorDTO sector;
    private int capacity;
	private int free;

	public SectorCapacityDTO(SectorCapacity sectorCapacity) {
        this.id = sectorCapacity.getId();
        this.capacity = sectorCapacity.getCapacity();
        this.free = sectorCapacity.getFree();
        this.sector = SectorMapper.toDTO(sectorCapacity.getSector());
	}

}
