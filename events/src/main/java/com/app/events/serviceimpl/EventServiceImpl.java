package com.app.events.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Place;
import com.app.events.repository.EventRepository;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	
	 @Autowired
	 private EventRepository eventRepository;

	 @Autowired
	 private PlaceRepository placeRepository;

	 
	 @Override
	 public Event findOne(Long id) throws ResourceNotFoundException {
		return this.eventRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event"));
	 }
	 

	  @Override
	  public Event create(Event event) throws Exception {
		  if(event.getId() != null){
			  throw new ResourceExistsException("Event");
	      }
	      Place place = placeRepository.findById(event.getPlace().getId()).orElseThrow(() -> new ResourceNotFoundException("Place"));
	      event.setPlace(place);
	      return this.eventRepository.save(event);
	       
	  }

	  @Override
	  public Event update(Event event) throws Exception {
		  Event eventToUpdate = this.findOne(event.getId());
		  eventToUpdate = this.prepareEventFields(eventToUpdate, event);
		  return this.eventRepository.save(eventToUpdate);   
	  }

	  @Override
	  public void delete(Long id) {
        this.eventRepository.deleteById(id);
      }
	  
	  public Event prepareEventFields(Event toUpdate, Event newEvent)
	  {
		  toUpdate.setName(newEvent.getName());
		  toUpdate.setDescription(newEvent.getDescription());
		  toUpdate.setEventState(newEvent.getEventState());
		  toUpdate.setEventType(newEvent.getEventType());
		  toUpdate.setFromDate(newEvent.getFromDate());
		  toUpdate.setToDate(newEvent.getToDate());
	      
		  return toUpdate;
	  }

	

}
