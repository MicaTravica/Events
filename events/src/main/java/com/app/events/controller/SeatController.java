package com.app.events.controller;

import com.app.events.dto.SeatDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.SeatMapper;
import com.app.events.model.Seat;
import com.app.events.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController extends BaseController{

	@Autowired
	private SeatService seatService;

	@GetMapping(value = "/api/seats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> getSeat(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Seat seat = seatService.findOne(id);
		return new ResponseEntity<>(SeatMapper.toDTO(seat), HttpStatus.OK);
	}

	@PostMapping(value = "/api/seats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> createSeat(@RequestBody SeatDTO seatDTO) throws Exception {
		Seat savedSeat = seatService.create(SeatMapper.toSeat(seatDTO));
		return new ResponseEntity<>(SeatMapper.toDTO(savedSeat), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/api/seats/{id}")
	public ResponseEntity<Seat> deleteSeat(@PathVariable("id") Long id) {
		seatService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
