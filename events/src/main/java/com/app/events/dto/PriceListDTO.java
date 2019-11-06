package com.app.events.dto;

import com.app.events.model.PriceList;
import java.util.HashSet;

import com.app.events.model.Event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceListDTO {

    private Long id;
    private double price;
    private EventDTO event;
    private SectorDTO sector;
    
    public PriceListDTO(PriceList priceList) {
        this.id = priceList.getId();
        this.price = priceList.getPrice();
        this.sector = SectorDTO.makeSimpleSectorDTO(priceList.getSector());
        this.event = this.makeEventDTO(priceList.getEvent());
    }

	public PriceList toPriceList() {
		return new PriceList (
                    this.getId(), 
                    this.getPrice(),
                    this.getEvent().toSimpleEvent(),
                    this.getSector().toSimpleSector()
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
                    new HashSet<>()
                )
            )
            : null;
    }
}