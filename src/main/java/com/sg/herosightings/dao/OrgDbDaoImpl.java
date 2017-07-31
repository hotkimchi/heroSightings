/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OrgDbDaoImpl implements OrgInfoDbDao {

    private static final String SQL_INSERT_ORG
            = "INSERT INTO organization (organization_name, description, motto, contact_email, "
            + "contact_phone, contact_other) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ORG
            = "DELETE FROM organization WHERE organization_id = ?";
    private static final String SQL_UPDATE_ORG
            = "UPDATE organization SET organization_name = ?, description = ?, motto = ?, "
            + "contact_email = ?, contact_phone = ?, contact_other = ? WHERE organization_id = ?";
    private static final String SQL_GET_ORG_BY_ID
            = "SELECT DISTINCT * FROM organization WHERE organization_id = ?";
    private static final String SQL_GET_ALL_ORGS
            = "SELECT DISTINCT * FROM organization";
    private static final String SQL_INSERT_ORG_HERO
            = "INSERT INTO organizationsuperhero (organization_id, superhero_id) "
            + "VALUES (?, ?)";
    private static final String SQL_DELETE_ORG_HERO
            = "DELETE FROM organizationsuperhero WHERE organization_id = ?";
    private static final String SQL_SET_LOCATION_ORG
            = "UPDATE location SET organization_id = ? WHERE location_id = ?";
    private static final String SQL_SET_LOCATION_ORG_NULL
            = "UPDATE location SET organization_id = null WHERE organization_id = ?";
    private static final String SQL_SELECT_ORG_BY_HERO
            = "SELECT DISTINCT o.organization_name, o.description, o.motto, o.contact_email, "
            + "o.contact_phone, o.contact_other, o.organization_id FROM organization o "
            + "JOIN organizationsuperhero oh ON o.organization_id = oh.organization_id "
            + "WHERE oh.superhero_id = ?";
    private static final String SQL_SELECT_ORG_BY_LOCATION
            = "SELECT DISTINCT o.organization_name, o.description, o.motto, o.contact_email, "
            + "o.contact_phone, o.contact_other, o.organization_id FROM organization o "
            + "JOIN location l ON o.organization_id = l.organization_id WHERE l.location_id = ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization insertOrg(Organization org) {
        jdbcTemplate.update(SQL_INSERT_ORG,
                org.getOrganizationName(),
                org.getDescription(),
                org.getMotto(),
                org.getEmail(),
                org.getPhone(),
                org.getOtherContactInfo());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrganizationId(id);
        addHeroesToOrg(org);
        setOrgLocal(org);
        return org;
    }

    private void addHeroesToOrg(Organization org) {
        final List<Hero> heroes = org.getHeroes();
        for (Hero hero : heroes) {
            jdbcTemplate.update(SQL_INSERT_ORG_HERO,
                    org.getOrganizationId(), hero.getHeroId());
        }
    }

    private void setOrgLocal(Organization org) {
        final List<Location> locals = org.getLocals();
        for (Location currentLocal : locals) {
            jdbcTemplate.update(SQL_SET_LOCATION_ORG,
                    org.getOrganizationId(), currentLocal.getLocationId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization deleteOrg(int orgId) {
        Organization org = getOrg(orgId);
        jdbcTemplate.update(SQL_SET_LOCATION_ORG_NULL, orgId);
        jdbcTemplate.update(SQL_DELETE_ORG_HERO, orgId);
        jdbcTemplate.update(SQL_DELETE_ORG, orgId);
        return org;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization updateOrg(Organization org) {
        jdbcTemplate.update(SQL_UPDATE_ORG,
                org.getOrganizationName(),
                org.getDescription(),
                org.getMotto(),
                org.getEmail(),
                org.getPhone(),
                org.getOtherContactInfo(),
                org.getOrganizationId());
        setOrgLocal(org);
        jdbcTemplate.update(SQL_DELETE_ORG_HERO, org.getOrganizationId());
        addHeroesToOrg(org);
        return getOrg(org.getOrganizationId());
    }

    @Override
    public Organization getOrg(int orgId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_ORG_BY_ID, new OrgMapper(), orgId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrgs() {
        return jdbcTemplate.query(SQL_GET_ALL_ORGS, new OrgMapper());
    }

    @Override
    public List<Organization> getOrgsByHero(Hero hero) {
        return jdbcTemplate.query(SQL_SELECT_ORG_BY_HERO,
                new OrgMapper(), hero.getHeroId());
    }

    @Override
    public Organization getOrgByLocation(Location local) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORG_BY_LOCATION, new OrgMapper(), local.getLocationId());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class OrgMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationName(rs.getString("organization_name"));
            org.setDescription(rs.getString("description"));
            org.setMotto(rs.getString("motto"));
            org.setEmail(rs.getString("contact_email"));
            org.setPhone(rs.getString("contact_phone"));
            org.setOtherContactInfo(rs.getString("contact_other"));
            org.setOrganizationId(rs.getInt("organization_id"));
            return org;
        }

    }

}
