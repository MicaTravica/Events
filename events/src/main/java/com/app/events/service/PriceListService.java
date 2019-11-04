package com.app.events.service;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.PriceList;

public interface PriceListService {

	public PriceListDTO findOne(Long id);

	public PriceListDTO create(PriceList sector);

	public PriceListDTO update(PriceList sector);

	public void delete(Long id);

}
