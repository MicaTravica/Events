package com.app.events.controller;

import com.app.events.dto.SeatDTO;
import com.app.events.model.Seat;
import com.app.events.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.SeatMapper;

@RestController
public class SeatController extends BaseController{

	@Autowired
	private SeatService seatService;
	
	@GetMapping(value = "/api/seats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> getSeat(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Seat seat = seatService.findOne(id);
		return new ResponseEntity<>(SeatMapper.toDTO(seat), HttpStatus.OK);
	}

	@PutMapping(value = "/api/seats", consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SeatDTO> updateSeat(SeatDTO seatDTO) throws Exception {
		Seat updatedSeat = seatService.update(SeatMapper.toSeat(seatDTO));
		return new ResponseEntity<>(SeatMapper.toDTO(updatedSeat), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/seats/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Seat> deleteSeat(@PathVariable("id") Long id) {
		seatService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
