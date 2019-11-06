package com.app.events.service;

import com.app.events.dto.PlaceDTO;
import com.app.events.model.Place;

public interface PlaceService {

	public PlaceDTO findOne(Long id);

	public PlaceDTO create(Place place);

	public PlaceDTO update(Place place);

	public void delete(Long id);
}
