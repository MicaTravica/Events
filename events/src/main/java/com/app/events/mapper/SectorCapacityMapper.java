package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.model.SectorCapacity;

public class SectorCapacityMapper {

    public static SectorCapacityDTO toDTO(SectorCapacity sectorCapacity) {
        return new SectorCapacityDTO(
                    sectorCapacity.getId(),
                    SectorMapper.toDTO(sectorCapacity.getSector()),
                    sectorCapacity.getCapacity(),
                    sectorCapacity.getFree()
                );
    }
    
    public static SectorCapacity toSectorCapacity(SectorCapacityDTO sectorCapacityDTO) {
        return new SectorCapacity( sectorCapacityDTO.getId(),
                                    new HashSet<>(),
                                SectorMapper.toSector(sectorCapacityDTO.getSector()),
                                sectorCapacityDTO.getCapacity(),
                                sectorCapacityDTO.getFree());
    }

}
