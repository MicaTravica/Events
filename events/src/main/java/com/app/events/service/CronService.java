package com.app.events.service;

public interface CronService {

    void notifyUsersForReservations();

    void markEventAsFinished() throws Exception; 
    
}