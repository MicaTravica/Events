package com.app.events.constants;

public class PriceListConstants {

	 /*
    	info of persisted priceList from data2.sql script
	  */
	public static final Long PERSISTED_PL_ID = 1l;
	public static final double PERSISTED_PRICE = 100;
	public static final Long PERSISTED_EVENT_ID = 1L;
	public static final Long PERSISTED_SECTOR_ID = 1l;
	
	public static final Long PERSISTED_PL_ID2 = 2l;
	public static final Long PERSISTED_SECTOR_ID2 = 2l;
	
	public static final Long NEW_EVENT_ID = 4l;
	public static final Long NEW_SECTOR_ID = 1l;
	public static final double NEW_PRICE = 1500.0;

	
	public static final Long INVALID_PL_ID = -1l;	

	public static final String URI_PREFIX = "/api/priceList/";
}
