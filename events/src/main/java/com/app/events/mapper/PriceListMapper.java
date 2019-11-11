package com.app.events.mapper;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.PriceList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceListMapper {

    @Autowired
    private SectorMapper sectorMapper;

    public PriceListDTO toDTO(PriceList priceList) {
        return new PriceListDTO(priceList);
    }
    
    public PriceList toPriceList(PriceListDTO priceListDTO) {
        return new PriceList (
            priceListDTO.getId(), 
            priceListDTO.getPrice(),
            priceListDTO.getEvent().toSimpleEvent(), //---> event mapper da odradi
            sectorMapper.toSector(priceListDTO.getSector())
        );
    }

}
