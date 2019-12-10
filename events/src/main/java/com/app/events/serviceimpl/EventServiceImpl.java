package com.app.events.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.DateException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.EventRepository;
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
		return this.eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event"));
	}

	// provera da li ima bar jedna hala izabrana
	// negde dodati kreiranje priceliste
	@Override
	public Event create(Event event) throws Exception {
		event.setId(null);
		if (event.getFromDate() == null || event.getToDate() == null || event.getFromDate().after(event.getToDate())) {
			throw new DateException("Dates can not be null and to date must be after from date");
		}
		Set<Hall> halls = new HashSet<>();
		for (Hall h : event.getHalls()) {
			Hall ha = hallService.findOne(h.getId());
			if (eventRepository.hallHaveEvent(ha.getId(), event.getFromDate(), event.getToDate())) {
				throw new DateException("Hall is not available in desired period");
			}
			for (Sector s : h.getSectors()) {
				sectorService.findOne(s.getId());
			}
			halls.add(ha);
		}
		event.setHalls(halls);
		event.setMediaList(new HashSet<>());
		event.setPriceLists(new HashSet<>());
		return eventRepository.save(event);
	}

	@Override
	public Event update(Event event) throws Exception {
		Event eventToUpdate = this.findOne(event.getId());
		if (event.getFromDate() == null || event.getToDate() == null || event.getFromDate().after(event.getToDate())) {
			throw new DateException("Dates can not be null and to date must be after from date");
		}
		Set<Hall> halls = new HashSet<>();
		for (Hall h : event.getHalls()) {
			Hall ha = hallService.findOne(h.getId());
			if (eventRepository.hallHaveEventUpdate(h.getId(), event.getFromDate(), event.getToDate(), event.getId())) {
				throw new DateException("Hall is not available in desired period");
			}
			halls.add(ha);
		}
		event.setHalls(halls);
		eventToUpdate = this.prepareEventFields(eventToUpdate, event);
		return this.eventRepository.save(eventToUpdate);
	}

	// hocemo li stavljati da je cancled?
	// nema bas smisla da brisemo
	@Override
	public void delete(Long id) throws ResourceNotFoundException {
		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event"));
		event.setHalls(new HashSet<>());
		eventRepository.save(event);
		this.eventRepository.deleteById(id);
	}
	
	@Override
	public void canceledEvent() {
		//nesto
	}

	public Event prepareEventFields(Event toUpdate, Event newEvent) {
		toUpdate.setName(newEvent.getName());
		toUpdate.setDescription(newEvent.getDescription());
		toUpdate.setEventState(newEvent.getEventState());
		toUpdate.setEventType(newEvent.getEventType());
		toUpdate.setFromDate(newEvent.getFromDate());
		toUpdate.setToDate(newEvent.getToDate());
		return toUpdate;
	}
}
