package com.app.events.serviceimpl;

import com.app.events.exception.SectorCapacityDoesntExistException;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.service.SectorCapacityService;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorCapacityServiceImpl implements SectorCapacityService {

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Autowired
    private SectorService sectorService;

    @Override
    public SectorCapacity findOne(Long id) throws SectorCapacityDoesntExistException{
        return this.sectorCapacityRepository.findById(id)
                    .orElseThrow(
                        ()-> new SectorCapacityDoesntExistException("sector capacity doesn't exist")
                    ); 
    }

    @Override
    public SectorCapacity create(SectorCapacity sectorCapacity) throws Exception{
        if(sectorCapacity.getId() != null){
            throw new RuntimeException("SectorCapacity already exists and has ID."); // custom exception here!
        }
        Sector sector = sectorService.findOne(sectorCapacity.getSector().getId());
        sectorCapacity.setSector(sector);
        sectorCapacity.setFree(sectorCapacity.getCapacity());
        return this.sectorCapacityRepository.save(sectorCapacity);
    }

    @Override
    public SectorCapacity update(SectorCapacity sectorCapacity) throws Exception {
        SectorCapacity sectorCapacityToUpdate = this.findOne(sectorCapacity.getId());
        sectorCapacityToUpdate.setFree(sectorCapacity.getFree());
        sectorCapacityToUpdate.setCapacity(sectorCapacity.getCapacity());
	    return this.sectorCapacityRepository.save(sectorCapacityToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.sectorCapacityRepository.deleteById(id);
    }
}
