package com.app.events.service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.PriceList;

public interface PriceListService {

	public PriceList findOne(Long id) throws ResourceNotFoundException;

	public PriceList create(PriceList sector) throws ResourceExistsException, ResourceNotFoundException;

	public PriceList update(PriceList sector) throws ResourceNotFoundException;

	public void delete(Long id);

}
