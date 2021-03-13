package com.tothenew.sprintbootjpa.controllers;

import com.tothenew.sprintbootjpa.entities.EmbEmployee;
import com.tothenew.sprintbootjpa.services.EmbEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/cm")
@RestController
public class EmbEmployeeController {
    @Autowired
    private EmbEmployeeService embEmployeeService;

    @GetMapping
    public List<EmbEmployee> retrieveAllEmbEmployees() {
        return embEmployeeService.getAllEmbEmployees();
    }

    @PostMapping
    public void createEmbEmployee(@RequestBody EmbEmployee embEmployee) {
        embEmployeeService.save(embEmployee);
    }

}
