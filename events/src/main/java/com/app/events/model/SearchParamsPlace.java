package com.app.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamsPlace {

	private int numOfPage;
	private int sizeOfPage;
	private String sortBy;
	private boolean ascending;
	private String name;
	private String address;

}
