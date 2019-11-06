package com.app.events.serviceimpl;

import java.util.Optional;

import com.app.events.exception.HallDoesntExist;
import com.app.events.exception.SectorDoesntExistException;
import com.app.events.exception.SectorExistException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.PriceListRepository;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Autowired
    private HallRepository hallRepository;

    @Override
    public Sector findOne(Long id) {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(id);
        return sectorOpt.isPresent() == true ? sectorOpt.get() : null;
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
        Optional<Sector> sectorOpt = this.sectorRepository.findById(sector.getId());
        if( !sectorOpt.isPresent())
        {
            throw new SectorDoesntExistException("Not found sector."); 
        }
        Sector sectorToUpdate = sectorOpt.get();
        Optional<Hall> hallOpt = this.hallRepository.findById(sector.getHall().getId());
        if( !hallOpt.isPresent())
        {
            throw new HallDoesntExist("Not found hall."); // custom exception here!
        }
        sectorToUpdate.setHall(hallOpt.get());
        sectorToUpdate.setName(sector.getName());
        sectorToUpdate.setSectorColumns(sector.getSectorColumns());
        sectorToUpdate.setSectorRows(sector.getSectorRows());
        return this.sectorRepository.save(sectorToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.sectorRepository.deleteById(id);
    }

}
