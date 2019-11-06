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

import com.app.events.dto.PlaceDTO;
import com.app.events.model.Place;
import com.app.events.service.PlaceService;


@RestController
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;

	@GetMapping(value = "/api/place/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> getPlace(@PathVariable("id") Long id) {
		PlaceDTO place = placeService.findOne(id);
		if (place == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PlaceDTO>(place, HttpStatus.OK);
	}

	@PostMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> createPlace(@RequestBody Place place) throws Exception {
		try {
			PlaceDTO savedPlace = placeService.create(place);
			return new ResponseEntity<PlaceDTO>(savedPlace, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> updatePlace(@RequestBody Place place) throws Exception {
		PlaceDTO placeUpdated = placeService.update(place);
		if (placeUpdated == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<PlaceDTO>(placeUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/place/{id}")
	public ResponseEntity<PlaceDTO> deletePlace(@PathVariable("id") Long id) {
		placeService.delete(id);
		return new ResponseEntity<PlaceDTO>(HttpStatus.NO_CONTENT);
	}

}



