package com.app.events.repository;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.PriceListConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.model.PriceList;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class PriceListRepositoryTest {

	@Autowired
	private PriceListRepository priceListRepository;
	
	@Test
	public void findBySectorId_Test_Success() {
		
		Set<PriceList> results = priceListRepository.findBySectorId(SectorConstants.PERSISTED_SECTOR_ID);
		assertEquals(1 ,results.size());
		
		for(PriceList pl : results) {
			assertEquals(PriceListConstants.PERSISTED_SECTOR_ID, pl.getSector().getId());
		}
				
	}
	
	@Test
	public void finfBySectorId_Test_Fail() {
		
		Set<PriceList> results = priceListRepository.findBySectorId(SectorConstants.INVALID_SECTOR_ID);
		assertEquals(0, results.size());
	}
	

}
