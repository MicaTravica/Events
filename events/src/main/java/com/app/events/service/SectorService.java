package com.app.events.service;

import java.util.Optional;

import com.app.events.exception.SectorDoesntExistException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id) throws SectorDoesntExistException;

	public Sector create(Sector sector) throws Exception;

	public Sector update(Sector sector) throws Exception;

	public void delete(Long id) throws IllegalArgumentException;

	public Sector prepareSectorFields(Sector toUpdate, Sector newSector, Optional<Hall> hallOpt);

}
