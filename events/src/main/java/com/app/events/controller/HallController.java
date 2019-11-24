package com.app.events.controller;

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

import com.app.events.dto.HallDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.HallMapper;
import com.app.events.model.Hall;
import com.app.events.service.HallService;

@RestController
public class HallController extends BaseController {

	@Autowired
	private HallService hallService;

	@GetMapping(value = "/api/hall/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> getHall(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Hall hall = hallService.findOne(id);
		return new ResponseEntity<>(HallMapper.toDTO(hall), HttpStatus.OK);
	}

	@PostMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO hallDTO) throws Exception {
		Hall savedHall = hallService.create(HallMapper.toHall(hallDTO));
		return new ResponseEntity<>(HallMapper.toDTO(savedHall), HttpStatus.CREATED);
	}

	@PutMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> updateHall(@RequestBody HallDTO hallDTO) throws Exception {
		Hall updatedHall = hallService.update(HallMapper.toHall(hallDTO));
		return new ResponseEntity<>(HallMapper.toDTO(updatedHall), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/hall/{id}")
	public ResponseEntity<HallDTO> deleteHall(@PathVariable("id") Long id) {
		hallService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}




