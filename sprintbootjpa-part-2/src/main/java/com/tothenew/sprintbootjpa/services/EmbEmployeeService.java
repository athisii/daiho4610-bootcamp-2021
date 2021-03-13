package com.tothenew.sprintbootjpa.services;

import com.tothenew.sprintbootjpa.entities.EmbEmployee;
import com.tothenew.sprintbootjpa.repos.EmbEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbEmployeeService {

    @Autowired
    private EmbEmployeeRepository embEmployeeRepository;

    public List<EmbEmployee> getAllEmbEmployees() {
        return embEmployeeRepository.findAll();
    }

    public void save(EmbEmployee embEmployee) {
        embEmployeeRepository.save(embEmployee);
    }
}
