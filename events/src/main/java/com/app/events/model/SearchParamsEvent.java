package com.app.events.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamsEvent {

	private int numOfPage;
	private int sizeOfPage;
	private String sortBy;
	private boolean ascending;
	private String name;
	private Date fromDate;
	private Date toDate;
	private EventState eventState;
	private EventType eventType;
	private Long placeId;
}
