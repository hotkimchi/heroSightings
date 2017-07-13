/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public class Hero {
    
    private int heroId;
    private String heroName;
    private String description;
    private List<Sighting> sightings = new ArrayList();
    private Map<SuperPower, Integer> powers = new HashMap();
    private List<Organization> orgs = new ArrayList();
    private boolean goodHero;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    public Map<SuperPower, Integer> getPowers() {
        return powers;
    }

    public void setPowers(Map<SuperPower, Integer> powers) {
        this.powers = powers;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }

    public boolean isGoodHero() {
        return goodHero;
    }

    public void setGoodHero(boolean goodHero) {
        this.goodHero = goodHero;
    }
    
    
    
}
