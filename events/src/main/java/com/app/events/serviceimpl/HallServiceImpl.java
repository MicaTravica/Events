package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.dto.HallDTO;
import com.app.events.model.Hall;
import com.app.events.repository.HallRepository;
import com.app.events.service.HallService;

@Service
public class HallServiceImpl implements HallService {
	
	@Autowired
    private HallRepository hallRepository;

    @Override
    public HallDTO findOne(Long id) {
        Hall hall = this.hallRepository.findById(id).get();
        HallDTO hallDTO = new HallDTO(hall);

        return hallDTO;
    }

    @Override
    public HallDTO create(Hall hall) {
        if(hall.getId() != null){
            throw new RuntimeException("Hall already exists and has ID.");
        }
        Hall savedHall = this.hallRepository.save(hall);
        HallDTO hallSavedDTO = new HallDTO(savedHall);
        
        return hallSavedDTO;
       
    }

    @Override
    public HallDTO update(Hall hall) {
        Hall hallToUpdate = this.hallRepository.findById(hall.getId()).get();
	    if (hallToUpdate == null) { 
	    	throw new RuntimeException("Not found hall with this ID."); 
        }	    

	    hallToUpdate.setName(hall.getName());
	    
	    Hall updatedHall = this.hallRepository.save(hallToUpdate);
	    HallDTO updatedHallDTO = new HallDTO(updatedHall);
	    
	    return updatedHallDTO;
	    

    }

    @Override
    public void delete(Long id) {
        this.hallRepository.deleteById(id);
    }



}





