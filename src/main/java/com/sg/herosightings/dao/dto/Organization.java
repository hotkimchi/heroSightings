/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class Organization {
    
    private int organizationId;
    private String organizationName;
    private String description;
    private String motto;
    private String email;
    private String phone;
    private String otherContactInfo;
    private List<Hero> heroes = new ArrayList();
    private List<Location> locals = new ArrayList();

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtherContactInfo() {
        return otherContactInfo;
    }

    public void setOtherContactInfo(String otherContactInfo) {
        this.otherContactInfo = otherContactInfo;
    }
    

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<Location> getLocals() {
        return locals;
    }

    public void setLocals(List<Location> locals) {
        this.locals = locals;
    }
    
    
}
