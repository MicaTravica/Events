package com.app.events.service;

import java.util.Collection;

import com.app.events.model.Event;

public interface EventService {

	public Collection<Event> findAll();
	
	public Event findOne(Long id);

	public Event create(Event event);

	public Event update(Event event);

	public void delete(Long id);
}
