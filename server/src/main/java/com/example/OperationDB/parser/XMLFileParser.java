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
                    } catch (ParseException ignored) {
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException ignored) {
        }

        return operations;
    }

    private static Operation parseElement(Element element) throws ParseException {
        String execDateStr = getTextContentOfElement(element, "exec-date");
        String orderDateStr = getTextContentOfElement(element, "order-date");
        String type = getTextContentOfElement(element, "type");
        String description = getTextContentOfElement(element, "description");
        String amountStr = getTextContentOfElement(element, "amount");
        String amountCurr = getTextContentOfElementAttribute(element, "amount", "curr");
        String endingBalanceStr = getTextContentOfElement(element, "ending-balance");
        String endingBalanceCurr = getTextContentOfElementAttribute(element, "ending-balance", "curr");

        LocalDate execDate = parseLocalDate(execDateStr);
        LocalDate orderDate = parseLocalDate(orderDateStr);

        BigDecimal amount = parseBigDecimal(amountStr);
        BigDecimal endingBalance = parseBigDecimal(endingBalanceStr);

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
    }

    private static LocalDate parseLocalDate(String dateStr) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (dateStr == null) {
            throw new ParseException();
        }

        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException();
        }
    }

    private static BigDecimal parseBigDecimal(String bigDecimalStr) throws ParseException {
        try {
            return new BigDecimal(bigDecimalStr);
        } catch (NumberFormatException e) {
            throw new ParseException();
        }
    }

    private static String getTextContentOfElement(Element element, String elementName) throws ParseException {
        NodeList nodes = element.getElementsByTagName(elementName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        } else {
            throw new ParseException();
        }
    }

    private static String getTextContentOfElementAttribute(Element element, String elementName, String attributeName) throws ParseException {
        NodeList nodes = element.getElementsByTagName(elementName);
        if (nodes.getLength() > 0) {
            Node attribute = nodes.item(0).getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                return attribute.getTextContent();
            } else {
                throw new ParseException();
            }
        } else {
            throw new ParseException();
        }
    }


}
