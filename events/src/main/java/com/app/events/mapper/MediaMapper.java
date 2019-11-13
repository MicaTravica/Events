package com.app.events.mapper;

import com.app.events.dto.MediaDTO;
import com.app.events.model.Media;

public class MediaMapper {

	public static MediaDTO toDTO(Media media) {
		return new MediaDTO(media.getId(), media.getPath(), media.getEvent().getId());
	}
	
	public static Media toMedia(MediaDTO mediaDto) {
		return new Media(mediaDto.getId(), mediaDto.getPath(), null);
	}
}
