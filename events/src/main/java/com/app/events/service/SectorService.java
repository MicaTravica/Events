package com.app.events.service;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Sector;

public interface SectorService {

	public SectorDTO findOne(Long id);

	public SectorDTO create(Sector sector);

	public SectorDTO update(Sector sector);

	public void delete(Long id);

}
