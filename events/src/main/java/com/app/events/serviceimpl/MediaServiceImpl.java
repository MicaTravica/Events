package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.EventNotFoundException;
import com.app.events.exception.MediaNotFoundException;
import com.app.events.model.Media;
import com.app.events.repository.EventRepository;
import com.app.events.repository.MediaRepository;
import com.app.events.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService {

	@Autowired
	MediaRepository mediaRepository;
	
	@Autowired
	EventRepository eventRepository;

	@Override
	public Media findOne(Long id) throws MediaNotFoundException {
		return mediaRepository.findById(id).orElseThrow(() -> new MediaNotFoundException(id));
	}

	@Override
	public Collection<Media> findAllForEvent(Long id) {
		return mediaRepository.findAllByEventId(id);
	}

	@Override
	public Media crate(Media media, Long eventId) throws EventNotFoundException {
		media.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId)));
		media.setId(null);
		return mediaRepository.save(media);
	}	

	@Override
	public void delete(Long id) throws MediaNotFoundException  {
		Media media = mediaRepository.findById(id).orElseThrow(() -> new MediaNotFoundException(id));
		media.setEvent(null);
		mediaRepository.save(media);
		mediaRepository.deleteById(id);;
	}

	
	
}
