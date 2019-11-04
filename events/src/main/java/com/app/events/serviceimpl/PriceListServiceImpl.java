package com.app.events.serviceimpl;

import com.app.events.dto.PriceListDTO;
import com.app.events.model.PriceList;
import com.app.events.repository.PriceListRepository;
import com.app.events.service.PriceListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    @Override
    public PriceListDTO findOne(Long id) {
        PriceList priceList = this.priceListRepository.findById(id).get();
        PriceListDTO priceListDTO = new PriceListDTO(priceList);

        return priceListDTO;
    }

    @Override
    public PriceListDTO create(PriceList priceList) {
        if(priceList.getId() != null){
            throw new RuntimeException("PriceList already exists and has ID."); // custom exception here!
        }
        PriceList savedPriceList = this.priceListRepository.save(priceList);
        PriceListDTO priceListCapacityDTO = new PriceListDTO(savedPriceList);
        
        return priceListCapacityDTO;
    }

    @Override
    public PriceListDTO update(PriceList priceList) {
        PriceList priceListToUpdate = this.priceListRepository.findById(priceList.getId()).get();
	    if (priceListToUpdate == null) { 
	    	throw new RuntimeException("Not found."); // custom exception here!
        }	    

        priceListToUpdate.setPrice(priceList.getPrice());
        priceListToUpdate.setEvent(priceList.getEvent());
        priceListToUpdate.setSector(priceList.getSector());
        
        PriceList updatedPriceList = this.priceListRepository.save(priceListToUpdate);
	    PriceListDTO updatedPriceListDTO = new PriceListDTO(updatedPriceList);
	        
	    return updatedPriceListDTO;
    }

    @Override
    public void delete(Long id) {
        this.priceListRepository.deleteById(id);
    }

}
