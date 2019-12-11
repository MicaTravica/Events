package com.app.events.service;

import java.util.Collection;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;

public interface HallService {

	public Hall findOne(Long id) throws ResourceNotFoundException;

	public Hall create(Hall hall) throws Exception;

	public Hall update(Hall hall) throws Exception;

	public void delete(Long id);

	public Collection<Hall> findHallByEventId(Long id);

}
