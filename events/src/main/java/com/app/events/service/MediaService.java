package com.app.events.service;

import java.util.Collection;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Media;

public interface MediaService {

	Media findOne(Long id) throws ResourceNotFoundException;

	Collection<Media> findAllForEvent(Long id);

	Media crate(Media media, Long eventId) throws ResourceNotFoundException;

	void delete(Long id) throws ResourceNotFoundException;

}
