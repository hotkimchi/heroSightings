/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings;

import com.sg.herosightings.dao.dto.SuperPower;
import com.sg.herosightings.service.HeroSightingService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@CrossOrigin
@Controller
@RequestMapping("/power")
public class PowerController {
    
    HeroSightingService service;
    
    @Inject
    public PowerController(HeroSightingService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "/getPowers", method = RequestMethod.GET)
    public String getPowers (Model model) {
        List<SuperPower> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        return "power";
    }
    
    @RequestMapping(value = "/addPower", method = RequestMethod.POST)
    public String addOrUpdatePower(HttpServletRequest request) {
        SuperPower power = new SuperPower();
        power.setPowerName(request.getParameter("powerName"));
        power.setDescription(request.getParameter("powerDescription"));
        if (request.getParameter("addSubmit") != null) {
            service.createPower(power);
        } else if (request.getParameter("updateSubmit") != null) {
            power.setSuperPowerId(Integer.parseInt(request.getParameter("hiddenId")));
            service.updatePower(power);
        }
        return "redirect:/power/getPowers";
    }
    
    @RequestMapping(value = "/deletePower", method = RequestMethod.GET)
    public String deletePower(HttpServletRequest request) {
        service.deletePower(Integer.parseInt(request.getParameter("powerId")));
        return "redirect:/power/getPowers";
    }
    
    @RequestMapping(value = "/power/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SuperPower getSuperPower(@PathVariable("id") int id){
        return service.getPower(id);
    }
}
