package com.app.events.service;

import com.app.events.exception.SectorCapacityDoesntExistException;
import com.app.events.model.SectorCapacity;

public interface SectorCapacityService {

	public SectorCapacity findOne(Long id) throws SectorCapacityDoesntExistException;

	public SectorCapacity create(SectorCapacity sector) throws Exception;

	public SectorCapacity update(SectorCapacity sector) throws Exception;

	public void delete(Long id);

}
