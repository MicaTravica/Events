package com.app.events.mapper;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Hall;
import com.app.events.model.Sector;

public class SectorMapper {

    public static SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector);
    }
    
    public static Sector toSector(SectorDTO sectorDTO){
		Sector sector = new Sector( sectorDTO.getId(), 
                        sectorDTO.getName(),
                        sectorDTO.getSectorRows(), 
                        sectorDTO.getSectorColumns()
                    );
        sector.setHall(sectorDTO.getHall() != null ? new Hall(sectorDTO.getHall().getId()) : null);
        return sector;
    }

}
