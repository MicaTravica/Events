package com.app.events.mapper;

import java.util.stream.Collectors;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;

public class EventMapper {

	public static EventDTO toDTO(Event event) {
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getFromDate(),
				event.getToDate(), event.getEventState(), event.getEventType(),
				event.getHalls().stream().map(HallMapper::toDTO).collect(Collectors.toSet()),
				event.getPriceLists().stream().map(PriceListMapper::toDTO).collect(Collectors.toSet()),
				event.getMediaList().stream().map(MediaMapper::toDTO).collect(Collectors.toSet()));
	}

	public static Event toEvent(EventDTO eventDTO) {
		return new Event(eventDTO.getId(), eventDTO.getName(), eventDTO.getDescription(), eventDTO.getFromDate(),
				eventDTO.getToDate(), eventDTO.getEventState(), eventDTO.getEventType(),
				eventDTO.getHalls().stream().map(HallMapper::toHall).collect(Collectors.toSet()),
				eventDTO.getPriceLise().stream().map(PriceListMapper::toPriceList).collect(Collectors.toSet()),
				eventDTO.getMediaList().stream().map(MediaMapper::toMedia).collect(Collectors.toSet()));
	}

}
