package com.app.events.service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.PriceList;

public interface PriceListService {

	public PriceList findOne(Long id) throws ResourceNotFoundException;

	public PriceList create(PriceList sector) throws Exception;

	public PriceList update(PriceList sector) throws Exception;

	public void delete(Long id);

}
