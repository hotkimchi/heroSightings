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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class HeroDbDaoImpl implements HeroInfoDbDao {

    private static final String SQL_INSERT_HERO
            = "INSERT INTO superhero (hero_name, description, good_hero) "
            + "VALUES (?, ?, ?)";
    private static final String SQL_DELETE_HERO
            = "DELETE FROM superhero WHERE superhero_id = ?";
    private static final String SQL_UPDATE_HERO
            = "UPDATE superhero SET hero_name = ? , description = ? , good_hero = ? "
            + "WHERE superhero_id = ?";
    private static final String SQL_GET_HERO_BY_ID
            = "SELECT * FROM superhero WHERE superhero_id = ?";
    private static final String SQL_GET_ALL_HEROES
            = "SELECT DISTINCT * FROM superhero";
    private static final String SQL_DELETE_HERO_ORG
            = "DELETE FROM organizationsuperhero WHERE superhero_id = ?";
    private static final String SQL_DELETE_HERO_SIGHTING
            = "DELETE FROM sightinghero WHERE superhero_id = ?";
    private static final String SQL_DELETE_HERO_POWER
            = "DELETE FROM superherosuperpower WHERE superhero_id = ?";
    private static final String SQL_INSERT_POWER_HERO
            = "INSERT INTO superherosuperpower (superhero_id, superpower_id, power_level) "
            + "VALUES (?, ?, ?)";
    private static final String SQL_INSERT_ORG_HERO
            = "INSERT INTO organizationsuperhero (organization_id, superhero_id) "
            + "VALUES (?, ?)";
    private static final String SQL_INSERT_SIGHTING_HERO
            = "INSERT INTO sightinghero (sighting_id, superhero_id) "
            + "VALUES (?, ?)";
    private static final String SQL_DELETE_POWER_FROM_HERO
            = "DELETE FROM superherosuperpower WHERE superhero_id = ? AND superpower_id = ?";
    private static final String SQL_UPDATE_POWER_LEVEL
            = "UPDATE superherosuperpower SET power_level = ? "
            + "WHERE superhero_id = ? AND superpower_id = ?";
    private static final String SQL_SELECT_HERO_BY_DATE
            = "SELECT DISTINCT h.hero_name, h.description, h.good_hero, h.superhero_id FROM superhero h "
            + "JOIN sightinghero sh ON h.superhero_id = sh.superhero_id "
            + "JOIN sighting s ON s.sighting_id = sh.sighting_id "
            + "WHERE s.date = ?";
    private static final String SQL_SELECT_HERO_BY_SIGHTING
            = "SELECT DISTINCT h.hero_name, h.description, h.good_hero, h.superhero_id FROM superhero h "
            + "JOIN sightinghero sh ON h.superhero_id = sh.superhero_id "
            + "JOIN sighting s ON s.sighting_id = sh.sighting_id "
            + "WHERE s.sighting_id = ?";
    private static final String SQL_SELECT_HERO_BY_LOCATION
            = "SELECT DISTINCT h.hero_name, h.description, h.good_hero, h.superhero_id FROM superhero h "
            + "JOIN sightinghero sh ON h.superhero_id = sh.superhero_id "
            + "JOIN sighting s ON s.sighting_id = sh.sighting_id "
            + "WHERE s.location_id = ?";
    private static final String SQL_SELECT_HERO_BY_ORG
            = "SELECT DISTINCT h.hero_name, h.description, h.good_hero, h.superhero_id FROM superhero h "
            + "JOIN organizationsuperhero oh ON h.superhero_id = oh.superhero_id "
            + "WHERE oh.organization_id = ?";
    private static final String SQL_SELECT_HERO_BY_POWER
            = "SELECT DISTINCT h.hero_name, h.description, h.good_hero, h.superhero_id FROM superhero h "
            + "JOIN superherosuperpower hp ON h.superhero_id = hp.superHero_id "
            + "WHERE hp.superpower_id = ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero insertHero(Hero hero) {
        jdbcTemplate.update(SQL_INSERT_HERO,
                hero.getHeroName(),
                hero.getDescription(),
                hero.isGoodHero());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(id);
        insertHeroInfo(hero);
        return hero;
    }

    private void insertHeroInfo(Hero hero) {
        final List<Organization> orgs = hero.getOrgs();
        final Map<SuperPower, Integer> powers = hero.getPowers();
        final List<Sighting> sights = hero.getSightings();
        for (Organization currentOrg : orgs) {
            addHeroesToOrg(currentOrg.getOrganizationId(), hero);
        }
        addSuperPowerToHero(hero.getHeroId(), powers);
        updatePowerLevel(hero, powers);
//        for (Sighting sight : sights) {
//            addSightingForHero(sight.getSightingId(), hero);
//        }
    }

    private void addHeroesToOrg(int orgId, Hero hero) {
        jdbcTemplate.update(SQL_INSERT_ORG_HERO, orgId, hero.getHeroId());
    }

    private void addSightingForHero(int sightId, Hero hero) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING_HERO,
                sightId, hero.getHeroId());
    }

    @Override
    public Hero removePowerFromHero(Hero hero, SuperPower power) {
        Hero poorHero = getHero(hero.getHeroId());
        jdbcTemplate.update(SQL_DELETE_POWER_FROM_HERO,
                poorHero.getHeroId(), power.getSuperPowerId());
        return poorHero;
    }

    private void updatePowerLevel(Hero hero, Map<SuperPower, Integer> powers) {
        for (SuperPower power : powers.keySet()) {
            jdbcTemplate.update(SQL_UPDATE_POWER_LEVEL, powers.get(power),
                    hero.getHeroId(), power.getSuperPowerId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero deleteHero(int heroId) {
        Hero hero = getHero(heroId);
        jdbcTemplate.update(SQL_DELETE_HERO_POWER, heroId);
        jdbcTemplate.update(SQL_DELETE_HERO_ORG, heroId);
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING, heroId);
        jdbcTemplate.update(SQL_DELETE_HERO, heroId);
        return hero;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero updateHero(Hero hero) {
        jdbcTemplate.update(SQL_UPDATE_HERO,
                hero.getHeroName(),
                hero.getDescription(),
                hero.isGoodHero(),
                hero.getHeroId());
        jdbcTemplate.update(SQL_DELETE_HERO_POWER, hero.getHeroId());
        jdbcTemplate.update(SQL_DELETE_HERO_ORG, hero.getHeroId());
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING, hero.getHeroId());
        insertHeroInfo(hero);
        return getHero(hero.getHeroId());
    }

    private void addSuperPowerToHero(int heroId, Map<SuperPower, Integer> powers) {
        for (SuperPower power : powers.keySet()) {
            jdbcTemplate.update(SQL_INSERT_POWER_HERO,
                    heroId, power.getSuperPowerId(), powers.get(power));
        }
    }

    @Override
    public Hero getHero(int heroId) {
        Hero hero = new Hero();
        try {
            hero = jdbcTemplate.queryForObject(SQL_GET_HERO_BY_ID,
                    new HeroMapper(), heroId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        return hero;
    }

    @Override
    public List<Hero> getAllHeroes() {
        return jdbcTemplate.query(SQL_GET_ALL_HEROES, new HeroMapper());
    }

    @Override
    public List<Hero> getHeroesByOrg(Organization org) {
        return jdbcTemplate.query(SQL_SELECT_HERO_BY_ORG,
                new HeroMapper(), org.getOrganizationId());
    }

    @Override
    public List<Hero> getHeroesByPower(SuperPower power) {
        return jdbcTemplate.query(SQL_SELECT_HERO_BY_POWER,
                new HeroMapper(), power.getSuperPowerId());
    }

    @Override
    public List<Hero> getHeroesByLocation(Location local) {
        return jdbcTemplate.query(SQL_SELECT_HERO_BY_LOCATION,
                new HeroMapper(), local.getLocationId());
    }

    @Override
    public List<Hero> getHeroesBySightingDate(LocalDate date) {
        return jdbcTemplate.query(SQL_SELECT_HERO_BY_DATE,
                new HeroMapper(), java.sql.Date.valueOf(date));
    }
    
    @Override
    public List<Hero> getHeroesBySighting(Sighting sight) {
        return jdbcTemplate.query(SQL_SELECT_HERO_BY_SIGHTING, new HeroMapper(), sight.getSightingId());
    }

    private static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroName(rs.getString("hero_name"));
            hero.setDescription(rs.getString("description"));
            hero.setGoodHero(rs.getBoolean("good_hero"));
            hero.setHeroId(rs.getInt("superhero_id"));
            return hero;
        }
    }

}
