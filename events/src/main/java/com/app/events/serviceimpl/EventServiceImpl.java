package com.app.events.serviceimpl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.events.exception.BadEventStateException;
import com.app.events.exception.CollectionIsEmptyException;
import com.app.events.exception.DateException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.SectorIsNotInThisHallException;
import com.app.events.exception.SectorPriceListException;
import com.app.events.exception.TicketIsBoughtException;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.SearchParamsEvent;
import com.app.events.model.Sector;
import com.app.events.repository.EventRepository;
import com.app.events.service.EventService;
import com.app.events.service.HallService;
import com.app.events.service.SectorService;
import com.app.events.service.TicketService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private HallService hallService;

	@Autowired
	private SectorService sectorService;

	@Autowired
	private TicketService ticketService;

	@Override
	public Event findOne(Long id) throws ResourceNotFoundException {
		return this.eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event"));
	}

	@Override
	public Collection<Event> findAllNotFinished() {
		return this.eventRepository.findAllNotFinished();
  }
  
  @Override
	public Event findOneAndLoadHalls(Long id) throws ResourceNotFoundException {
		Event event = this.eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event"));
		Set<Hall> halls = this.hallService.findHallByEventId(id)
									.stream().collect(Collectors.toSet());
		event.setHalls(halls);

		for(Hall h: halls) {
			h.setSectors(this.sectorService.findAllByHallAndEvent(h.getId(), event.getId()).stream().collect(Collectors.toSet()));
		}
		return event;
	}

	@Override
	public Event create(Event event) throws Exception {
		event.setId(null);
		if (event.getFromDate() == null || event.getToDate() == null || event.getFromDate().after(event.getToDate()))
			throw new DateException("Dates can not be null and to date must be after from date");

		if (!(event.getEventState().equals(EventState.AVAILABLE)
				|| event.getEventState().equals(EventState.NOT_AVAILABLE)))
			throw new BadEventStateException("Event state must be available or not available!");

		Set<Hall> halls = new HashSet<>();
		if (event.getHalls().isEmpty())
			throw new CollectionIsEmptyException("hall");

		Map<Long, PriceList> priceListMap = event.getPriceLists().stream()
				.collect(Collectors.toMap(x -> x.getSector().getId(), x -> x));
		for (Hall h : event.getHalls()) {
			Hall ha = hallService.findOne(h.getId());
			if (eventRepository.hallHaveEvent(ha.getId(), event.getFromDate(), event.getToDate())) {
				throw new DateException("Hall is not available in desired period");
			}
			// proveriti da li je sektor u hali
			prepareForUpdateSectors(ha.getId(), h.getSectors(), priceListMap);
			halls.add(ha);
		}
		event.setHalls(halls);
		event.setMediaList(new HashSet<>());
		event.setPriceLists(new HashSet<>());
		return eventRepository.save(event);
	}

	@Override
	public Event updateEventState(Event event, EventState state) {
		event.setEventState(state);
		return eventRepository.save(event);
	}

	@Override
	public Event update(Event event) throws Exception {
		Event eventToUpdate = this.findOne(event.getId());

		if (event.getFromDate() == null || event.getToDate() == null || event.getFromDate().after(event.getToDate()))
			throw new DateException("Dates can not be null and to date must be after from date");

		boolean ready = prepareForUpdateEventState(event, eventToUpdate);
		if (!event.getFromDate().equals(eventToUpdate.getFromDate())
				|| !event.getToDate().equals(eventToUpdate.getToDate())) {
			Collection<Hall> halls = hallService.findHallByEventId(event.getId());
			for (Hall h : halls) {
				if (eventRepository.hallHaveEventUpdate(h.getId(), event.getFromDate(), event.getToDate(),
						event.getId())) {
					throw new DateException("Hall is not available in desired period");
				}
			}
		}

		if (!ready) {
			// vratiti ljudima novac jer je dogadaj otkazan
		}

		eventToUpdate = this.prepareEventFields(eventToUpdate, event);
		return this.eventRepository.save(eventToUpdate);
	}

	@Override
	public Event updateHall(Event event) throws Exception {
		Event eventToUpdate = findOne(event.getId());
		if (ticketService.ticketForEventIsSale(event.getId()))
			throw new TicketIsBoughtException("Ticket is bought for this event, you can not change the hall!");

		Set<Hall> halls = new HashSet<>();
		if (event.getHalls().isEmpty())
			throw new CollectionIsEmptyException("hall");

		Map<Long, PriceList> priceListMap = event.getPriceLists().stream()
				.collect(Collectors.toMap(x -> x.getSector().getId(), x -> x));
		for (Hall h : event.getHalls()) {
			Hall ha = hallService.findOne(h.getId());
			if (eventRepository.hallHaveEventUpdate(ha.getId(), eventToUpdate.getFromDate(), eventToUpdate.getToDate(),
					eventToUpdate.getId())) {
				throw new DateException("Hall is not available in desired period");
			}
			prepareForUpdateSectors(ha.getId(), h.getSectors(), priceListMap);
			halls.add(ha);
		}
		return eventRepository.save(eventToUpdate);
	}

	@Override
	public void delete(Long id) throws ResourceNotFoundException {
		Event event = findOne(id);
		event.setHalls(new HashSet<>());
		eventRepository.save(event);
		this.eventRepository.deleteById(id);
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

	private void prepareForUpdateSectors(Long hallId, Collection<Sector> sectors, Map<Long, PriceList> priceListMap)
			throws Exception {
		if (sectors.isEmpty()) {
			throw new CollectionIsEmptyException("sector");
		}
		for (Sector s : sectors) {
			Sector sector = sectorService.findOne(s.getId());
			if (sector.getHall().getId() != hallId)
				throw new SectorIsNotInThisHallException();
			if (priceListMap.get(s.getId()) == null)
				throw new SectorPriceListException();
		}
	}

	private boolean prepareForUpdateEventState(Event event, Event eventToUpdate) throws BadEventStateException {
		if (!event.getEventState().equals(eventToUpdate.getEventState())) {
			if (event.getEventState().equals(EventState.NOT_AVAILABLE)) {
				if (!eventToUpdate.getEventState().equals(EventState.AVAILABLE)
						&& !eventToUpdate.getEventState().equals(EventState.CANCELED)) {
					throw new BadEventStateException("Event state can be changed to available or canceled!");
				} else if (eventToUpdate.getEventState().equals(EventState.AVAILABLE)
						&& eventToUpdate.getFromDate().before(new Date())) {
					throw new BadEventStateException("Event state can not be available!");
				}
			} else if (event.getEventState().equals(EventState.AVAILABLE)) {

				if (!eventToUpdate.getEventState().equals(EventState.CANCELED)) {
					return true;
				} else {
					throw new BadEventStateException("Event state can be changed only to canceled!");
				}
			} else {
				throw new BadEventStateException("Event state can not be changed!");
			}
		}
		return false;
	}

	@Override
	public Page<Event> search(SearchParamsEvent params) {
		if (params.getSortBy().equals("")) {
			params.setSortBy("fromDate");
		}
		Pageable pageable;
		if(params.isAscending()) {
			pageable = PageRequest.of(params.getNumOfPage(), params.getSizeOfPage(),
					Sort.by(params.getSortBy()).ascending());
		} else {
			pageable = PageRequest.of(params.getNumOfPage(), params.getSizeOfPage(),
					Sort.by(params.getSortBy()).descending());
		}
		Page<Event> found = eventRepository.search(params.getName(), params.getFromDate(), params.getToDate(),
				params.getEventState(), params.getEventType(), params.getPlaceId(), pageable);
		return found;
	}

	
}
