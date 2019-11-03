package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

}
