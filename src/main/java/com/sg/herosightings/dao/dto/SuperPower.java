/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao.dto;

import java.util.List;

/**
 *
 * @author apprentice
 */
public class SuperPower {
    
    private int superPowerId;
    private String powerName;
    private String description;
    private int powerLevel;

    public int getSuperPowerId() {
        return superPowerId;
    }

    public void setSuperPowerId(int superPowerId) {
        this.superPowerId = superPowerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
    
    
}
