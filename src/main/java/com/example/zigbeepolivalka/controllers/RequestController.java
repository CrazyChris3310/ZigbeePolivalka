package com.example.zigbeepolivalka.controllers;

import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.domain.MoistureMode;
import com.example.zigbeepolivalka.exceptions.NoSuchFlowerException;
import com.example.zigbeepolivalka.services.ZigBeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class RequestController {

    @Autowired
    ZigBeeService service;

    @GetMapping("/")
    public String start(Model model){
        //TODO Убрать строки добавления цветков
        Flower rose = new Flower("Rose", new MoistureMode());
        rose.setId(1);
        Flower sunflower = new Flower("SunFlower", new MoistureMode());
        sunflower.setId(2);
        Flower butterCup = new Flower("ButterCup", new MoistureMode());
        butterCup.setId(3);
        service.addFlower(rose);
        service.addFlower(sunflower);
        service.addFlower(butterCup);
        return "start";
    }

    @GetMapping("/flowers")
    public String flowerList(Model model){
        model.addAttribute("flowerList", service.getConnectedDevices());
        return "main";
    }

    @GetMapping("/flowers/{id}")
    public String currentFlower(Model model, @PathVariable int id){
        try {
            model.addAttribute("flower", service.getFlowerById(id));
            return "/parts/settings";
        } catch (NoSuchFlowerException exception) {
            return "/parts/error";
        }
    }
}
