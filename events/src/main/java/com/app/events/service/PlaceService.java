package com.app.events.service;


import java.util.List;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Place;

public interface PlaceService {

	public List<Place> findAll();
	
	public Place findOne(Long id) throws ResourceNotFoundException;

	public Place create(Place place) throws Exception;

	public Place update(Place place) throws Exception;

	public void delete(Long id);

	public void coordinatesCheckReserved(Place place) throws ResourceExistsException;

	public Place setPlaceFields(Place placeToUpdate, Place place);
}
