/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.service;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.dao.dto.Sighting;
import com.sg.herosightings.dao.dto.SuperPower;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author apprentice
 */
public interface HeroSightingService {
    
    List<Hero> getAllHeroes();
    List<SuperPower> getAllPowers();
    List<Organization> getAllOrgs();
    List<Location> getAllLocals();
    List<Sighting> getAllSightings();
    Organization getOrg(int id);
    SuperPower getPower(int id);
    Location getLocation(int id);
    Sighting createSighting(Sighting sight);
    Hero createHero(Hero hero);
    Boolean validateIdsNotNull (String[] array);
    SuperPower createPower(SuperPower power);
    Hero getHero(int heroId);
    Organization createOrg(Organization org);
    Location createLocation(Location local);
    Sighting deleteSighting(int sightId);
    Hero deleteHero(int heroId);
    SuperPower deletePower(int powerId);
    Location deleteLocal(int localId);
    Organization deleteOrg(int orgId);
    SuperPower updatePower(SuperPower power);
    Location updateLocal(Location local);
    Organization updateOrg(Organization org);
    Hero updateHero(Hero hero);
    List<Sighting> getLastTenSightings();
    List<Hero> getHeroesByDate(LocalDate date);
    List<Hero> getHeroesByPower(SuperPower power);
    List<Hero> getHeroesByLocation(Location local);
    List<Hero> getHeroesByOrganization(Organization org);
    Map<SuperPower, Integer> getPowersByHero(Hero hero);
    Set<SuperPower> getPowersByLevel(int level);
    List<Organization> getOrgsByHeroes(Hero hero);
    Organization getOrgByLocation(Location local);
    List<Location> getLocalsByHero(Hero hero);
    List<Location> getLocalsByOrg(Organization org);
    List<Location> getLocalsByDate(LocalDate date);
    List<Sighting> getSightingsByHero(Hero hero);
    List<Sighting> getSightingsByDate(LocalDate date);
    List<Sighting> getSightingsByLocal(Location local);
}
