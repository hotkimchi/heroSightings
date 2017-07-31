/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings;

import com.sg.herosightings.dao.dto.Hero;
import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.service.HeroSightingService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import static jdk.nashorn.internal.runtime.Debug.id;
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
@RequestMapping("/organization")
public class OrganizationController {

    private HeroSightingService service;

    @Inject
    public OrganizationController(HeroSightingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getOrgs", method = RequestMethod.GET)
    public String getOrgs(Model model) {
        List<Organization> orgs = service.getAllOrgs();
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locals = service.getAllLocals();
        model.addAttribute("orgs", orgs);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locals", locals);
        return "organization";
    }

    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public String addOrg(HttpServletRequest request) {
        Organization org = new Organization();
        org.setOrganizationName(request.getParameter("orgName"));
        org.setDescription(request.getParameter("orgDescription"));
        org.setMotto(request.getParameter("orgMotto"));
        org.setEmail(request.getParameter("orgEmail"));
        org.setPhone(request.getParameter("orgPhone"));
        org.setOtherContactInfo(request.getParameter("orgOtherContactInfo"));
        String[] orgHeroes = request.getParameterValues("orgHeroes");
        if (service.validateIdsNotNull(orgHeroes)) {
            List<Hero> heroes = new ArrayList();
            for (int i = 0; i < orgHeroes.length; i++) {
                int heroId = Integer.parseInt(orgHeroes[i]);
                Hero hero = service.getHero(heroId);
                heroes.add(hero);
            }
            org.setHeroes(heroes);
        }
        String[] orgLocals = request.getParameterValues("orgLocals");
        if (service.validateIdsNotNull(orgLocals)) {
            List<Location> locals = new ArrayList();
            for (int i = 0; i < orgLocals.length; i++) {
                int localId = Integer.parseInt(orgLocals[i]);
                Location local = service.getLocation(localId);
                locals.add(local);
            }
            org.setLocals(locals);
        }
        if (request.getParameter("addSubmit") != null) {
            service.createOrg(org);
        } else if (request.getParameter("updateSubmit") != null){
            org.setOrganizationId(Integer.parseInt(request.getParameter("hiddenId")));
            service.updateOrg(org);
        }
        return "redirect:/organization/getOrgs";
    }

    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    public String deleteOrg(HttpServletRequest request) {
        service.deleteOrg(Integer.parseInt(request.getParameter("orgId")));
        return "redirect:/organization/getOrgs";
    }

    @RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Organization getOrg(@PathVariable("id") int orgId) {
        return service.getOrg(orgId);
    }
}
