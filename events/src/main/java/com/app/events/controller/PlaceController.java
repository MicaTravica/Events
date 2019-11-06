package com.app.events.controller;

import java.util.ArrayList;
import java.util.Collection;

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

	@GetMapping(value = "/api/places", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PlaceDTO>> getPlaces(){
		Collection<Place> places = placeService.findAll();
		Collection<PlaceDTO> placesDTO = new ArrayList<>();
		for(Place p : places) {
			placesDTO.add(new PlaceDTO(p));
		}
		return new ResponseEntity<>(placesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/place/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> getPlace(@PathVariable("id") Long id) {
		Place place = placeService.findOne(id);
		if (place == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(place), HttpStatus.OK);
	}

	@PostMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) throws Exception {
		try {
			Place place = new Place(placeDTO);
			Place savedPlace = placeService.create(place);
			return new ResponseEntity<PlaceDTO>(new PlaceDTO(savedPlace), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> updatePlace(@RequestBody PlaceDTO placeDTO) throws Exception {
		Place place = new Place(placeDTO);
		Place updatedPlace = placeService.update(place);
		if (updatedPlace == null) {
			return new ResponseEntity<PlaceDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<PlaceDTO>(new PlaceDTO(updatedPlace), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/place/{id}")
	public ResponseEntity<PlaceDTO> deletePlace(@PathVariable("id") Long id) {
		placeService.delete(id);
		return new ResponseEntity<PlaceDTO>(HttpStatus.NO_CONTENT);
	}

}



