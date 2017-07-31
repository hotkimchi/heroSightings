/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings;

import com.sg.herosightings.dao.dto.Location;
import com.sg.herosightings.dao.dto.Organization;
import com.sg.herosightings.service.HeroSightingService;
import java.util.List;
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
@RequestMapping("/location")
public class LocationController {

    private HeroSightingService service;

    @Inject
    public LocationController(HeroSightingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getLocals", method = RequestMethod.GET)
    public String getLocals(Model model) {
        List<Location> locals = service.getAllLocals();
        List<Organization> orgs = service.getAllOrgs();
        model.addAttribute("orgs", orgs);
        model.addAttribute("locals", locals);
        return "location";
    }

    @RequestMapping(value = "/addLocal", method = RequestMethod.POST)
    public String addLocal(HttpServletRequest request) {
        Location local = new Location();
        local.setLocalName(request.getParameter("localName"));
        local.setAddress(request.getParameter("localAddress"));
        local.setDescription(request.getParameter("localDescription"));
        String latString = request.getParameter("localLatitude");
        local.setLatitude(Float.parseFloat(latString));
        String lonString = request.getParameter("localLongitude");
        local.setLongitude(Float.parseFloat(lonString));
        String orgIdString = request.getParameter("localOrg");
        int orgId = Integer.parseInt(orgIdString);
        if (orgId != 0) {
            Organization localOrg = service.getOrg(orgId);
            local.setOrg(localOrg);
        }
        if (request.getParameter("addSubmit") != null) {
            service.createLocation(local);
        } else if (request.getParameter("updateSubmit") != null){
            local.setLocationId(Integer.parseInt(request.getParameter("hiddenId")));
            service.updateLocal(local);
        }
        return "redirect:/location/getLocals";
    }

    @RequestMapping(value = "/deleteLocal", method = RequestMethod.GET)
    public String deleteLocal(HttpServletRequest request) {
        service.deleteLocal(Integer.parseInt(request.getParameter("localId")));
        return "redirect:/location/getLocals";
    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Location getLocal(@PathVariable("id") int localId) {
        return service.getLocation(localId);
    }
}
