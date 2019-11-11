package com.app.events.mapper;

import org.springframework.stereotype.Component;

import com.app.events.dto.MediaDTO;
import com.app.events.model.Media;

@Component
public class MediaMapper {

	public MediaDTO toDTO(Media media) {
		return new MediaDTO(media.getId(), media.getPath(), media.getEvent().getId());
	}
	
	public Media toMedia(MediaDTO mediaDto) {
		return new Media(mediaDto.getId(), mediaDto.getPath(), null);
	}
}
