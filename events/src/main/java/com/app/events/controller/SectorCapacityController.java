package com.app.events.controller;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.model.SectorCapacity;
import com.app.events.service.SectorCapacityService;

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

@RestController
public class SectorCapacityController {

	@Autowired
	private SectorCapacityService sectorCapacityService;

	@GetMapping(value = "/api/sectorCapacity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> getSectorCapacity(@PathVariable("id") Long id) {
		SectorCapacityDTO sector = sectorCapacityService.findOne(id);
		if (sector == null) {
			return new ResponseEntity<SectorCapacityDTO>(HttpStatus.NOT_FOUND);
		}
		System.out.println(sector.toString());
		return new ResponseEntity<SectorCapacityDTO>(sector, HttpStatus.OK);
	}

	@PostMapping(value = "/api/sectorCapacity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> createSectorCapacity(@RequestBody SectorCapacity sector) throws Exception {
		try {
			SectorCapacityDTO savedSector = sectorCapacityService.create(sector);
			return new ResponseEntity<SectorCapacityDTO>(savedSector, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<SectorCapacityDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/sectorCapacity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> updateSectorCapacity(@RequestBody SectorCapacity sector) throws Exception {
		SectorCapacityDTO updatedSector = sectorCapacityService.update(sector);
		if (updatedSector == null) {
			return new ResponseEntity<SectorCapacityDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<SectorCapacityDTO>(updatedSector, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/sectorCapacity/{id}")
	public ResponseEntity<SectorCapacityDTO> deleteSectorCapacity(@PathVariable("id") Long id) {
		sectorCapacityService.delete(id);
		return new ResponseEntity<SectorCapacityDTO>(HttpStatus.NO_CONTENT);
	}

}
