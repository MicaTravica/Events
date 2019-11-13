package com.app.events.service;

import java.util.Collection;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;

public interface HallService {
	
	public Collection<Hall> findAll();
	
	public Hall findOne(Long id) throws ResourceNotFoundException;

	public Hall create(Hall hall) throws Exception;

	public Hall update(Hall hall) throws Exception;

	public void delete(Long id);

}
