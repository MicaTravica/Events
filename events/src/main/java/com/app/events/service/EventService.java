package com.app.events.service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;

public interface EventService {

	Event findOne(Long id) throws ResourceNotFoundException;

	Event create(Event event) throws Exception;

	Event update(Event event) throws Exception;

	Event prepareEventFields(Event toUpdate, Event newEvent);

	void delete(Long id) throws ResourceNotFoundException;

	Event updateHall(Event event) throws Exception;
}
