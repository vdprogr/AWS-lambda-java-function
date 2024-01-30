package com.vdprog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class App {

    public String handler(String jsonData) throws Exception {
        // Sample JSON data (replace it with your actual JSON data)
        //String jsonData = "{\"name\": \"John\", \"age\": 30}, {\"name\": \"Alice\", \"age\": 25}";

        // Convert JSON data to CSV
        String csvData = convertJsonToCsv(jsonData);
        System.out.println(csvData);
        // Output CSV data
        return csvData;
    }

    private static String convertJsonToCsv(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON data
        JsonNode rootNode = objectMapper.readTree("[" + jsonData + "]");

        // Create a StringWriter to write CSV data
        try (Writer writer = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(writer)) {

            // Write JSON keys as header
            csvWriter.writeNext(getKeys(rootNode));

            // Write JSON values to CSV
            for (JsonNode node : rootNode) {
                csvWriter.writeNext(getValues(node));
            }

            // Flush the writer
            writer.flush();

            // Return CSV data as a string
            return writer.toString();
        }
    }

    private static String[] getKeys(JsonNode node) {
        Iterator<String> fieldNames = node.get(0).fieldNames();
        List<String> keysList = new ArrayList<>();
        fieldNames.forEachRemaining(keysList::add);
        return keysList.toArray(new String[keysList.size()]);
    }

    private static String[] getValues(JsonNode node) {
        String[] values = new String[node.size()];
        int index = 0;
        for (JsonNode childNode : node) {
            values[index++] = childNode.asText();
        }
        return values;
    }
}