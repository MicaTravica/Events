package com.app.events.dto;

import com.app.events.model.PriceList;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.Event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceListDTO {


	@Autowired
	private SectorMapper sectorMapper;

	private Long id;
    private double price;
    private EventDTO event;
    private SectorDTO sector;
    
    public PriceListDTO(PriceList priceList) {
        this.id = priceList.getId();
        this.price = priceList.getPrice();
        this.sector = sectorMapper.toDTO(priceList.getSector());
        this.event = this.makeEventDTO(priceList.getEvent());
    }

	public PriceList toPriceList() {
		return new PriceList (
                    this.getId(), 
                    this.getPrice(),
                    this.getEvent().toSimpleEvent(),
                    sectorMapper.toSector(this.getSector())
                );
    }

    public EventDTO makeEventDTO(Event event)
    {
        return event != null ? 
            new EventDTO(
                new Event(event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getFromDate(),
                    event.getToDate(),
                    event.getEventState(),
                    event.getEventType(), 
                    event.getPlace(),  // ovo jos kad ostali zavrse pogledaj!!!
                    new HashSet<>(),
                    null
                )
            )
            : null;
    }
}