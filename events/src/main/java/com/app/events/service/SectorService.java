package com.app.events.service;

import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id);

	public Sector create(Sector sector) throws Exception;

	public Sector update(Sector sector) throws Exception;

	public void delete(Long id);

}
