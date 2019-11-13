package com.app.events.service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id) throws ResourceNotFoundException;

	public Sector create(Sector sector) throws ResourceExistsException, ResourceNotFoundException;

	public Sector update(Sector sector) throws ResourceNotFoundException;

	public void delete(Long id);

	public Sector prepareSectorFields(Sector toUpdate, Sector newSector);

}
