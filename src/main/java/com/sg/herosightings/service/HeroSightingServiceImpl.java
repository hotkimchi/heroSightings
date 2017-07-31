/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.service;

import com.sg.herosightings.dao.HeroInfoDbDao;
import com.sg.herosightings.dao.LocationInfoDbDao;
import com.sg.herosightings.dao.OrgInfoDbDao;
import com.sg.herosightings.dao.PowerInfoDbDao;
import com.sg.herosightings.dao.SightingInfoDbDao;
import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.dao.dto.Sighting;
import com.sg.herosightings.dao.dto.SuperPower;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

/**
 *
 * @author apprentice
 */
public class HeroSightingServiceImpl implements HeroSightingService {
    
    private HeroInfoDbDao heroDao;
    private OrgInfoDbDao orgDao;
    private LocationInfoDbDao locationDao;
    private PowerInfoDbDao powerDao;
    private SightingInfoDbDao sightingDao;
    
    @Inject
    public HeroSightingServiceImpl(HeroInfoDbDao heroDao, OrgInfoDbDao orgDao, LocationInfoDbDao locationDao, PowerInfoDbDao powerDao, SightingInfoDbDao sightingDao) {
        this.heroDao = heroDao;
        this.orgDao = orgDao;
        this.powerDao = powerDao;
        this.locationDao = locationDao;
        this.sightingDao = sightingDao;
    }

    @Override
    public List<Hero> getAllHeroes() {
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            List<Organization> orgs = orgDao.getOrgsByHero(hero);
            Map<SuperPower, Integer> powers = powerDao.getPowersByHero(hero);
            List<Sighting> sights = sightingDao.getSightingsByHero(hero);
            for (Sighting sight : sights) {
                sight.setLocation(locationDao.getLocalBySightId(sight.getSightingId()));
            }
            hero.setOrgs(orgs);
            hero.setPowers(powers);
            hero.setSightings(sights);
        }
        return heroes;
    }

    @Override
    public List<SuperPower> getAllPowers() {
        List<SuperPower> powers = powerDao.getAllSuperPowers();
        for (SuperPower power : powers) {
            List<Hero> heroes = heroDao.getHeroesByPower(power);
            power.setHeroes(heroes);
        }
        return powers;
    }

    @Override
    public List<Organization> getAllOrgs() {
        List<Organization> orgs = orgDao.getAllOrgs();
        for (Organization org : orgs) {
            org.setHeroes(heroDao.getHeroesByOrg(org));
            org.setLocals(locationDao.getAllLocalsByOrg(org));
        }
        return orgs;
    }

    @Override
    public List<Location> getAllLocals() {
        List<Location> locals = locationDao.getAllLocals();
        for (Location local : locals) {
            Organization org = orgDao.getOrgByLocation(local);
            local.setOrg(org);
        }
        return locals;
    }

    @Override
    public Organization getOrg(int id) {
        Organization org = orgDao.getOrg(id);
        List<Hero> heroes = heroDao.getHeroesByOrg(org);
        List<Location> locals = locationDao.getAllLocalsByOrg(org);
        org.setHeroes(heroes);
        org.setLocals(locals);
        return org;
    }

    @Override
    public SuperPower getPower(int id) {
        return powerDao.getSuperPower(id);
    }

    @Override
    public Location getLocation(int id) {
        Location local = locationDao.getLocal(id);
        Organization org = orgDao.getOrgByLocation(local);
        local.setOrg(org);
        return local;
    }

    @Override
    public Sighting createSighting(Sighting sight) {
        List<Sighting>  sights = getAllSightings();
        for (Sighting currentSight : sights){
            if (currentSight.getDate().equals(sight.getDate()) && 
                    currentSight.getLocation().equals(sight.getLocation())){
                return null;
            }
        }
        return sightingDao.insertSighting(sight);
    }

    @Override
    public Hero createHero(Hero hero) {
        return heroDao.insertHero(hero);
    }

    @Override
    public Boolean validateIdsNotNull(String[] array) {
        if (array == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public SuperPower createPower(SuperPower power) {
        return powerDao.insertSuperPower(power);
    }

    @Override
    public Hero getHero(int heroId) {
        Hero hero = heroDao.getHero(heroId);
        List<Organization> orgs = orgDao.getOrgsByHero(hero);
        List<Sighting> sights = sightingDao.getSightingsByHero(hero);
        for (Sighting sight : sights) {
            Location local = locationDao.getLocalBySightId(sight.getSightingId());
            sight.setLocation(local);
        }
        Map<SuperPower, Integer> powers = powerDao.getPowersByHero(hero);
        hero.setOrgs(orgs);
        hero.setPowers(powers);
        hero.setSightings(sights);
        return hero;
    }

    @Override
    public Organization createOrg(Organization org) {
        return orgDao.insertOrg(org);
    }

    @Override
    public Location createLocation(Location local) {
        return locationDao.insertLocal(local);
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sights = sightingDao.getAllSightings();
        for (Sighting sight : sights) {
            List<Hero> heroes = heroDao.getHeroesBySighting(sight);
            sight.setHeroes(heroes);
            sight.setLocation(locationDao.getLocalBySightId(sight.getSightingId()));
        }
        return sights;
    }

    @Override
    public Sighting deleteSighting(int sightId) {
        return sightingDao.deleteSighting(sightId);
    }

    @Override
    public Hero deleteHero(int heroId) {
        return heroDao.deleteHero(heroId);
    }

    @Override
    public SuperPower deletePower(int powerId) {
        return powerDao.deleteSuperPower(powerId);
    }

    @Override
    public Location deleteLocal(int localId) {
        List<Sighting> sights = sightingDao.getSightingsByLocation(locationDao.getLocal(localId));
        for (Sighting sight : sights) {
            sightingDao.deleteSighting(sight.getSightingId());
        }
        return locationDao.deleteLocal(localId);
    }

    @Override
    public Organization deleteOrg(int orgId) {
        return orgDao.deleteOrg(orgId);
    }

    @Override
    public SuperPower updatePower(SuperPower power) {
        return powerDao.updateSuperPower(power);
    }

    @Override
    public Location updateLocal(Location local) {
        return locationDao.updateLocal(local);
    }

    @Override
    public Organization updateOrg(Organization org) {
        return orgDao.updateOrg(org);
    }

    @Override
    public Hero updateHero(Hero hero) {
        Hero oldHero = heroDao.getHero(hero.getHeroId());
        List<Organization> oldHeroOrgs = orgDao.getOrgsByHero(oldHero);
        Map<SuperPower, Integer> oldHeroPowers = powerDao.getPowersByHero(hero);
        for (int i = 0; i < oldHeroOrgs.size(); i++){
            if (hero.getOrgs().contains(oldHeroOrgs.get(i))) {
                hero.getOrgs().remove(oldHeroOrgs.get(i));
            }
        }
        for (SuperPower power : oldHeroPowers.keySet()){
            if (hero.getPowers().containsKey(power) && hero.getPowers().get(power).equals(oldHeroPowers.get(power))){
                hero.getPowers().remove(power);
            } else if (hero.getPowers().containsKey(power) && !hero.getPowers().get(power).equals(oldHeroPowers.get(power))){
                heroDao.removePowerFromHero(hero, power);
            }
        }
        return heroDao.updateHero(hero);
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        List<Sighting> sights = sightingDao.getLastTenSightings();
        for (Sighting sight : sights) {
            Location local = locationDao.getLocalBySightId(sight.getSightingId());
            List<Hero> heroes = heroDao.getHeroesBySighting(sight);
            sight.setLocation(local);
            sight.setHeroes(heroes);
        }
        return sights;
    }

    @Override
    public List<Hero> getHeroesByDate(LocalDate date) {
        return heroDao.getHeroesBySightingDate(date);
    }

    @Override
    public List<Hero> getHeroesByPower(SuperPower power) {
        return heroDao.getHeroesByPower(power);
    }

    @Override
    public List<Hero> getHeroesByLocation(Location local) {
        return heroDao.getHeroesByLocation(local);
    }

    @Override
    public List<Hero> getHeroesByOrganization(Organization org) {
        return heroDao.getHeroesByOrg(org);
    }

    @Override
    public Map<SuperPower, Integer> getPowersByHero(Hero hero) {
        return powerDao.getPowersByHero(hero);
    }

    @Override
    public Set<SuperPower> getPowersByLevel(int level) {
        return powerDao.getPowersGreaterThan(level);
    }

    @Override
    public List<Organization> getOrgsByHeroes(Hero hero) {
        return orgDao.getOrgsByHero(hero);
    }

    @Override
    public Organization getOrgByLocation(Location local) {
        return orgDao.getOrgByLocation(local);
    }

    @Override
    public List<Location> getLocalsByHero(Hero hero) {
        return locationDao.getAllLocalsByHero(hero);
    }

    @Override
    public List<Location> getLocalsByOrg(Organization org) {
        return locationDao.getAllLocalsByOrg(org);
    }

    @Override
    public List<Location> getLocalsByDate(LocalDate date) {
        return locationDao.getAllLocalsBySightDate(date);
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        List<Sighting> sights = sightingDao.getSightingsByHero(hero);
        for (Sighting sight : sights) {
            List<Hero> heroes = heroDao.getHeroesBySighting(sight);
            sight.setHeroes(heroes);
            Location local = locationDao.getLocalBySightId(sight.getSightingId());
            sight.setLocation(local);
        }
        return sights;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        List<Sighting> sights = sightingDao.getSightingsByDate(date);
        for (Sighting sight : sights) {
            List<Hero> heroes = heroDao.getHeroesBySighting(sight);
            sight.setHeroes(heroes);
            Location local = locationDao.getLocalBySightId(sight.getSightingId());
            sight.setLocation(local);
        }
        return sights;
    }

    @Override
    public List<Sighting> getSightingsByLocal(Location local) {
        List<Sighting> sights = sightingDao.getSightingsByLocation(local);
        for (Sighting sight : sights) {
            List<Hero> heroes = heroDao.getHeroesBySighting(sight);
            sight.setHeroes(heroes);
            sight.setLocation(local);
        }
        return sights;
    }
      
}
