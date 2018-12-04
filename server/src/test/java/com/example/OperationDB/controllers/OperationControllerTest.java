package com.example.OperationDB.controllers;

import com.example.OperationDB.model.Operation;
import com.example.OperationDB.service.OperationService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OperationController.class)
public class OperationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OperationService operationService;

    @Test
    public void givenOperation_whenGetOperations_thenReturnJsonArray() throws Exception {

        Operation operation0 = new Operation(
                null, LocalDate.now(), LocalDate.now(),
                "test type", "test description",
                new BigDecimal("100"), "PLN",
                new BigDecimal("10000"), "PLN");
        Operation operation1 = new Operation(
                null, LocalDate.now(), LocalDate.now(),
                "test type", "test description",
                new BigDecimal("100"), "PLN",
                new BigDecimal("10000"), "PLN");

        List<Operation> operations = Arrays.asList(operation0, operation1);

        BDDMockito.given(operationService.findAll()).willReturn(operations);

        mvc.perform(get("/operations/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", Matchers.is(operation0.getDescription())))
                .andExpect(jsonPath("$[1].endingBalanceCurr", Matchers.is(operation1.getEndingBalanceCurr())))
                .andExpect(jsonPath("$[1].endingBalance", Matchers.is(Integer.parseInt(operation1.getEndingBalance().toString()))));
    }
}