package com.app.events.controller;


import java.util.ArrayList;
import java.util.List;

import com.app.events.dto.PlaceDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.PlaceMapper;
import com.app.events.model.Place;
import com.app.events.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController extends BaseController {

	@Autowired
	private PlaceService placeService;

	@GetMapping(value = "/api/places", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlaceDTO>> getPlaces() {
		List<Place> places = placeService.findAll();
		List<PlaceDTO> placesDTO = new ArrayList<PlaceDTO>();
		for (Place place: places) {
			placesDTO.add(PlaceMapper.toDTO(place));
		}
		return new ResponseEntity<>(placesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/api/place/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlaceDTO> getPlace(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Place place = placeService.findOne(id);
		return new ResponseEntity<>(PlaceMapper.toDTO(place), HttpStatus.OK);
	}

	@PostMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) throws Exception {
			Place savedPlace = placeService.create(PlaceMapper.toPlace(placeDTO));
			return new ResponseEntity<>(PlaceMapper.toDTO(savedPlace), HttpStatus.CREATED);
	}

	@PutMapping(value = "/api/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PlaceDTO> updatePlace(@RequestBody PlaceDTO placeDTO) throws Exception {
		Place updatedPlace = placeService.update(PlaceMapper.toPlace(placeDTO));
		return new ResponseEntity<>(PlaceMapper.toDTO(updatedPlace), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/place/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PlaceDTO> deletePlace(@PathVariable("id") Long id) {
		placeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}



