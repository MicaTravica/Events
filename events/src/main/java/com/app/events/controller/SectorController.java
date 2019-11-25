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

import com.app.events.dto.SectorDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.SectorMapper;
import com.app.events.model.Sector;
import com.app.events.service.SectorService;

@RestController
public class SectorController extends BaseController{

	@Autowired
	private SectorService sectorService;

	@GetMapping(value = "/api/sector/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> getSector(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Sector sector = sectorService.findOne(id);
		return new ResponseEntity<>(SectorMapper.toDTO(sector), HttpStatus.OK);
		
	}

	@PostMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SectorDTO> createSector(@RequestBody SectorDTO sectorDTO) throws Exception {
			Sector savedSector = sectorService.create(SectorMapper.toSector(sectorDTO));
			return new ResponseEntity<>(SectorMapper.toDTO(savedSector), HttpStatus.CREATED);
	}

	@PutMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SectorDTO> updateSector(@RequestBody SectorDTO sectorDTO) throws Exception {
		Sector updatedSector = sectorService.update(SectorMapper.toSector(sectorDTO));
		return new ResponseEntity<>(SectorMapper.toDTO(updatedSector), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/sector/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Sector> deleteSector(@PathVariable("id") Long id) {
		sectorService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
