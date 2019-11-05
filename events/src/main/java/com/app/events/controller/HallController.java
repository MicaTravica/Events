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

import com.app.events.dto.HallDTO;
import com.app.events.model.Hall;
import com.app.events.service.HallService;




@RestController
public class HallController {

	@Autowired
	private HallService hallService;

	@GetMapping(value = "/api/hall/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> getHall(@PathVariable("id") Long id) {
		HallDTO hall = hallService.findOne(id);
		if (hall == null) {
			return new ResponseEntity<HallDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HallDTO>(hall, HttpStatus.OK);
	}

	@PostMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> createHall(@RequestBody Hall hall) throws Exception {
		try {
			HallDTO savedHall = hallService.create(hall);
			return new ResponseEntity<HallDTO>(savedHall, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<HallDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> updateHall(@RequestBody Hall hall) throws Exception {
		HallDTO hallUpdated = hallService.update(hall);
		if (hallUpdated == null) {
			return new ResponseEntity<HallDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<HallDTO>(hallUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/hall/{id}")
	public ResponseEntity<HallDTO> deleteHall(@PathVariable("id") Long id) {
		hallService.delete(id);
		return new ResponseEntity<HallDTO>(HttpStatus.NO_CONTENT);
	}

}




