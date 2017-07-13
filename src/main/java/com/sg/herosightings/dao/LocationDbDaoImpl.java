/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
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
public class LocationDbDaoImpl implements LocationInfoDbDao {

    private static final String SQL_INSERT_LOCATION
            = "INSERT INTO location (location_name, description, address, latitude, "
            + "longitude) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SET_LOCATION_ORG
            = "UPDATE location SET organization_id = ? WHERE location_id = ?";
    private static final String SQL_DELETE_LOCATION
            = "DELETE FROM location WHERE location_id = ?";
    private static final String SQL_UPDATE_LOCATION
            = "UPDATE location SET location_name = ?, description = ?, address = ?, "
            + "latitude = ?, longitude = ? WHERE location_id = ?";
    private static final String SQL_GET_LOCATION_BY_ID
            = "SELECT * FROM location WHERE location_id = ?";
    private static final String SQL_GET_ALL_LOCATIONS
            = "SELECT * FROM location";
    private static final String SQL_SELECT_LOCATIONS_BY_ORG
            = "SELECT * FROM location WHERE organization_id = ?";
    private static final String SQL_DELETE_SIGHTING_BY_LOCATION
            = "DELETE FROM sighting WHERE location_id = ?";
    private static final String SQL_SELECT_LOCATION_BY_DATE
            = "SELECT l.location_name, l.description, l.address, l.latitude, l.longitude, l.location_id FROM location l "
            + "JOIN sighting s ON l.location_id = s.location_id "
            + "WHERE s.date = ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location insertLocal(Location local) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                local.getLocalName(),
                local.getDescription(),
                local.getAddress(),
                local.getLatitude(),
                local.getLongitude());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        local.setLocationId(id);
        if (local.getOrg() != null){
            setOrgLocal(local.getOrg(), id);
        }
        return getLocal(id);
    }

    private void setOrgLocal(Organization org, int localId) {
        jdbcTemplate.update(SQL_SET_LOCATION_ORG,
                org.getOrganizationId(), localId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location deleteLocal(int localId) {
        Location local = getLocal(localId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING_BY_LOCATION, localId);
        jdbcTemplate.update(SQL_DELETE_LOCATION, localId);
        return local;
    }

    @Override
    public Location updateLocal(Location local) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                local.getLocalName(),
                local.getDescription(),
                local.getAddress(),
                local.getLatitude(),
                local.getLongitude(),
                local.getLocationId());
        if (local.getOrg() != null){
            setOrgLocal(local.getOrg(), local.getLocationId());
        }
        return getLocal(local.getLocationId());
    }

    @Override
    public Location getLocal(int localId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_LOCATION_BY_ID,
                    new LocationMapper(), localId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocals() {
        return jdbcTemplate.query(SQL_GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public List<Location> getAllLocalsBySightDate(LocalDate date) {
        return jdbcTemplate.query(SQL_SELECT_LOCATION_BY_DATE, new LocationMapper(), java.sql.Date.valueOf(date));
    }

    @Override
    public List<Location> getAllLocalsByOrg(Organization org) {
        List<Location> localList = jdbcTemplate.query(SQL_SELECT_LOCATIONS_BY_ORG,
                new LocationMapper(), org.getOrganizationId());
        return localList;
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location local = new Location();
            local.setLocalName(rs.getString("location_name"));
            local.setDescription(rs.getString("description"));
            local.setAddress(rs.getString("address"));
            local.setLatitude(rs.getDouble("latitude"));
            local.setLongitude(rs.getDouble("longitude"));
            local.setLocationId(rs.getInt("location_id"));
            return local;
        }

    }

}
