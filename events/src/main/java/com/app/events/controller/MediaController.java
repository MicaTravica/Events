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
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.MediaMapper;
import com.app.events.service.MediaService;

@RestController
@RequestMapping("api")
public class MediaController extends BaseController {

	@Autowired
	private MediaService mediaService;
	
	@GetMapping(value = "/media/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MediaDTO> getMedia(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(MediaMapper.toDTO(mediaService.findOne(id)),HttpStatus.OK);
	}
	
	@GetMapping(value = "/media/event/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MediaDTO>> getMediaForEvent(@PathVariable("id") Long id) {
		return new ResponseEntity<>(mediaService.findAllForEvent(id).stream()
									.map(media -> MediaMapper.toDTO(media))
									.collect(Collectors.toList()),HttpStatus.OK);
	}

	@PostMapping(value = "/media", 
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MediaDTO> createMedia(@RequestBody MediaDTO mediaDto) throws ResourceNotFoundException {
		return new ResponseEntity<>(MediaMapper.toDTO(mediaService.crate(MediaMapper.toMedia(mediaDto), mediaDto.getEventId())), HttpStatus.OK);
	}


	@DeleteMapping(value = "/media/{id}}")
	public ResponseEntity<String> deleteMedia(@PathVariable("id") Long id) throws ResourceNotFoundException {
		mediaService.delete(id);
		return new ResponseEntity<>("Media is deleted", HttpStatus.NO_CONTENT);
	}

}
