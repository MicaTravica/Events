package com.app.events.serviceimpl;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.DateException;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.repository.EventRepository;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.EventService;
import com.app.events.service.HallService;
import com.app.events.service.SectorService;

@Service
public class EventServiceImpl implements EventService {

	
	 @Autowired
	 private EventRepository eventRepository;

	 @Autowired
	 private HallService hallService;
	 
	 @Autowired
	 private SectorService sectorService;
	 
	 
	 @Override
	 public Event findOne(Long id) throws ResourceNotFoundException {
		return this.eventRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event"));
	 }
	 

	  @Override
	  public Event create(Event event) throws Exception {
		  if(event.getId() != null){
			  throw new ResourceExistsException("Event");
	      }
		  
		  if(event.getFromDate().after(event.getToDate())) {
			  throw new DateException();
		  }
		  
		  Set<Hall> halls = new HashSet<>();
		  
		  for(Hall h : event.getHalls()) {
			  Hall ha = hallService.findOne(h.getId());
			  if(eventRepository.hallHaveEvent(h.getId(), event.getFromDate(), event.getToDate())) {
				  throw new DateException();
			  }

			  halls.add(ha);
		  }
		  
		  event.setHalls(halls);
		 
	      return this.eventRepository.save(event);
	       
	  }

	  @Override
	  public Event update(Event event) throws Exception {
		  Event eventToUpdate = this.findOne(event.getId());
		  if(event.getFromDate().after(event.getToDate())) {
			  throw new DateException();
		  }
		  
		  Set<Hall> halls = new HashSet<>();
		  
		  for(Hall h : event.getHalls()) {
			  Hall ha = hallService.findOne(h.getId());
			  if(eventRepository.hallHaveEventUpdate(h.getId(), event.getFromDate(), event.getToDate(), event.getId())) {
				  throw new DateException();
			  }

			  halls.add(ha);
		  }
		  
		  event.setHalls(halls);
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
