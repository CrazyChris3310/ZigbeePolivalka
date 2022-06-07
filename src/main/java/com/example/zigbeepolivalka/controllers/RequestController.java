package com.example.zigbeepolivalka.controllers;

import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.domain.MoistureMode;
import com.example.zigbeepolivalka.domain.TimeMode;
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

    @Autowired
    ZigBeeService service;

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
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String updateCurrentFlower(@RequestParam Map<String, String> body, Model model, @PathVariable String id){
        System.out.println(body);
        try {
            if (Objects.equals(body.get("watering_mode"), "1")) {
                MoistureMode mode = new MoistureMode();
                mode.setModeParameter(Integer.parseInt(body.get("levels")));
                service.updateFlower(id, new Flower(body.get("name"), mode));
            } else {
                TimeMode mode = new TimeMode();
                int time = Integer.parseInt(body.get("days")) * 60 * 24 + Integer.parseInt(body.get("hours")) * 60 + Integer.parseInt(body.get("min"));
                mode.setModeParameter(time);
                service.updateFlower(id, new Flower(body.get("name"), mode));
            }
        } catch (NoSuchFlowerException exception){
            return "error";
        }
        return flowerList(model);
    }

    @GetMapping("/search")
    public String findNewFlowers(Model model){
        try {
            model.addAttribute("findFlowers", service.getAvailableFlowers());
        } catch (XBeeException e) {
            System.out.println("BRUH");
        }
        return "search";
    }

    @PostMapping("/save")
    public String saveNewFlowers(@RequestBody(required = false) Map<String, String> body, Model model){
        System.out.println(body);
        service.selectFlowers(body.keySet());
        return flowerList(model);
    }
}
