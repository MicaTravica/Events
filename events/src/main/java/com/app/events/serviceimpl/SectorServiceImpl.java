package com.app.events.serviceimpl;

import java.util.Optional;

import com.app.events.exception.HallDoesntExist;
import com.app.events.exception.SectorDoesntExistException;
import com.app.events.exception.SectorExistException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private HallRepository hallRepository;

    @Override
    public Sector findOne(Long id) throws SectorDoesntExistException{
        return this.sectorRepository.findById(id)
                    .orElseThrow(
                        ()-> new SectorDoesntExistException("sector doesn't exist")
                    ); 
    }

    @Override
    public Sector create(Sector sector) throws Exception{
        if(sector.getId() != null){
            throw new SectorExistException("Section with id: " + sector.getId() + "exists");
        }
        Optional<Hall> optHall = hallRepository.findById(sector.getHall().getId());
        if(optHall.isPresent()){
            sector.setHall(optHall.get());
            return this.sectorRepository.save(sector);
        }
        throw new HallDoesntExist("Hall doesn't exists");
    }

    @Override
    public Sector update(Sector sector) throws Exception{
        Sector sectorToUpdate = this.findOne(sector.getId());
        sectorToUpdate = this.prepareSectorFields(sectorToUpdate, sector);
        return this.sectorRepository.save(sectorToUpdate);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException{
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
