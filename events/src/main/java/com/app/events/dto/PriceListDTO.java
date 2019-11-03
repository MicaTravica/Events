package com.app.events.dto;

import com.app.events.model.PriceList;

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
            //this.event = priceList.getEvent()
            //this.sector = new SectorDTO(priceList.getSector());
    }

}
