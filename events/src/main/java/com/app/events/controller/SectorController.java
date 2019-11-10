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

import com.app.events.dto.SectorDTO;
import com.app.events.exception.SectorDoesntExistException;
import com.app.events.mapper.SectorMapper;
import com.app.events.model.Sector;
import com.app.events.service.SectorService;

@RestController
public class SectorController extends BaseController{

	@Autowired
	private SectorMapper sectorMapper;

	@Autowired
	private SectorService sectorService;

	@GetMapping(value = "/api/sector/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> getSector(@PathVariable("id") Long id) throws SectorDoesntExistException{
		Sector sector = sectorService.findOne(id);
		return new ResponseEntity<>(sectorMapper.toDTO(sector), HttpStatus.OK);
		
	}

	@PostMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> createSector(@RequestBody SectorDTO sectorDTO) throws Exception {
			Sector savedSector = sectorService.create(sectorMapper.toSector(sectorDTO));
			sectorDTO = new SectorDTO(savedSector);
			return new ResponseEntity<>(sectorDTO, HttpStatus.CREATED);
	}

	@PutMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> updateSector(@RequestBody SectorDTO sectorDTO) throws Exception{
		Sector updatedSector = sectorService.update(sectorMapper.toSector(sectorDTO));
		if (updatedSector != null) {
			sectorDTO = new SectorDTO(updatedSector);
			return new ResponseEntity<>(sectorDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/api/sector/{id}")
	public ResponseEntity<Sector> deleteSector(@PathVariable("id") Long id) {
		sectorService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
