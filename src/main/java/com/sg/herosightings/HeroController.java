/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.dao.dto.Sighting;
import com.sg.herosightings.dao.dto.SuperPower;
import com.sg.herosightings.service.HeroSightingService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping("/hero")
public class HeroController {

    private HeroSightingService service;

    @Inject
    public HeroController(HeroSightingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getHeroes", method = RequestMethod.GET)
    public String getHeroes(Model model) {
        List<Hero> heroes = service.getAllHeroes();
        List<SuperPower> powers = service.getAllPowers();
        List<Organization> orgs = service.getAllOrgs();
        List<Location> locals = service.getAllLocals();
        model.addAttribute("heroes", heroes);
        model.addAttribute("orgs", orgs);
        model.addAttribute("powers", powers);
        model.addAttribute("locals", locals);
        return "hero";
    }

    @RequestMapping(value = "/addHero", method = RequestMethod.POST)
    public String addHero(HttpServletRequest request) {
        Hero hero = new Hero();
        hero.setHeroName(request.getParameter("heroName"));
        hero.setDescription(request.getParameter("heroDescription"));
        String isGood = request.getParameter("isGood");
        if (isGood.equals("good")) {
            hero.setGoodHero(true);
        } else {
            hero.setGoodHero(false);
        }
        String[] organizationList = request.getParameterValues("organizations");
        if (service.validateIdsNotNull(organizationList)) {
            List<Organization> orgs = new ArrayList();
            for (int i = 0; i < organizationList.length; i++) {
                int orgId = Integer.parseInt(organizationList[i]);
                Organization org = service.getOrg(orgId);
                orgs.add(org);
            }
            hero.setOrgs(orgs);
        }
        String[] powersList = request.getParameterValues("superpowers");
        if (service.validateIdsNotNull(powersList)) {
            Map<SuperPower, Integer> powerMap = new HashMap();
            for (int i = 0; i < powersList.length; i++) {
                int powerId = Integer.parseInt(powersList[i]);
                String stringLevel = request.getParameter("powerId-" + powersList[i]);
                if (stringLevel != null) {
                    int powerLevel = Integer.parseInt(stringLevel);
                    SuperPower power = service.getPower(powerId);
                    powerMap.put(power, powerLevel);
                }
            }
            hero.setPowers(powerMap);
        }
        String[] sightingList = request.getParameterValues("sighitngLocations");
        List<Sighting> sights = new ArrayList();
        if (service.validateIdsNotNull(sightingList)) {
            List<Hero> heroes = new ArrayList();
            heroes.add(hero);
            for (int i = 0; i < sightingList.length; i++) {
                Sighting sight = new Sighting();
                int localId = Integer.parseInt(sightingList[i]);
                Location local = service.getLocation(localId);
                String dateString = request.getParameter("localId-" + sightingList[i]);
                if (dateString != null) {
                    LocalDate date = LocalDate.parse(dateString);
                    sight.setDate(date);
                    sight.setLocation(local);
                    sight.setHeroes(heroes);
                    sights.add(sight);
                }
            }
        }
        if (request.getParameter("addSubmit") != null) {
            service.createHero(hero);
            for (Sighting sight : sights) {
                service.createSighting(sight);
            }
        } else if (request.getParameter("updateSubmit") != null) {
            hero.setHeroId(Integer.parseInt(request.getParameter("hiddenId")));
            service.updateHero(hero);
            for (Sighting sight : sights) {
                service.createSighting(sight);
            }
        }
        return "redirect:/hero/getHeroes";
    }

    @RequestMapping(value = "/deleteHero", method = RequestMethod.GET)
    public String deleteHero(HttpServletRequest request) {
        service.deleteHero(Integer.parseInt(request.getParameter("heroId")));
        return "redirect:/hero/getHeroes";
    }

    @RequestMapping(value = "/hero/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Hero getHero(@PathVariable("id") int heroId) {
        return service.getHero(heroId);
    }

}
