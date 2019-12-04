package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.service.SectorService;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private HallRepository hallRepository;

    @Override
    public Sector findOne(Long id) throws ResourceNotFoundException{
        return this.sectorRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Sector")
                    ); 
    }
    
    @Override
    public Sector create(Sector sector) throws Exception {
        if(sector.getId() != null){
            throw new ResourceExistsException("Sector");
        }
        Hall hall = hallRepository.findById(sector.getHall().getId()).orElseThrow(() -> new ResourceNotFoundException("Hall"));
        sector.setHall(hall);
        return this.sectorRepository.save(sector);
    }

    @Override
    public Sector update(Sector sector) throws Exception {
        Sector sectorToUpdate = this.findOne(sector.getId());
        sectorToUpdate = this.prepareSectorFields(sectorToUpdate, sector);
        return this.sectorRepository.save(sectorToUpdate);
    }

    @Override
    public void delete(Long id){
        this.sectorRepository.deleteById(id);
    }

    public Sector prepareSectorFields(Sector toUpdate, Sector newSector)
    {
        toUpdate.setName(newSector.getName());
        toUpdate.setSectorColumns(newSector.getSectorColumns());
        toUpdate.setSectorRows(newSector.getSectorRows());
        return toUpdate;
    }

}
