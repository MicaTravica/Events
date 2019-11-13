package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;
import com.app.events.model.Place;

public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return new EventDTO( event.getId(),event.getName(),
                            event.getDescription(), event.getFromDate(),
                            event.getToDate(), event.getEventState(),
                            event.getEventType(), PlaceMapper.toDTO(event.getPlace())
        );
    }
    
    public static Event toEvent(EventDTO eventDTO) {
        return new Event( eventDTO.getId(), eventDTO.getName(), eventDTO.getDescription(),
                        eventDTO.getFromDate(), eventDTO.getToDate(),eventDTO.getEventState(),
                        eventDTO.getEventType(), new Place(eventDTO.getPlace().getId()),
                        new HashSet<>(), new HashSet<>()
                    );
    }

}
