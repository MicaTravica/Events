package com.app.events.service;

import com.app.events.dto.HallDTO;
import com.app.events.model.Hall;

public interface HallService {
	
	public HallDTO findOne(Long id);

	public HallDTO create(Hall hall);

	public HallDTO update(Hall hall);

	public void delete(Long id);

}
