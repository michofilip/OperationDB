package com.example.OperationDB.service;

import com.example.OperationDB.model.Operation;
import com.example.OperationDB.repository.OperationRepository;
import com.example.OperationDB.service.parser.XMLFileParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@AllArgsConstructor
public class OperationService {

    private OperationRepository operationRepository;

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public Operation findOne(Long id) {
        return operationRepository.findOne(id);
    }

    public void saveFromXMLFile(MultipartFile file) {
        List<Operation> operations = XMLFileParser.parseFromXMLFile(file);
        operationRepository.save(operations);
    }
}
