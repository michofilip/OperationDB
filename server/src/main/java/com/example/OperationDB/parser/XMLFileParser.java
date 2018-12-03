package com.example.OperationDB.parser;

import com.example.OperationDB.model.Operation;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class XMLFileParser {

    public static List<Operation> parseFromXMLFile(MultipartFile file) {
        List<Operation> operations = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.getInputStream());

            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("operation");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    try {
                        Operation operation = parseElement(element);
                        operations.add(operation);
                    } catch (ParseException e) {
//                        e.printStackTrace();
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e) {
//            e.printStackTrace();
        }

        return operations;
    }

    private static Operation parseElement(Element element) throws ParseException {
        try {
            String execDateStr = element.getElementsByTagName("exec-date").item(0).getTextContent();
            String orderDateStr = element.getElementsByTagName("order-date").item(0).getTextContent();
            String type = element.getElementsByTagName("type").item(0).getTextContent();
            String description = element.getElementsByTagName("description").item(0).getTextContent();
            String amountStr = element.getElementsByTagName("amount").item(0).getTextContent();
            String amountCurr = element.getElementsByTagName("amount").item(0).getAttributes().getNamedItem("curr").getTextContent();
            String endingBalanceStr = element.getElementsByTagName("ending-balance").item(0).getTextContent();
            String endingBalanceCurr = element.getElementsByTagName("ending-balance").item(0).getAttributes().getNamedItem("curr").getTextContent();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (execDateStr == null || orderDateStr == null) {
                throw new ParseException();
            }
            LocalDate execDate;
            LocalDate orderDate;
            try {
                execDate = LocalDate.parse(execDateStr, formatter);
                orderDate = LocalDate.parse(orderDateStr, formatter);
            } catch (DateTimeParseException e) {
                throw new ParseException();
            }

            BigDecimal amount;
            BigDecimal endingBalance;
            try {
                amount = new BigDecimal(amountStr);
                endingBalance = new BigDecimal(endingBalanceStr);
            } catch (NumberFormatException e) {
                throw new ParseException();
            }

            Operation operation = new Operation();

            operation.setExecDate(execDate);
            operation.setOrderDate(orderDate);
            operation.setType(type);
            operation.setDescription(description);
            operation.setAmount(amount);
            operation.setAmountCurr(amountCurr);
            operation.setEndingBalance(endingBalance);
            operation.setEndingBalanceCurr(endingBalanceCurr);

            return operation;
        } catch (NullPointerException e) {
            throw new ParseException();
        }
    }
}
