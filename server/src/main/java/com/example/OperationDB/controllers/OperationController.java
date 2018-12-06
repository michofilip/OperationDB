package com.example.OperationDB.controllers;

import com.example.OperationDB.model.Operation;
import com.example.OperationDB.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/operations")
@Api(value = "OperationControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OperationController {

    private OperationService operationService;

    @GetMapping
//    @ApiOperation("Gets operations")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Collection.class)})
    public Collection<Operation> getAll() {
        return operationService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Gets operation by id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Operation.class)})
    public Operation getOne(@PathVariable Long id) {
        return operationService.findOne(id);
    }

    @PostMapping
    public void createFromFile(MultipartFile file) {
        operationService.saveFromXMLFile(file);
    }

}
