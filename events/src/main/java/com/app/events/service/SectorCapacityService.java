package com.app.events.service;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.model.SectorCapacity;

public interface SectorCapacityService {

	public SectorCapacityDTO findOne(Long id);

	public SectorCapacityDTO create(SectorCapacity sector);

	public SectorCapacityDTO update(SectorCapacity sector);

	public void delete(Long id);

}
