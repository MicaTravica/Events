package com.app.events.serviceimpl;

import java.util.Optional;

import com.app.events.model.Sector;
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

    @Override
    public Sector findOne(Long id) {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(id);
        return sectorOpt.isPresent() == true ? sectorOpt.get() : null;
    }

    @Override
    public Sector create(Sector sector) {
        if(sector.getId() != null){
            throw new RuntimeException("Sector already exists and has ID."); // custom exception here!
        }
        // da li cu za Hall trebati da pozivam metodu njegovog servisa???
        return this.sectorRepository.save(sector);
    }

    @Override
    public Sector update(Sector sector) {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(sector.getId());
        if( !sectorOpt.isPresent())
        {
            throw new RuntimeException("Not found."); // custom exception here!
        }
        Sector sectorToUpdate = sectorOpt.get();
	    sectorToUpdate.setHall(sector.getHall());
	    sectorToUpdate.setName(sector.getName());
        sectorToUpdate.setPriceLists(sector.getPriceLists());
        sectorToUpdate.setSeats(sector.getSeats());
        sectorToUpdate.setSectorCapacities(sector.getSectorCapacities());
        sectorToUpdate.setSectorColumns(sector.getSectorColumns());
        sectorToUpdate.setSectorRows(sector.getSectorRows());
	    return this.sectorRepository.save(sectorToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.sectorRepository.deleteById(id);
    }

}
