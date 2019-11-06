package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;
import com.app.events.repository.EventRepository;
import com.app.events.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	
	 @Autowired
	    private EventRepository eventRepository;

	    @Override
	    public EventDTO findOne(Long id) {
	        Event event = this.eventRepository.findById(id).get();
	        EventDTO eventDTO = new EventDTO(event);

	        return eventDTO;
	    }

	    @Override
	    public EventDTO create(Event event) {
	        if(event.getId() != null){
	            throw new RuntimeException("Event already exists and has ID."); // custom exception here!
	        }
	        Event savedEvent = this.eventRepository.save(event);
	        EventDTO eventSavedDTO = new EventDTO(savedEvent);
	        
	        return eventSavedDTO;
	       
	    }

	    @Override
	    public EventDTO update(Event event) {
	        Event eventToUpdate = this.eventRepository.findById(event.getId()).get();
		    if (eventToUpdate == null) { 
		    	throw new RuntimeException("Not found event with this ID."); // custom exception here!
	        }	    

		    eventToUpdate.setName(event.getName());
		    eventToUpdate.setDescription(event.getDescription());
		    eventToUpdate.setFromDate(event.getFromDate());
		    eventToUpdate.setToDate(event.getToDate());
		    eventToUpdate.setEventState(event.getEventState());
		    eventToUpdate.setEventType(event.getEventType());
		    
	        Event updatedEvent = this.eventRepository.save(eventToUpdate);
	        EventDTO updatedEventDTO = new EventDTO(updatedEvent);
	        
	        return updatedEventDTO;
	        
	    }

	    @Override
	    public void delete(Long id) {
	        this.eventRepository.deleteById(id);
	    }

	

}
