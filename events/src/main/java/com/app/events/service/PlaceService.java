package com.app.events.service;

import java.util.Collection;

import com.app.events.model.Place;

public interface PlaceService {

	public Collection<Place> findAll();
	
	public Place findOne(Long id);

	public Place create(Place place);

	public Place update(Place place);

	public void delete(Long id);
}
