package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.SectorCapacityMapper;
import com.app.events.model.SectorCapacity;
import com.app.events.service.SectorCapacityService;

@CrossOrigin(origins = "*")
@RestController
public class SectorCapacityController {

	@Autowired
	private SectorCapacityService sectorCapacityService;

	@GetMapping(value = "/api/sectorCapacity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> getSectorCapacity(@PathVariable("id") Long id) throws ResourceNotFoundException {
		SectorCapacity sectorCapacity = sectorCapacityService.findOne(id);
		return new ResponseEntity<SectorCapacityDTO>
			(
				SectorCapacityMapper.toDTO(sectorCapacity),
				HttpStatus.OK
			);
	}

	@PostMapping(value = "/api/sectorCapacity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> createSectorCapacity(@RequestBody SectorCapacityDTO sectorCapacityDTO) throws Exception {

		SectorCapacity savedSector = sectorCapacityService.create(SectorCapacityMapper.toSectorCapacity(sectorCapacityDTO));
		return new ResponseEntity<SectorCapacityDTO>
			(
				SectorCapacityMapper.toDTO(savedSector),
				HttpStatus.CREATED
			);
	}

	@PutMapping(value = "/api/sectorCapacity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorCapacityDTO> updateSectorCapacity(@RequestBody SectorCapacityDTO param) throws Exception {
		SectorCapacity updatedSector = sectorCapacityService.update(SectorCapacityMapper.toSectorCapacity(param));
		return new ResponseEntity<SectorCapacityDTO>
		(
			SectorCapacityMapper.toDTO(updatedSector),
			HttpStatus.OK
		);
	}

	@DeleteMapping(value = "/api/sectorCapacity/{id}")
	public ResponseEntity<SectorCapacityDTO> deleteSectorCapacity(@PathVariable("id") Long id) {
		sectorCapacityService.delete(id);
		return new ResponseEntity<SectorCapacityDTO>(HttpStatus.NO_CONTENT);
	}

}
