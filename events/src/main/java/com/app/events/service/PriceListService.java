package com.app.events.service;

import com.app.events.model.PriceList;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface PriceListService {

	public PriceList findOne(Long id) throws ResourceNotFoundException;

	public PriceList create(PriceList sector) throws Exception;

	public PriceList update(PriceList sector) throws Exception;

	public void delete(Long id);

}
