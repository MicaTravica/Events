package com.app.events.dto;

import com.app.events.model.Media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {

	private Long id;
	private String path;
	private Long eventId;
	
	public MediaDTO(Media media) {
		this.id = media.getId();
		this.path = media.getPath();
	}
}
