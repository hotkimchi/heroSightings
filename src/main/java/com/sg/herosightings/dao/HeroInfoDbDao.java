/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.dao.dto.SuperPower;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface HeroInfoDbDao {
    
    Hero insertHero(Hero hero);
    Hero deleteHero(int heroId);
    Hero updateHero(Hero hero);
    Hero getHero(int heroId);
    List<Hero> getAllHeroes();
    Hero removePowerFromHero(Hero hero, SuperPower power);
    List<Hero> getHeroesByOrg(Organization org);
    List<Hero> getHeroesByPower(SuperPower power);
    List<Hero> getHeroesByLocation(Location local);
    List<Hero> getHeroesBySightingDate(LocalDate date);
    
}
