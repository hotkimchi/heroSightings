/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Organization;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface OrgInfoDbDao {
    
    Organization insertOrg(Organization org);
    Organization deleteOrg(int heroId);
    Organization updateOrg(Organization org);
    Organization getOrg(int heroId);
    List<Organization> getAllOrgs();
    List<Organization> getOrgsByHero(Hero hero);
    
}
