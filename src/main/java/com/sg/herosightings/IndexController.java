/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Sighting;
import com.sg.herosightings.service.HeroSightingService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class IndexController {
    
    private HeroSightingService service;
    
    @Inject
    public IndexController(HeroSightingService service) {
        this.service = service;
    }
    
    @RequestMapping(value = {"/", "/getIndex"}, method = RequestMethod.GET)
    public String getLatestSightings(Model model) {
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locals = service.getAllLocals();
        List<Sighting> sights = service.getLastTenSightings();
        model.addAttribute("locals", locals);
        model.addAttribute("heroes", heroes);
        model.addAttribute("sights", sights);
        return "index";
    }
    
}
