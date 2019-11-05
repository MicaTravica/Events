package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;
import com.app.events.service.EventService;

@RestController
public class EventController {
	
	@Autowired
	private EventService eventService;

	@GetMapping(value = "/api/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> getEvent(@PathVariable("id") Long id) {
		EventDTO event = eventService.findOne(id);
		if (event == null) {
			return new ResponseEntity<EventDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EventDTO>(event, HttpStatus.OK);
	}

	@PostMapping(value = "/api/event", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> createEvent(@RequestBody Event event) throws Exception {
		try {
			EventDTO savedEvent = eventService.create(event);
			return new ResponseEntity<EventDTO>(savedEvent, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<EventDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/event", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> updateEvent(@RequestBody Event event) throws Exception {
		EventDTO eventUpdated = eventService.update(event);
		if (eventUpdated == null) {
			return new ResponseEntity<EventDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<EventDTO>(eventUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/event/{id}")
	public ResponseEntity<EventDTO> deleteEvent(@PathVariable("id") Long id) {
		eventService.delete(id);
		return new ResponseEntity<EventDTO>(HttpStatus.NO_CONTENT);
	}

}
