package com.app.events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDTO {

	private Long id;
    private double price;
    private Long eventId;
    private Long sectorId;
    

}