package com.app.events.mapper;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.Event;
import com.app.events.model.PriceList;
import com.app.events.model.Sector;

public class PriceListMapper {

    public static PriceListDTO toDTO(PriceList priceList) {
        return new PriceListDTO(
            priceList.getId(), 
            priceList.getPrice(),
            priceList.getEvent().getId(), 
            priceList.getSector().getId()
        );
    }
    
    public static PriceList toPriceList(PriceListDTO priceListDTO) {
        return new PriceList (
            priceListDTO.getId(), 
            priceListDTO.getPrice(),
            new Event(priceListDTO.getEventId()) ,
            new Sector(priceListDTO.getSectorId())
        );
    }

}
