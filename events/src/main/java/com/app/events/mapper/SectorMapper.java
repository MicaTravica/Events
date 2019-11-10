package com.app.events.mapper;

import com.app.events.dto.SectorDTO;
import com.app.events.exception.SectorDoesntExistException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorMapper {

    @Autowired
    private SectorService sectorService;

    public SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector);
    }
    
    public Sector toSector(SectorDTO sectorDTO){
		Sector sector = new Sector( sectorDTO.getId(), 
                        sectorDTO.getName(),
                        sectorDTO.getSectorRows(), 
                        sectorDTO.getSectorColumns()
                    );
        sector.setHall(new Hall(sectorDTO.getHall().getId()));
        return sector;
    }

    public Sector findSectorFromDTO(SectorDTO sectorDTO) throws SectorDoesntExistException {
        return sectorService.findOne(sectorDTO.getId());
    }

}
