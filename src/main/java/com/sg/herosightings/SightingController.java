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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping("/sighting")
public class SightingController {

    private HeroSightingService service;

    public SightingController(HeroSightingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getSights", method = RequestMethod.GET)
    public String getSightings(Model model) {
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locals = service.getAllLocals();
        List<Sighting> sights = service.getAllSightings();
        model.addAttribute("sights", sights);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locals", locals);
        return "sighting";
    }

    @RequestMapping(value = "/addSighting", method = RequestMethod.POST)
    public String addSighting(HttpServletRequest request) {
        Sighting sight = new Sighting();
        String[] heroStrings = request.getParameterValues("sightHeroes");
        if (service.validateIdsNotNull(heroStrings)) {
            List<Hero> heroes = new ArrayList();
            for (String heroId : heroStrings) {
                Hero hero = service.getHero(Integer.parseInt(heroId));
                heroes.add(hero);
            }
            sight.setHeroes(heroes);
        }
        String localStringId = request.getParameter("sightLocation");
        int localId = Integer.parseInt(localStringId);
        if (localId != 0) {
            Location local = service.getLocation(localId);
            sight.setLocation(local);
            String dateString = request.getParameter("sightDate");
            if (dateString != null) {
                LocalDate date = LocalDate.parse(dateString);
                sight.setDate(date);
                service.createSighting(sight);
            }
        } else {
            return "redirect:/sighting/getSights";
        }
        return "redirect:/sighting/getSights";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        int sightId = Integer.parseInt(request.getParameter("deleteSighting"));
        service.deleteSighting(sightId);
        return "redirect:/sighting/getSights";
    }
}
