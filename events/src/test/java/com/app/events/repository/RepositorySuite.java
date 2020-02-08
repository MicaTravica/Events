package com.app.events.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventRepositoryTest.class,
        HallRepositoryTest.class,
        PlaceRepositoryTest.class,
        SeatRepositoryTest.class,
        SectorRepositoryTest.class,
        SectorCapacityRepositoryTest.class
})
public class RepositorySuite {
}
