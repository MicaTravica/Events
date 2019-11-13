package com.app.events.service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.SectorCapacity;

public interface SectorCapacityService {

	public SectorCapacity findOne(Long id) throws ResourceNotFoundException;

	public SectorCapacity create(SectorCapacity sector) throws Exception;

	public SectorCapacity update(SectorCapacity sector) throws Exception;

	public void delete(Long id);

}
