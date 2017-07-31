/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.SuperPower;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author apprentice
 */
public interface PowerInfoDbDao {
    
    SuperPower insertSuperPower(SuperPower power);
    SuperPower deleteSuperPower(int powerId);
    SuperPower updateSuperPower(SuperPower power);
    SuperPower getSuperPower(int powerId);
    List<SuperPower> getAllSuperPowers();
    Map<SuperPower, Integer> getPowersByHero(Hero hero);
    Set<SuperPower> getPowersGreaterThan (int powerLevel);
    
}
