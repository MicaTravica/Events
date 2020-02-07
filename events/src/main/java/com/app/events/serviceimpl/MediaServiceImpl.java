package com.app.events.serviceimpl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Media;
import com.app.events.repository.MediaRepository;
import com.app.events.service.EventService;
import com.app.events.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService {

	@Autowired
	MediaRepository mediaRepository;
	
	@Autowired
	EventService eventService;

	@Override
	public Media findOne(Long id) throws ResourceNotFoundException {
		return mediaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Media"));
	}

	@Override
	public Collection<Media> findAllForEvent(Long id) {
		return mediaRepository.findAllByEventId(id);
	}

	@Override
	public Media create(Media media, Long eventId) throws Exception {
		Event event = eventService.findOne(eventId);
		System.out.println(event.getId());
		media.setEvent(event);
		media.setId(null);
		return mediaRepository.save(media);
	}	

	@Override
	public Collection<Media> createMedias(Collection<Media> mediaList, Long eventId) throws ResourceNotFoundException{
		Collection<Media> createdMediaList = new HashSet<>();
		Event event = eventService.findOne(eventId);
		for(Media m : mediaList) {
			m.setEvent(event);
			m.setId(null);
			createdMediaList.add(mediaRepository.save(m));
		}
		return createdMediaList;
	}
	@Override
	public void delete(Long id) throws ResourceNotFoundException  {
		Media media = mediaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Media"));
		media.setEvent(null);
		mediaRepository.save(media);
		mediaRepository.deleteById(id);
	}

	
	
}
