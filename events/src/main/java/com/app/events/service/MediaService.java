package com.app.events.service;

import java.util.Collection;

import com.app.events.model.Media;

public interface MediaService {

	Media findOne(Long id) throws Exception;

	Collection<Media> findAllForEvent(Long id);

	Media crate(Media media, Long eventId) throws Exception;

	void delete(Long id) throws Exception;

}
