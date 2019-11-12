package com.app.events.mapper;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.PriceList;

public class PriceListMapper {

    public static PriceListDTO toDTO(PriceList priceList) {
        return new PriceListDTO(priceList);
    }
    
    public static PriceList toPriceList(PriceListDTO priceListDTO) {
        return new PriceList (
            priceListDTO.getId(), 
            priceListDTO.getPrice(),
            priceListDTO.getEvent().toSimpleEvent(), //---> event mapper da odradi
            SectorMapper.toSector(priceListDTO.getSector())
        );
    }

}
