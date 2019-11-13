package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Hall;
import com.app.events.model.Sector;

public class SectorMapper {

    public static SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector.getId(), sector.getName(), 
                            sector.getSectorRows(), sector.getSectorRows(),
                            sector.getHall().getId()  
                        );
    }
    
    public static Sector toSector(SectorDTO sectorDTO){
		return new Sector( sectorDTO.getId(), 
                        sectorDTO.getName(),
                        sectorDTO.getSectorRows(), 
                        sectorDTO.getSectorColumns(),
                        new Hall(sectorDTO.getHallId()),
                        new HashSet<>(),
                        new HashSet<>(),
                        new HashSet<>()
                    );
    }

}
