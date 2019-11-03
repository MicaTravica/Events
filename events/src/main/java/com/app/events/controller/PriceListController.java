package com.app.events.controller;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.PriceList;
import com.app.events.service.PriceListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class PriceListController {

	@Autowired
	private PriceListService priceListService;

	@GetMapping(value = "/api/priceList/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> getPriceList(@PathVariable("id") Long id) {
		PriceListDTO priceList = priceListService.findOne(id);
		if (priceList == null) {
			return new ResponseEntity<PriceListDTO>(HttpStatus.NOT_FOUND);
		}
		System.out.println(priceList.toString());
		return new ResponseEntity<PriceListDTO>(priceList, HttpStatus.OK);
	}

	@PostMapping(value = "/api/priceList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> createPriceList(@RequestBody PriceList sector) throws Exception {
		try {
			PriceListDTO savedPriceList = priceListService.create(sector);
			return new ResponseEntity<PriceListDTO>(savedPriceList, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<PriceListDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/priceList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceListDTO> updatePriceList(@RequestBody PriceList sector) throws Exception {
		PriceListDTO updatedPriceList = priceListService.update(sector);
		if (updatedPriceList == null) {
			return new ResponseEntity<PriceListDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<PriceListDTO>(updatedPriceList, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/priceList/{id}")
	public ResponseEntity<PriceListDTO> deletePriceList(@PathVariable("id") Long id) {
		priceListService.delete(id);
		return new ResponseEntity<PriceListDTO>(HttpStatus.NO_CONTENT);
	}

}
