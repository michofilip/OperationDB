package com.example.OperationDB.controllers;

import com.example.OperationDB.model.Operation;
import com.example.OperationDB.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
public class OperationController {

    private OperationService operationService;

    @GetMapping("/")
    public Collection<Operation> getAll() {
        return operationService.findAll();
    }

    @GetMapping("/{id}")
    public Operation getOne(@PathVariable Long id) {
        return operationService.findOne(id);
    }

    @PostMapping("/")
    public void createFromFile(MultipartFile file) {
        operationService.saveFromXMLFile(file);
    }

}
