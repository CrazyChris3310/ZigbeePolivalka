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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
public class RequestController {

    @Autowired
    ZigBeeService service;

    @GetMapping("/")
    public String start(Model model){
        //TODO Убрать строки добавления цветков
//        MoistureMode mode = new MoistureMode();
//        mode.setDuration(50);
//        Flower rose = new Flower("Rose", mode);
//        rose.setId(1);
//        Flower sunflower = new Flower("SunFlower", new TimeMode());
//        sunflower.setId(2);
//        Flower butterCup = new Flower("ButterCup", new MoistureMode());
//        butterCup.setId(3);
//        service.addFlower(rose);
//        service.addFlower(sunflower);
//        service.addFlower(butterCup);
        return "start";
    }

    @GetMapping("/flowers")
    public String flowerList(Model model){
        model.addAttribute("flowerList", service.getFlowers());
        return "main";
    }

    @GetMapping("/flowers/{id}")
    public String getCurrentFlower(Model model, @PathVariable String id){
        try {
            model.addAttribute("flower", service.getFlowerById(id));
            return "/parts/settings";
        } catch (NoSuchFlowerException exception) {
            return "/parts/error";
        }
    }

    @PostMapping("/flowers/{id}")
    public String updateCurrentFlower(@RequestParam String body, Model model, @PathVariable String id){
        Map<String, String> parsedBody = bodyToMap(body);
        System.out.println(body);
        try {
            if (Objects.equals(parsedBody.get("watering_mode"), "1")) {
                MoistureMode mode = new MoistureMode();
                mode.setModeParameter(Integer.parseInt(parsedBody.get("levels")));
                service.updateFlower(id, new Flower(parsedBody.get("name"), mode));
            } else {
                TimeMode mode = new TimeMode();
                int time = Integer.parseInt(parsedBody.get("days")) * 3600 * 24
                           + Integer.parseInt(parsedBody.get("hours")) * 3600
                           + Integer.parseInt(parsedBody.get("min")) * 60;
                mode.setModeParameter(time);
                service.updateFlower(id, new Flower(parsedBody.get("name"), mode));
            }
        } catch (NoSuchFlowerException | XBeeException exception){
            return "/parts/error";
        }
        return flowerList(model);
    }

    public Map<String, String> bodyToMap (String body) {
        Map<String, String> result = new HashMap<>();
        String[] values = body.split("&");
        for (String value : values) {
            String[] pair = value.split("=");
            if (pair.length == 2) {
                result.put(pair[0], pair[1]);
            }
        }
        return result;
    }
}
