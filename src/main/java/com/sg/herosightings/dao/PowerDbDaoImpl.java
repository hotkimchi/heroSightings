/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class PowerDbDaoImpl implements PowerInfoDbDao {
    
    private static final String SQL_INSERT_POWER
            = "INSERT INTO superpower (power_name, description) VALUES (?, ?)";
    private static final String SQL_DELETE_POWER
            = "DELETE FROM superpower WHERE superpower_id = ?";
    private static final String SQL_UPDATE_POWER
            = "UPDATE superpower SET power_name = ?, description = ? WHERE superpower_id = ?";
    private static final String SQL_GET_POWER_BY_ID
            = "SELECT * FROM superpower WHERE superpower_id = ?";
    private static final String SQL_GET_ALL_POWERS
            = "SELECT * FROM superpower";
    private static final String SQL_DELETE_POWER_HERO
            = "DELETE FROM superherosuperpower WHERE superpower_id = ?";
    private static final String SQL_SELECT_POWER_BY_HERO
            = "SELECT DISTINCT p.power_name, p.description, p.superpower_id FROM superpower p JOIN superherosuperpower hp ON "
            + "p.superpower_id = hp.superpower_id WHERE hp.superhero_id = ?";
    private static final String SQL_GET_POWER_LEVEL
            = "SELECT power_level FROM superherosuperpower WHERE superhero_id = ? AND superpower_id = ?";
    private static final String SQL_GET_POWERS_GREATER_THAN
            = "SELECT DISTINCT * FROM superpower p JOIN superherosuperpower hp ON p.superpower_id = hp.superpower_id WHERE hp.power_level >= ?";
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower insertSuperPower(SuperPower power) {
        jdbcTemplate.update(SQL_INSERT_POWER,
                power.getPowerName(),
                power.getDescription());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setSuperPowerId(id);
        return power;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower deleteSuperPower(int powerId) {
        SuperPower power = getSuperPower(powerId);
        jdbcTemplate.update(SQL_DELETE_POWER_HERO, powerId);
        jdbcTemplate.update(SQL_DELETE_POWER, powerId);
        return power;
    }

    @Override
    public SuperPower updateSuperPower(SuperPower power) {
        jdbcTemplate.update(SQL_UPDATE_POWER,
                power.getPowerName(),
                power.getDescription(),
                power.getSuperPowerId());
        return getSuperPower(power.getSuperPowerId());
    }

    @Override
    public SuperPower getSuperPower(int powerId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_POWER_BY_ID, new PowerMapper(), powerId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return jdbcTemplate.query(SQL_GET_ALL_POWERS, new PowerMapper());
    }

    @Override
    public Map<SuperPower, Integer> getPowersByHero(Hero hero) {
        List<SuperPower> powers =  jdbcTemplate.query(SQL_SELECT_POWER_BY_HERO, new PowerMapper(), hero.getHeroId());
        Map<SuperPower, Integer> powerMap = new HashMap();
        for (SuperPower power : powers) {
            try{
                Integer powerLevel = jdbcTemplate.queryForObject(SQL_GET_POWER_LEVEL, new PowerLevelMapper(), hero.getHeroId(), power.getSuperPowerId());
                powerMap.put(power, powerLevel);
            } catch (EmptyResultDataAccessException ex) {
                return null;
            }
        }
        return powerMap;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Set<SuperPower> getPowersGreaterThan (int powerLevel) {
        List<SuperPower> powers = jdbcTemplate.query(SQL_GET_POWERS_GREATER_THAN, new PowerMapper(), powerLevel);
        Set<SuperPower> powerfulPowers = new HashSet(powers);
        return powerfulPowers;
    }
    
    private static final class PowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int i) throws SQLException {
            SuperPower power = new SuperPower();
            power.setPowerName(rs.getString("power_name"));
            power.setDescription(rs.getString("description"));
            power.setSuperPowerId(rs.getInt("superpower_id"));
            return power;
        }

    }
    
    private static final class PowerLevelMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            int powerLevel = rs.getInt("power_level");
            return powerLevel;
        }
        
    }

}
