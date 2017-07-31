/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class SightingDbDaoImpl implements SightingInfoDbDao{
    
    private static final String SQL_INSERT_SIGHTING
            = "INSERT INTO sighting (location_id, date) VALUES (?, ?)";
    private static final String SQL_DELETE_SIGHTING
            = "DELETE FROM sighting WHERE sighting_id = ?";
    private static final String SQL_GET_SIGHTING_BY_ID
            = "SELECT DISTINCT * FROM sighting WHERE sighting_id = ?";
    private static final String SQL_GET_ALL_SIGHTINGS
            = "SELECT DISTINCT * FROM sighting";
    private static final String SQL_DELETE_SIGHTING_HERO
            = "DELETE FROM sightinghero WHERE sighting_id = ?";
    private static final String SQL_SELECT_SIGHTING_BY_LOCATION
            = "SELECT DISTINCT s.date, s.sighting_id FROM sighting s WHERE s.location_id = ?";
    private static final String SQL_SELECT_SIGHTING_BY_DATE
            = "SELECT DISTINCT s.date, s.sighting_id FROM sighting s WHERE s.date = ?";
    private static final String SQL_SELECT_SIGHTING_BY_HERO
            = "SELECT DISTINCT s.date, s.sighting_id FROM sighting s JOIN sightinghero sh ON s.sighting_id = sh.sighting_id WHERE sh.superhero_id = ?";
    private static final String SQL_INSERT_SIGHTING_HERO
            = "INSERT INTO sightinghero (sighting_id, superhero_id) "
            + "VALUES (?, ?)";
    private static final String SQL_SELECT_LAST_TEN_SIGHTING
            = "SELECT DISTINCT date, sighting_id FROM sighting ORDER BY date DESC LIMIT 0, 10";
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting insertSighting(Sighting sight) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sight.getLocation().getLocationId(),
                java.sql.Date.valueOf(sight.getDate()));
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setSightingId(id);
        setHeroesSighted(sight);
        return getSighting(id);
    }
    
    private void setHeroesSighted(Sighting sight) {
        List<Hero> heroes = sight.getHeroes();
        for (Hero hero : heroes) {
            jdbcTemplate.update(SQL_INSERT_SIGHTING_HERO, sight.getSightingId(), hero.getHeroId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting deleteSighting(int sightId) {
        Sighting sight = getSighting(sightId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO, sightId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightId);
        return sight;
    }

    @Override
    public Sighting updateSighting(Sighting sight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sighting getSighting(int sightId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_SIGHTING_BY_ID,
                    new SightingMapper(), sightId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        return jdbcTemplate.query(SQL_GET_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_DATE, new SightingMapper(), java.sql.Date.valueOf(date));
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location local) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_LOCATION, new SightingMapper(), local.getLocationId());
    }
    
    @Override 
    public List<Sighting> getSightingsByHero (Hero hero) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_HERO, new SightingMapper(), hero.getHeroId());
    }
    
    @Override
    public List<Sighting> getLastTenSightings() {
        return jdbcTemplate.query(SQL_SELECT_LAST_TEN_SIGHTING, new SightingMapper());
    }
    
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sight = new Sighting();
            sight.setDate(rs.getDate("date").toLocalDate());
            sight.setSightingId(rs.getInt("sighting_id"));
            return sight;
        }

    }
}
