/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.dao.dto.Sighting;
import com.sg.herosightings.dao.dto.SuperPower;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class HeroDbDaoImplTest {
    
    private HeroInfoDbDao heroDao;
    private PowerInfoDbDao powerDao;
    private OrgInfoDbDao orgDao;
    private LocationInfoDbDao localDao;
    private SightingInfoDbDao sightDao;
    
    public HeroDbDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        heroDao = ctx.getBean("heroDao", HeroInfoDbDao.class);
        powerDao = ctx.getBean("powerDao", PowerInfoDbDao.class);
        orgDao = ctx.getBean("orgDao", OrgInfoDbDao.class);
        localDao = ctx.getBean("locationDao", LocationInfoDbDao.class);
        sightDao = ctx.getBean("sightingDao", SightingInfoDbDao.class);
        List<Hero> heroes = heroDao.getAllHeroes();
        for(Hero currentHero : heroes) {
            heroDao.deleteHero(currentHero.getHeroId());
        }
        List<SuperPower> powers = powerDao.getAllSuperPowers();
        for(SuperPower currentPower : powers){
            powerDao.deleteSuperPower(currentPower.getSuperPowerId());
        }
        List<Organization> orgs = orgDao.getAllOrgs();
        for (Organization currentOrg : orgs) {
            orgDao.deleteOrg(currentOrg.getOrganizationId());
        }
        List<Location> locals = localDao.getAllLocals();
        for (Location currentLocal : locals) {
            localDao.deleteLocal(currentLocal.getLocationId());
        }
        List<Sighting> sights = sightDao.getAllSightings();
        for (Sighting currentSight : sights) {
            sightDao.deleteSighting(currentSight.getSightingId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setJdbcTemplate method, of class HeroDbDaoImpl.
     */
    @Test
    public void testSetJdbcTemplate() {
    }

    /**
     * Test of insertHero method, of class HeroDbDaoImpl.
     */
    @Test
    public void testInsert() {
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        List<Hero> oneGuy = new ArrayList();
        oneGuy.add(hero);
        
        SuperPower notSeriousPunch = new SuperPower();
        SuperPower seriousPunch = new SuperPower();
        notSeriousPunch.setPowerName("Not Serious Punch");
        notSeriousPunch.setDescription("Kills in one punch");
        seriousPunch.setPowerName("Serious Punch");
        seriousPunch.setDescription("Kills in one punch");
        powerDao.insertSuperPower(notSeriousPunch);
        powerDao.insertSuperPower(seriousPunch);
        
        
        Organization org = new Organization();
        org.setOrganizationName("The Hero Association");
        org.setDescription("Heroes that like to do good for the community. If you are not part of the association, you are either a creep or a dangerous person.");
        org.setMotto("Do some good!");
        //org.setHeroes(oneGuy);
        orgDao.insertOrg(org);
        
        Location local = new Location();
        local.setLocalName("house");
        local.setDescription("In a bad neighborhood");
        local.setAddress("District 9");
        local.setLatitude(56.86753f);
        local.setLongitude(-23.659392f);
        local.setOrg(org);
        localDao.insertLocal(local);
        
        Sighting orgSight = new Sighting();
        LocalDate date = LocalDate.parse("2017-05-02");
        orgSight.setDate(date);
        orgSight.setLocation(local);
        //orgSight.setHeroes(oneGuy);
        sightDao.insertSighting(orgSight);
        
        Map<SuperPower, Integer> powers = new HashMap();
        powers.put(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()), 1000);
        powers.put(powerDao.getSuperPower(seriousPunch.getSuperPowerId()), 5000);
        
        hero.setPowers(powers);
        List<Organization> orgs = new ArrayList();
        orgs.add(orgDao.getOrg(org.getOrganizationId()));
        
        hero.setOrgs(orgs);
        List<Sighting> sightings = new ArrayList();
        hero.setSightings(sightings);
        
        hero = heroDao.insertHero(hero);
        
        Hero fromHero = heroDao.getHero(hero.getHeroId());
        assertEquals(fromHero.getHeroId(), hero.getHeroId());
        assertEquals(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()).getSuperPowerId(), notSeriousPunch.getSuperPowerId());
        assertEquals(powerDao.getSuperPower(seriousPunch.getSuperPowerId()).getSuperPowerId(), seriousPunch.getSuperPowerId());
        assertEquals(orgDao.getOrg(org.getOrganizationId()).getOrganizationId(), org.getOrganizationId());
        assertEquals(localDao.getLocal(local.getLocationId()).getLocationId(), local.getLocationId());
        assertEquals(sightDao.getSighting(orgSight.getSightingId()).getSightingId(), orgSight.getSightingId());
    }

    /**
     * Test of removePowerFromHero method, of class HeroDbDaoImpl.
     */
    @Test
    public void testRemovePowerFromHero() {
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        
        SuperPower notSeriousPunch = new SuperPower();
        SuperPower seriousPunch = new SuperPower();
        notSeriousPunch.setPowerName("Not Serious Punch");
        notSeriousPunch.setDescription("Kills in one punch");
        seriousPunch.setPowerName("Serious Punch");
        seriousPunch.setDescription("Kills in one punch");
        powerDao.insertSuperPower(notSeriousPunch);
        powerDao.insertSuperPower(seriousPunch);
        
        Map<SuperPower, Integer> powers = new HashMap();
        powers.put(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()), 1000);
        powers.put(powerDao.getSuperPower(seriousPunch.getSuperPowerId()), 5000);
        
        hero.setPowers(powers);
        heroDao.insertHero(hero);
        
        assertEquals(powerDao.getPowersByHero(hero).size(), hero.getPowers().size());
        heroDao.removePowerFromHero(hero, seriousPunch);
        assertEquals(powerDao.getPowersByHero(hero).size(), 1);
    }

    /**
     * Test of deleteHero method, of class HeroDbDaoImpl.
     */
    @Test
    public void testDelete() {
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        List<Hero> oneGuy = new ArrayList();
        oneGuy.add(hero);
        
        SuperPower notSeriousPunch = new SuperPower();
        SuperPower seriousPunch = new SuperPower();
        notSeriousPunch.setPowerName("Not Serious Punch");
        notSeriousPunch.setDescription("Kills in one punch");
        seriousPunch.setPowerName("Serious Punch");
        seriousPunch.setDescription("Kills in one punch");
        powerDao.insertSuperPower(notSeriousPunch);
        powerDao.insertSuperPower(seriousPunch);
        
        
        Organization org = new Organization();
        org.setOrganizationName("The Hero Association");
        org.setDescription("Heroes that like to do good for the community. If you are not part of the association, you are either a creep or a dangerous person.");
        org.setMotto("Do some good!");
        //org.setHeroes(oneGuy);
        orgDao.insertOrg(org);
        
        Location local = new Location();
        local.setLocalName("house");
        local.setDescription("In a bad neighborhood");
        local.setAddress("District 9");
        local.setLatitude(56.86753f);
        local.setLongitude(-23.659392f);
        local.setOrg(org);
        localDao.insertLocal(local);
        
        Sighting orgSight = new Sighting();
        LocalDate date = LocalDate.parse("2017-05-02");
        orgSight.setDate(date);
        orgSight.setLocation(local);
        //orgSight.setHeroes(oneGuy);
        sightDao.insertSighting(orgSight);
        
        Map<SuperPower, Integer> powers = new HashMap();
        powers.put(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()), 1000);
        powers.put(powerDao.getSuperPower(seriousPunch.getSuperPowerId()), 5000);
        
        hero.setPowers(powers);
        List<Organization> orgs = new ArrayList();
        orgs.add(orgDao.getOrg(org.getOrganizationId()));
        
        hero.setOrgs(orgs);
        List<Sighting> sightings = new ArrayList();
        hero.setSightings(sightings);
        
        hero = heroDao.insertHero(hero);
        heroDao.deleteHero(hero.getHeroId());
        orgDao.deleteOrg(org.getOrganizationId());
        powerDao.deleteSuperPower(notSeriousPunch.getSuperPowerId());
        sightDao.deleteSighting(orgSight.getSightingId());
        localDao.deleteLocal(local.getLocationId());
        
        assertNull(heroDao.getHero(hero.getHeroId()));
        assertNull(orgDao.getOrg(org.getOrganizationId()));
        assertNull(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()));
        assertNull(sightDao.getSighting(orgSight.getSightingId()));
        assertNull(localDao.getLocal(local.getLocationId()));
    }

    /**
     * Test of updateHero method, of class HeroDbDaoImpl.
     */
    @Test
    public void testUpdate() {
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        List<Hero> oneGuy = new ArrayList();
        heroDao.insertHero(hero);
        
        SuperPower notSeriousPunch = new SuperPower();
        SuperPower seriousPunch = new SuperPower();
        notSeriousPunch.setPowerName("Not Serious Punch");
        notSeriousPunch.setDescription("Kills in one punch");
        seriousPunch.setPowerName("Serious Punch");
        seriousPunch.setDescription("Kills in one punch");
        powerDao.insertSuperPower(notSeriousPunch);
        powerDao.insertSuperPower(seriousPunch);
        notSeriousPunch.setDescription("Sometimes kills with One Punch");
        powerDao.updateSuperPower(notSeriousPunch);
        assertEquals(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()).getDescription(), notSeriousPunch.getDescription());
        Map<SuperPower, Integer> powers = new HashMap();
        powers.put(powerDao.getSuperPower(notSeriousPunch.getSuperPowerId()), 1000);
        powers.put(powerDao.getSuperPower(seriousPunch.getSuperPowerId()), 5000);
        
        hero.setPowers(powers);
        hero.setGoodHero(false);
        
        heroDao.updateHero(hero);
        assertTrue(heroDao.getHero(hero.getHeroId()).isGoodHero() == false);
        assertEquals(heroDao.getHeroesByPower(seriousPunch).size(), 1);
        
        Organization org = new Organization();
        org.setOrganizationName("The Hero Association");
        org.setDescription("Heroes that like to do good for the community. If you are not part of the association, you are either a creep or a dangerous person.");
        org.setMotto("Do some good!");
        oneGuy.add(hero);
        org.setHeroes(oneGuy);
        orgDao.insertOrg(org);
        
        Hero evilHero = new Hero();
        evilHero.setHeroName("One Kick");
        evilHero.setDescription("A Hero looking for fun and profit");
        evilHero.setGoodHero(false);
        heroDao.insertHero(evilHero);
        List<Hero> twoGuys = new ArrayList();
        twoGuys.add(evilHero);
        twoGuys.add(hero);
        org.setHeroes(twoGuys);
        org.setEmail("email@amil.com");
        orgDao.updateOrg(org);
        
        Location local = new Location();
        local.setLocalName("house");
        local.setDescription("In a bad neighborhood");
        local.setAddress("District 9");
        local.setLatitude(56.86753f);
        local.setLongitude(-23.659392f);
        localDao.insertLocal(local);
        local.setOrg(org);
        local.setLongitude(45.987612f);
        localDao.updateLocal(local);
        List<Location> orgLocal = new ArrayList();
        orgLocal.add(local);
        org.setLocals(orgLocal);
        orgDao.updateOrg(org);
        
        assertEquals(orgDao.getOrg(org.getOrganizationId()).getEmail(), org.getEmail());
        assertTrue(heroDao.getHeroesByOrg(org).size() > oneGuy.size());
        assertEquals(orgDao.getOrgsByHero(evilHero).get(0).getOrganizationId(), org.getOrganizationId());
        List<Location> orgLocals= localDao.getAllLocalsByOrg(org);
        //assertEquals(orgLocals.get(0).getLocationId(), local.getLocationId());
        assertTrue(localDao.getLocal(local.getLocationId()).getLongitude() == local.getLongitude());
        
        
    }

    /**
     * Test of getHero method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGetHero() {
    }

    /**
     * Test of getAllHeroes method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGetAll() {
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        Hero evilHero = new Hero();
        heroDao.insertHero(hero);
        evilHero.setHeroName("One Kick");
        evilHero.setDescription("A Hero looking for fun and profit");
        evilHero.setGoodHero(false);
        heroDao.insertHero(evilHero);
        List<Hero> oneGuy = new ArrayList();
        oneGuy.add(hero);
        oneGuy.add(evilHero);
        
        assertEquals(heroDao.getAllHeroes().size(), oneGuy.size());
        
        SuperPower notSeriousPunch = new SuperPower();
        SuperPower seriousPunch = new SuperPower();
        notSeriousPunch.setPowerName("Not Serious Punch");
        notSeriousPunch.setDescription("Kills in one punch");
        seriousPunch.setPowerName("Serious Punch");
        seriousPunch.setDescription("Kills in one punch");
        powerDao.insertSuperPower(notSeriousPunch);
        powerDao.insertSuperPower(seriousPunch);
        List<SuperPower> powers = new ArrayList();
        powers.add(seriousPunch);
        powers.add(notSeriousPunch);
        
        assertEquals(powerDao.getAllSuperPowers().size(), powers.size());
        
        Location local = new Location();
        local.setLocalName("house");
        local.setDescription("In a bad neighborhood");
        local.setAddress("District 9");
        local.setLatitude(56.86753f);
        local.setLongitude(-23.659392f);
        localDao.insertLocal(local);
        Location otherLocal = new Location();
        otherLocal.setLocalName("Bar");
        otherLocal.setDescription("In a good neighborhood");
        otherLocal.setAddress("District 9");
        otherLocal.setLatitude(46.86753f);
        otherLocal.setLongitude(-3.659392f);
        localDao.insertLocal(otherLocal);
        List<Location> locals = new ArrayList();
        locals.add(local);
        locals.add(otherLocal);
        
        assertEquals(localDao.getAllLocals().size(), locals.size());
        
        Sighting orgSight = new Sighting();
        LocalDate date = LocalDate.parse("2017-05-02");
        orgSight.setDate(date);
        orgSight.setLocation(local);
        sightDao.insertSighting(orgSight);
        Sighting sight = new Sighting();
        LocalDate otherDate = LocalDate.parse("2016-05-02");
        sight.setDate(otherDate);
        sight.setLocation(otherLocal);
        sightDao.insertSighting(sight);
        List<Sighting> sights = new ArrayList();
        sights.add(sight);
        sights.add(orgSight);
        
        assertEquals(sightDao.getAllSightings().size(), sights.size());
    }

    /**
     * Test of getHeroesByOrg method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGetHeroesByOrg() {
    }

    /**
     * Test of getHeroesByPower method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGetHeroesByPower() {
    }

    /**
     * Test of getHeroesByLocation method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGettingHeroesLocationsAndSightings() {
        Location local = new Location();
        local.setLocalName("house");
        local.setDescription("In a bad neighborhood");
        local.setAddress("District 9");
        local.setLatitude(56.86753f);
        local.setLongitude(-23.659392f);
        localDao.insertLocal(local);
        Location otherLocal = new Location();
        otherLocal.setLocalName("Bar");
        otherLocal.setDescription("In a good neighborhood");
        otherLocal.setAddress("District 9");
        otherLocal.setLatitude(46.86753f);
        otherLocal.setLongitude(-3.659392f);
        localDao.insertLocal(otherLocal);
        
        Hero hero = new Hero();
        hero.setHeroName("One Punch");
        hero.setDescription("A Hero looking for fun and profit");
        hero.setGoodHero(true);
        heroDao.insertHero(hero);
        Hero evilHero = new Hero();
        evilHero.setHeroName("One Kick");
        evilHero.setDescription("A Hero looking for fun and profit");
        evilHero.setGoodHero(false);
        heroDao.insertHero(evilHero);
        
        List<Hero> heroes = new ArrayList();
        heroes.add(hero);
        heroes.add(evilHero);
        
        Sighting orgSight = new Sighting();
        LocalDate date = LocalDate.parse("2017-05-02");
        orgSight.setDate(date);
        orgSight.setLocation(local);
        orgSight.setHeroes(heroes);
        sightDao.insertSighting(orgSight);
        Sighting sight = new Sighting();
        LocalDate otherDate = LocalDate.parse("2016-05-02");
        sight.setDate(otherDate);
        sight.setLocation(otherLocal);
        sight.setHeroes(heroes);
        sightDao.insertSighting(sight);
        Sighting localSight = new Sighting();
        localSight.setDate(date);
        localSight.setLocation(local);
        sightDao.insertSighting(localSight);
        List<Sighting> sights = new ArrayList();
        sights.add(sight);
        sights.add(orgSight);
        
        
        
        
        assertEquals(heroDao.getHeroesByLocation(local).get(0).getHeroId(), hero.getHeroId());
        assertEquals(heroDao.getHeroesByLocation(otherLocal).size(), 2);
        assertEquals(heroDao.getHeroesBySightingDate(date).get(0).getHeroId(), hero.getHeroId());
        assertEquals(heroDao.getHeroesBySightingDate(otherDate).size(), 2);
        
        assertEquals(localDao.getAllLocalsBySightDate(date).get(0).getLocationId(), local.getLocationId());
        assertEquals(localDao.getAllLocalsBySightDate(otherDate).get(0).getLocationId(), otherLocal.getLocationId());
        
        assertEquals(sightDao.getSightingsByDate(date).get(0).getSightingId(), orgSight.getSightingId());
        assertEquals(sightDao.getSightingsByLocation(local).size(), 2);
    }

    /**
     * Test of getHeroesBySightingDate method, of class HeroDbDaoImpl.
     */
    @Test
    public void testGetHeroesBySightingDate() {
    }
    
}
