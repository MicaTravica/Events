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
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.SeatDTO;
import com.app.events.model.Seat;
import com.app.events.service.SeatService;

@RestController
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping(value = "/api/seats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> getSeat(@PathVariable("id") Long id) {
		SeatDTO seat = seatService.findOne(id);
		if (seat == null) {
			return new ResponseEntity<SeatDTO>(HttpStatus.NOT_FOUND);
		}
		System.out.println(seat.toString());
		return new ResponseEntity<SeatDTO>(seat, HttpStatus.OK);
	}

	@PostMapping(value = "/api/seats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> createSeat(Seat seat) throws Exception {

		try {
			SeatDTO savedSeat = seatService.create(seat);
			return new ResponseEntity<SeatDTO>(savedSeat, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<SeatDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/seats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> updateSeat(Seat seat) throws Exception {
		SeatDTO updatedSeat = seatService.update(seat);
		if (updatedSeat == null) {
			return new ResponseEntity<SeatDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<SeatDTO>(updatedSeat, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/seats/{id}")
	public ResponseEntity<Seat> deleteSeat(@PathVariable("id") Long id) {
		seatService.delete(id);
		return new ResponseEntity<Seat>(HttpStatus.NO_CONTENT);
	}

}
