/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface LocationInfoDbDao {
    
    Location insertLocal(Location local);
    Location deleteLocal(int localId);
    Location updateLocal(Location local);
    Location getLocal(int localId);
    List<Location> getAllLocals();
    List<Location> getAllLocalsBySightDate(LocalDate date);
    List<Location> getAllLocalsByOrg(Organization org);
    Location getLocalBySightId(int sightId);
    List<Location> getAllLocalsByHero(Hero hero);
}
