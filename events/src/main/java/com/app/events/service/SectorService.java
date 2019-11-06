package com.app.events.service;

import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id);

	public Sector create(Sector sector);

	public Sector update(Sector sector);

	public void delete(Long id);

}
