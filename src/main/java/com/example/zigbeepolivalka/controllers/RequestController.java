package com.example.zigbeepolivalka.controllers;

import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.AbstractMode;
import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.domain.MoistureMode;
import com.example.zigbeepolivalka.domain.TimeMode;
import com.example.zigbeepolivalka.domain.WateringMode;
import com.example.zigbeepolivalka.exceptions.NoSuchFlowerException;
import com.example.zigbeepolivalka.services.ZigBeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@Controller
public class RequestController {

    private final ZigBeeService service;

    public RequestController(ZigBeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String start(Model model){
        return "start";
    }

    @GetMapping("/flowers")
    public String flowerList(Model model){
        model.addAttribute("flowerList", service.getFlowers());
        return "main";
    }

    @GetMapping("/{id}")
    public String getCurrentFlower(Model model, @PathVariable String id){
        try {
            model.addAttribute("flower", service.getFlowerById(id));
            return "settings";
        } catch (NoSuchFlowerException exception) {
            model.addAttribute("message", "Chosen flower isn't find. Please, try again");
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String updateCurrentFlower(@RequestParam int watering_mode,
                                      @RequestParam int levels,
                                      @RequestParam int days,
                                      @RequestParam int hours,
                                      @RequestParam int min,
                                      @RequestParam String name,
                                      @RequestParam byte valve_open_time,
                                      @PathVariable String id,
                                      Model model)
    {
        System.out.println(valve_open_time);
        try {
            WateringMode mode;
            if (watering_mode == 1) {
                mode = new MoistureMode();
                mode.setModeParameter(levels);
            } else {
                mode = new TimeMode();
                int time = days * 60 * 24 +
                           hours * 60 +
                           min;
                mode.setModeParameter(time);
            }
            Flower newFlower = new Flower(name, mode);
            newFlower.setValveOpenTime(valve_open_time);
            service.updateFlower(id, newFlower);
        } catch (NoSuchFlowerException | XBeeException exception){
            model.addAttribute("message", "Chosen flower isn't find or XBee has troubles with connection. Please, try again");
            return "error";
        }
        return "redirect:/flowers";
    }

    @GetMapping("/search")
    public String findNewFlowers(Model model){
        try {
            model.addAttribute("findFlowers", service.getAvailableFlowers());
        } catch (XBeeException e) {
            model.addAttribute("message", "XBee has troubles with connection. Please, try again");
            return "error";
        }
        return "search";
    }

    @PostMapping("/save")
    public String saveNewFlowers(@RequestParam(required = false) Map<String, String> body, Model model){
        System.out.println(body);
        service.selectFlowers(body.keySet());
        return flowerList(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteFlower(@PathVariable String id, Model model) {
        try {
            service.removeFlower(id);
        } catch (XBeeException | NoSuchFlowerException e) {
            model.addAttribute("message", "Chosen flower isn't found or XBee has troubles with connection. Please, try again");
            return "error";
        }
        return "redirect:/flowers";
    }
}
