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

import com.app.events.dto.HallDTO;
import com.app.events.mapper.HallMapper;
import com.app.events.model.Hall;
import com.app.events.service.HallService;

@RestController
public class HallController {

	@Autowired
	private HallService hallService;
	
	@GetMapping(value = "/api/halls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HallDTO>> getHalls(){
		Collection<Hall> halls = hallService.findAll();
		Collection<HallDTO> hallsDTO = new ArrayList<>();
		for(Hall h : halls) {
			hallsDTO.add(HallMapper.toDTO(h));
		}
		return new ResponseEntity<>(hallsDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/api/hall/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> getHall(@PathVariable("id") Long id) {
		Hall hall = hallService.findOne(id);
		if (hall == null) {
			return new ResponseEntity<HallDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HallDTO>(HallMapper.toDTO(hall), HttpStatus.OK);
	}

	@PostMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO hallDTO) throws Exception {
		try {
			Hall hall = new Hall(hallDTO);
			Hall savedHall = hallService.create(hall);
			return new ResponseEntity<HallDTO>(HallMapper.toDTO(savedHall), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<HallDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/hall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HallDTO> updateHall(@RequestBody HallDTO hallDTO) throws Exception {
		Hall hall = new Hall(hallDTO);
		Hall updatedHall = hallService.update(hall);
		if (updatedHall == null) {
			return new ResponseEntity<HallDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<HallDTO>(HallMapper.toDTO(updatedHall), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/hall/{id}")
	public ResponseEntity<HallDTO> deleteHall(@PathVariable("id") Long id) {
		hallService.delete(id);
		return new ResponseEntity<HallDTO>(HttpStatus.NO_CONTENT);
	}

}




