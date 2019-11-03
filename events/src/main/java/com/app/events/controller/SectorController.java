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

import com.app.events.dto.SectorDTO;
import com.app.events.model.Sector;
import com.app.events.service.SectorService;

@CrossOrigin(origins = "*")
@RestController
public class SectorController {

	@Autowired
	private SectorService sectorService;

	@GetMapping(value = "/api/sector/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> getSector(@PathVariable("id") Long id) {
		SectorDTO sector = sectorService.findOne(id);
		if (sector == null) {
			return new ResponseEntity<SectorDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SectorDTO>(sector, HttpStatus.OK);
	}

	@PostMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> createSector(@RequestBody Sector sector) throws Exception {
		try {
			SectorDTO savedSector = sectorService.create(sector);
			return new ResponseEntity<SectorDTO>(savedSector, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<SectorDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> updateSector(@RequestBody Sector sector) throws Exception {
		SectorDTO updatedSector = sectorService.update(sector);
		if (updatedSector == null) {
			return new ResponseEntity<SectorDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<SectorDTO>(updatedSector, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/sector/{id}")
	public ResponseEntity<Sector> deleteSector(@PathVariable("id") Long id) {
		sectorService.delete(id);
		return new ResponseEntity<Sector>(HttpStatus.NO_CONTENT);
	}

}
