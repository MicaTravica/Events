package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.model.SectorCapacity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorCapacityMapper {

    @Autowired
    private SectorMapper sectorMapper;

    public SectorCapacityDTO toDTO(SectorCapacity sectorCapacity) {
        return new SectorCapacityDTO(sectorCapacity);
    }
    
    public SectorCapacity toSectorCapacity(SectorCapacityDTO sectorCapacityDTO) {
        return new SectorCapacity( sectorCapacityDTO.getId(),
                                    new HashSet<>(),
                                sectorMapper.toSector(sectorCapacityDTO.getSector()),
                                sectorCapacityDTO.getCapacity(),
                                sectorCapacityDTO.getFree());
    }

}
