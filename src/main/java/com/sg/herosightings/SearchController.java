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
import java.util.Set;
import javax.inject.Inject;
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
//@RequestMapping({"/hero","/power","/organization", "/location", "/sighting", "/"})
public class SearchController {

    private HeroSightingService service;

    @Inject
    public SearchController(HeroSightingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/search/heroes", method = RequestMethod.GET)
    public String getHeroes(HttpServletRequest request, Model model) {
        List<Hero> heroes = new ArrayList();
        String searchBy = request.getParameter("byCategory");
        int search = Integer.parseInt(searchBy);
        String searchInput = request.getParameter("searchInput");
        switch (search) {
            case 0:
                break;
            case 1:
                List<SuperPower> powers = service.getAllPowers();
                for (SuperPower power : powers) {
                    if (power.getPowerName().equals(searchInput)) {
                        heroes =  service.getHeroesByPower(power);
                    }
                }
                break;
            case 2:
                List<Organization> orgs = service.getAllOrgs();
                for (Organization org : orgs) {
                    if (org.getOrganizationName().equals(searchInput)) {
                        heroes = service.getHeroesByOrganization(org);
                    }
                }
                break;
            case 3:
                List<Location> locals = service.getAllLocals();
                for (Location local : locals) {
                    if (local.getLocalName().equals(searchInput)) {
                        heroes =  service.getHeroesByLocation(local);
                    }
                }
                break;
            case 4:
                List<Sighting> sights = service.getAllSightings();
                for (Sighting sight : sights) {
                    if (sight.getDate().equals(LocalDate.parse(searchInput))) {
                        heroes =  service.getHeroesByDate(LocalDate.parse(searchInput));
                    }
                }
                break;
        }
        model.addAttribute("heroes", heroes);
        return "search";
    }
    
    @RequestMapping(value = "/search/powers", method = RequestMethod.GET)
    public String getPowers(HttpServletRequest request, Model model){
        Map<SuperPower, Integer> powers = new HashMap();
        String searchBy = request.getParameter("byCategory");
        int search = Integer.parseInt(searchBy);
        String searchInput = request.getParameter("searchInput");
        switch (search) {
            case 0:
                break;
            case 1:
                List<Hero> heroes = service.getAllHeroes();
                for (Hero hero : heroes) {
                    if (hero.getHeroName().equals(searchInput)) {
                        powers = service.getPowersByHero(hero);
                    }
                }
                model.addAttribute("powers", powers);
                break;
            case 2:
                Set<SuperPower> powerfulPowers = service.getPowersByLevel(search);
                model.addAttribute("powerfulPowers", powerfulPowers);
                break;
        }
        return "search";
    }
    
    @RequestMapping(value = "/search/organizations", method = RequestMethod.GET)
    public String getOrgs(HttpServletRequest request, Model model) {
        List<Organization> orgs = new ArrayList();
        String searchBy = request.getParameter("byCategory");
        int search = Integer.parseInt(searchBy);
        String searchInput = request.getParameter("searchInput");
        switch (search) {
            case 0:
                break;
            case 1:
                List<Hero> heroes = service.getAllHeroes();
                for (Hero hero : heroes) {
                    if (hero.getHeroName().equals(searchInput)){
                        orgs = service.getOrgsByHeroes(hero);
                    }
                }
                break;
            case 2:
                List<Location> locals = service.getAllLocals();
                for (Location local : locals) {
                    if (local.getLocalName().equals(searchInput)){
                        Organization org = service.getOrgByLocation(local);
                        orgs.add(org);
                    }
                }
                break;
        }
        model.addAttribute("orgs", orgs);
        return "search";
    }
    
    @RequestMapping(value = "/search/locations", method = RequestMethod.GET)
    public String getLocals(HttpServletRequest request, Model model) {
        List<Location> locals = new ArrayList();
        String searchBy = request.getParameter("byCategory");
        int search = Integer.parseInt(searchBy);
        String searchInput = request.getParameter("searchInput");
        switch (search) {
            case 0:
                break;
            case 1:
                List<Hero> heroes = service.getAllHeroes();
                for (Hero hero : heroes) {
                    if (hero.getHeroName().equals(searchInput)) {
                        locals = service.getLocalsByHero(hero);
                    }
                }
                break;
            case 2:
                List<Sighting> sights = service.getAllSightings();
                for (Sighting sight : sights) {
                    if (sight.getDate().equals(LocalDate.parse(searchInput))){
                        locals = service.getLocalsByDate(LocalDate.parse(searchInput));
                    }
                }
                break;
            case 3:
                List<Organization> orgs = service.getAllOrgs();
                for (Organization org : orgs) {
                    if (org.getOrganizationName().equals(searchInput)){
                        locals = service.getLocalsByOrg(org);
                    }
                }
                break;
        }
        model.addAttribute("locals", locals);
        return "search";
    }
    
    @RequestMapping(value = "/search/sightings", method = RequestMethod.GET)
    public String getSightings(HttpServletRequest request, Model model) {
        List<Sighting> sights = new ArrayList();
        String searchBy = request.getParameter("byCategory");
        int search = Integer.parseInt(searchBy);
        String searchInput = request.getParameter("searchInput");
        switch (search) {
            case 0:
                break;
            case 1:
                List<Hero> heroes = service.getAllHeroes();
                for (Hero hero : heroes) {
                    if (hero.getHeroName().equals(searchInput)){
                        sights = service.getSightingsByHero(hero);
                    }
                }
                break;
            case 2:
                List<Location> locals = service.getAllLocals();
                for (Location local : locals) {
                    if (local.getLocalName().equals(searchInput)){
                        sights = service.getSightingsByLocal(local);
                    }
                }
                break;
            case 3:
                sights = service.getSightingsByDate(LocalDate.parse(searchInput));
                break;
        }
        model.addAttribute("sights", sights);
        return "search";
    }
}
