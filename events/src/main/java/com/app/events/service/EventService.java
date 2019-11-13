package com.app.events.service;


import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;

public interface EventService {
	
	public Event findOne(Long id) throws ResourceNotFoundException;

	public Event create(Event event) throws Exception;

	public Event update(Event event) throws Exception;
	
	public Event prepareEventFields(Event toUpdate, Event newEvent);

	public void delete(Long id);
}
