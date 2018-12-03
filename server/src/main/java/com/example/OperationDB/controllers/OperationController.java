package com.example.OperationDB.controllers;

import com.example.OperationDB.model.Operation;
import com.example.OperationDB.parser.XMLFileParser;
import com.example.OperationDB.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
public class OperationController {

    private OperationRepository operationRepository;

    @GetMapping("/")
    public Collection<Operation> getAll() {
        return operationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Operation getOne(@PathVariable Long id) {
        return operationRepository.findOne(id);
    }

    @PostMapping("/")
    public void createFromFile(MultipartFile file) {
        List<Operation> operations = XMLFileParser.parseFromXMLFile(file);
        operationRepository.save(operations);
    }

}
