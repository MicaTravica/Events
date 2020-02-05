package com.app.events.serviceimpl;

import java.util.Collection;

import java.util.Collection;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.service.SeatService;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private HallRepository hallRepository;
    
    @Autowired
    private SeatService seatService;


    @Override
    public Sector findOne(Long id) throws ResourceNotFoundException {
        return this.sectorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sector"));
    }

    @Override
    public Sector create(Sector sector) throws Exception {
    	Seat seat = new Seat();
		seat.setSector(sector);

        if (sector.getId() != null) {
            throw new ResourceExistsException("Sector");
        }
        
        Hall hall = hallRepository.findById(sector.getHall().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hall"));
        sector.setHall(hall);
        Sector savedSector=  this.sectorRepository.save(sector);
        saveSeat(savedSector);
        return savedSector;
        
    }
    
    public void saveSeat(Sector sector) throws Exception {
    	 if(sector.getSectorColumns()> 0 && sector.getSectorRows()> 0) {
         	for(int i=1; i<sector.getSectorRows()+1; i++) {
         		for(int j=1; j < sector.getSectorColumns()+1; j++) {
         	    	Seat seat = new Seat();
         	    	seat.setSector(sector);
         	    	seat.setSeatColumn(j);
         			seat.setSeatRow(i);
         			seatService.create(seat);
         		}
         	}
         }
    }

    @Override
    public Sector update(Sector sector) throws Exception {
        Sector sectorToUpdate = this.findOne(sector.getId());
        sectorToUpdate = this.prepareSectorFields(sectorToUpdate, sector);
        saveSeat(sectorToUpdate);
        return this.sectorRepository.save(sectorToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.sectorRepository.deleteById(id);
    }

    public Sector prepareSectorFields(Sector toUpdate, Sector newSector) {
        toUpdate.setName(newSector.getName());
        toUpdate.setSectorColumns(newSector.getSectorColumns());
        toUpdate.setSectorRows(newSector.getSectorRows());
        return toUpdate;
    }

    @Override
	public Collection<Sector> getSectorsByHallId(Long id) {
		return sectorRepository.findAllByHallId(id);
	}
    public Collection<Sector> findAllByHallAndEvent(Long hallId, Long eventId) {
        return this.sectorRepository.findAllByHallIdAndEventId(hallId, eventId);
    }

	@Override
	public Collection<Sector> getSectorsByHallId(Long id) {
		return sectorRepository.findAllByHallId(id);
	}

}
