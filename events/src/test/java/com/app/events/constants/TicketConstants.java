package com.app.events.constants;


public class TicketConstants {
	

    /*
        info of persisted ticket from data2.sql script
    */
    public static final Long PERSISTED_TICKET_ID = 1l;
    public static final String PERSISTED_TICKET_BAR_CODE = "DFA";
    public static final int PERSISTED_TICKET_PRICE = 100;
    public static final int PERSISTED_TICKET_VERSION = 0;
    public static final Long PERSISTED_TICKET_EVENT_ID = 1l;
    public static final Long PERSISTED_TICKET_SEAT_ID = 1l;
    
    public static final Long INVALID_TICKET_ID = -1l;


    /*
    
    public static final Long PERSISTED_SECTOR_CAPACITY_SECTOR_ID = SectorConstants.PERSISTED_SECTOR_ID;
    public static final Long PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID = SectorConstants.INVALID_SECTOR_ID;

    /*
        constants for pesisting new sector in db
    */
    
    //public static final Long VALID_SECTOR_CAPACITY_ID_FOR_PERSISTANCE = 10l;

    /*
        maybe to make single invalid ID for all entities with value of -1?? 
    */
    /*
    public static final Long INVALID_SECTOR_CAPACITY_ID = -1l;
    public static final int PERSISTED_SECTOR_CAPACITY_FREE_INVALID = -100;
    public static final int PERSISTED_SECTOR_CAPACITY_CAPACITY_INVALID = -100;
	public static final String URI_PREFIX = "/api/sectorCapacity/";*/

}

