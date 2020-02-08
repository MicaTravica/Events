package com.app.events.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventControllerIntegrationTest.class,
        HallControllerIntegrationTest.class,
        MediaControllerIntegrationTest.class,
        PlaceControllerIntegrationTest.class,
        SeatControllerIntegrationTest.class,
        SectorCapacityControllerIntegrationTest.class,
        SectorControllerIntegrationTest.class
    })
public class ControllerSuite {
}
