package com.app.events.dto;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.SectorCapacity;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorCapacityDTO {

	@Autowired
	private SectorMapper sectorMapper;

    private Long id;
    private SectorDTO sector;
    private int capacity;
	private int free;

	public SectorCapacityDTO(SectorCapacity sectorCapacity) {
        this.id = sectorCapacity.getId();
        this.capacity = sectorCapacity.getCapacity();
        this.free = sectorCapacity.getFree();
        this.sector = sectorMapper.toDTO(sectorCapacity.getSector());
	}

}
