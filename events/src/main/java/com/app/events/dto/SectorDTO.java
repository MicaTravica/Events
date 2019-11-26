package com.app.events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {

	private Long id;
	private String name;

	private int sectorRows;
	private int sectorColumns;
	private int sectorCapacity;
	private Long hallId;



}
