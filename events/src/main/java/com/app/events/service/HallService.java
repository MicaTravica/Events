package com.app.events.service;

import java.util.Collection;

import com.app.events.model.Hall;

public interface HallService {
	
	public Collection<Hall> findAll();
	
	public Hall findOne(Long id);

	public Hall create(Hall hall);

	public Hall update(Hall hall);

	public void delete(Long id);

}
