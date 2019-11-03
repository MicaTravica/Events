package com.app.events.dto;

import com.app.events.model.PriceList;
import com.app.events.model.Sector;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceListDTO {

    private Long id;
    private double price;
    //private Event event;
    private SectorDTO sector;
    
    public PriceListDTO(PriceList priceList) {
        this.id = priceList.getId();
        this.price = priceList.getPrice();
        Sector priceListSector = 
            new Sector(
                priceList.getSector().getId(),
                priceList.getSector().getName(),
                priceList.getSector().getSectorColumns(),
                priceList.getSector().getSectorRows()
            );
        this.sector = new SectorDTO(priceListSector);
        //this.event = priceList.getEvent()
    }

}
