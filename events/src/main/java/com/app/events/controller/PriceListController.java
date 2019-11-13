package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.PriceListDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.PriceListMapper;
import com.app.events.model.PriceList;
import com.app.events.service.PriceListService;

@RestController
public class PriceListController {

	@Autowired
	private PriceListService priceListService;

	@GetMapping(value = "/api/priceList/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> getPriceList(@PathVariable("id") Long id) throws ResourceNotFoundException {
		PriceList priceList = priceListService.findOne(id);
		return new ResponseEntity<>(PriceListMapper.toDTO(priceList), HttpStatus.OK);
	}

	@PostMapping(value = "/api/priceList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> createPriceList(@RequestBody PriceListDTO priceListDTO) throws Exception {
		PriceList savedPriceList = priceListService.create(PriceListMapper.toPriceList(priceListDTO));
		return new ResponseEntity<>(PriceListMapper.toDTO(savedPriceList), HttpStatus.CREATED);
	}

	@PutMapping(value = "/api/priceList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> updatePriceList(@RequestBody PriceListDTO priceListDTO) throws Exception {
		PriceList updatedPriceList = priceListService.update(PriceListMapper.toPriceList(priceListDTO));
		return new ResponseEntity<>(PriceListMapper.toDTO(updatedPriceList),HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/priceList/{id}")
	public ResponseEntity<PriceListDTO> deletePriceList(@PathVariable("id") Long id) {
		priceListService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
