package com.app.events.mapper;

import java.util.HashSet;
import java.util.stream.Collectors;

import com.app.events.dto.EventDTO;
import com.app.events.model.Event;

public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return new EventDTO( event.getId(),event.getName(),
                            event.getDescription(), event.getFromDate(),
                            event.getToDate(), event.getEventState(),
                            event.getEventType(), 
                            event.getHalls().stream().map(HallMapper::toDTO).collect(Collectors.toSet()),
                            event.getMediaList().stream().map(MediaMapper::toDTO).collect(Collectors.toSet())
        );
    }
    
    public static Event toEvent(EventDTO eventDTO) {
        return new Event( eventDTO.getId(), eventDTO.getName(), eventDTO.getDescription(),
                        eventDTO.getFromDate(), eventDTO.getToDate(),eventDTO.getEventState(),
                        eventDTO.getEventType(), eventDTO.getHalls().stream().map(HallMapper::toHall).collect(Collectors.toSet()),
                        new HashSet<>(), eventDTO.getMediaList().stream().map(MediaMapper::toMedia).collect(Collectors.toSet())
                    );
    }

}
