package com.app.events.service;

import java.util.Collection;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id) throws ResourceNotFoundException;

	public Sector create(Sector sector) throws Exception;

	public Sector update(Sector sector) throws Exception;

	public void delete(Long id);

	public Sector prepareSectorFields(Sector toUpdate, Sector newSector);

	Collection<Sector> findAllByHallAndEvent(Long hallId, Long eventId);

}
