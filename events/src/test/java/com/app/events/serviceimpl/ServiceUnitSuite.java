package com.app.events.serviceimpl;

import com.app.events.serviceimpl.event.EventServiceImplUnitTest;
import com.app.events.serviceimpl.hall.HallServiceImplUnitTest;
import com.app.events.serviceimpl.media.MediaServiceImplUnitTest;
import com.app.events.serviceimpl.place.PlaceServiceImplUnitTest;
import com.app.events.serviceimpl.seat.SeatServiceImplUnitTest;
import com.app.events.serviceimpl.sector.SectorServiceImplUnitTest;
import com.app.events.serviceimpl.sectorCapacity.SectorCapacityServiceImplUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventServiceImplUnitTest.class,
        HallServiceImplUnitTest.class,
        MediaServiceImplUnitTest.class,
        PlaceServiceImplUnitTest.class,
        SeatServiceImplUnitTest.class,
        SectorServiceImplUnitTest.class,
        SectorCapacityServiceImplUnitTest.class
})
public class ServiceUnitSuite {
}
