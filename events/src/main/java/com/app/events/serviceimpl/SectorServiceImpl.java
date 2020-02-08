package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.ResourceNullNumber;
import com.app.events.exception.TicketBoughtOrReservedException;
import com.app.events.model.Hall;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.repository.TicketRepository;
import com.app.events.service.SeatService;
import com.app.events.service.SectorCapacityService;
import com.app.events.service.SectorService;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private HallRepository hallRepository;
    
    @Autowired
    private SeatService seatService;
   

    @Autowired
    private TicketRepository ticketRepository;
   
    
    @Autowired
    private SectorCapacityService sectorCapacityService;



    @Override
    public Sector findOne(Long id) throws ResourceNotFoundException {
        return this.sectorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sector"));
    }

    @Override
    public Sector create(Sector sector) throws Exception {
        if (sector.getId() != null) {
            throw new ResourceExistsException("Sector");
        }
        if (sector.getSectorColumns()<0 || sector.getSectorRows()<0) {
            throw new ResourceNullNumber();
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
        checkTicketForSeat(sectorToUpdate);
        deleteSeatForOldSector(sectorToUpdate);
        saveSeat(sectorToUpdate);
        return this.sectorRepository.save(sectorToUpdate);
    }

	private void deleteSeatForOldSector(Sector sectorToUpdate) {
   	 if(sectorToUpdate.getSectorColumns()> 0 && sectorToUpdate.getSectorRows()> 0) {
         	Collection<Seat> seats = seatService.findSeatFromSector(sectorToUpdate.getId());
         	for(Seat s : seats) {
         		seatService.delete(s.getId());
         	}
    } 
	}
	
	private void checkTicketForSeat(Sector sectorToUpdate) throws ResourceExistsException, TicketBoughtOrReservedException {
	    Collection<Seat> seats = seatService.findSeatFromSector(sectorToUpdate.getId());
	   	for(Seat s : seats) {
	        Collection<Ticket> tickets = ticketRepository.findTicketsBySeatId(s.getId());
           	if(!tickets.isEmpty()) {
           		throw new TicketBoughtOrReservedException();
	         } 
	     }
	   	Collection<SectorCapacity> sectorCapacity = sectorCapacityService.findSectorCapacityBySectorId(sectorToUpdate.getId());
	   	for(SectorCapacity sc: sectorCapacity) {
    		Collection<Ticket> tickets = ticketRepository.findTicketsBySectorCapacityId(sc.getId());
    		if(!tickets.isEmpty()) {
	       		throw new TicketBoughtOrReservedException();
	           	} 
	     	}
			
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

}
