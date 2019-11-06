package com.app.events.service;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Sector;

public interface SectorService {

	public Sector findOne(Long id);

	public Sector create(SectorDTO sector);

	public SectorDTO update(Sector sector);

	public void delete(Long id);

}
