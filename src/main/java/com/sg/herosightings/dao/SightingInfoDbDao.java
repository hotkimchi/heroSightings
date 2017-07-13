/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface SightingInfoDbDao {
    
    Sighting insertSighting(Sighting sight);
    Sighting deleteSighting(int sightId);
    Sighting updateSighting(Sighting sight);
    Sighting getSighting(int sightId);
    List<Sighting> getAllSightings();
    List<Sighting> getSightingsByDate(LocalDate date);
    List<Sighting> getSightingsByLocation(Location local);
}
