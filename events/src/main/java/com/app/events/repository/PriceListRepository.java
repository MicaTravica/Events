package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

import com.app.events.model.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query("SELECT priceList from PriceList priceList WHERE priceList.sector.id = ?1")
    Set<PriceList> findBySectorId(Long sectorId);
    

}
