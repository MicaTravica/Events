package com.app.events.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.MediaDTO;
import com.app.events.model.Media;
import com.app.events.model.Sector;
import com.app.events.service.MediaService;

@RestController
@RequestMapping("api")
public class MediaController extends BaseController {

	@Autowired
	MediaService mediaService;
	
	@GetMapping(value = "/media/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MediaDTO> getMedia(@PathVariable("id") Long id) throws Exception {
		return new ResponseEntity<>(new MediaDTO(mediaService.findOne(id)),HttpStatus.OK);
	}
	
	@GetMapping(value = "/media/event/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MediaDTO>> getMediaForEvent(@PathVariable("id") Long id) {
		return new ResponseEntity<>(mediaService.findAllForEvent(id).stream()
									.map(m -> new MediaDTO(m))
									.collect(Collectors.toList()),HttpStatus.OK);
	}

	@PostMapping(value = "/media", 
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MediaDTO> createMedia(@RequestBody MediaDTO mediaDto) throws Exception {
		return new ResponseEntity<>(new MediaDTO(mediaService.crate(new Media(mediaDto), mediaDto.getEventId())), HttpStatus.OK);
	}


	@DeleteMapping(value = "/media/{id}}")
	public ResponseEntity<Sector> deleteMedia(@PathVariable("id") Long id) throws Exception {
		mediaService.delete(id);
		return new ResponseEntity<Sector>(HttpStatus.NO_CONTENT);
	}

}
