package com.app.events.service;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;

public interface EventService {

	public EventDTO findOne(Long id);

	public EventDTO create(Event event);

	public EventDTO update(Event event);

	public void delete(Long id);
}
