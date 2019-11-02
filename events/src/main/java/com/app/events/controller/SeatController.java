package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.SeatDTO;
import com.app.events.service.SeatService;

@RestController
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping(value="/api/seats/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatDTO> getSeat(@PathVariable("id") Long id) {
		SeatDTO seat = seatService.findOne(id);
		if (seat == null) {
			return new ResponseEntity<SeatDTO>(HttpStatus.NOT_FOUND);
		}
		System.out.println(seat.toString());
		return new ResponseEntity<SeatDTO>(seat, HttpStatus.OK);
	}

}
