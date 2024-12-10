package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConfigurationServiceImplementation implements ConfigurationService {

    private static final Logger logger = Logger.getLogger(ConfigurationServiceImplementation.class.getName());
    private static final String CONFIG_DIRECTORY = "./configurations"; // Directory to store JSON files

    public ConfigurationServiceImplementation() {
        File directory = new File(CONFIG_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    @Override
    public Configuration saveConfiguration(Configuration configuration) {
        saveConfigurationToGsonFile(configuration);
        return configuration;
    }

    public void saveConfigurationToGsonFile(Configuration configuration) {
        String fileName = String.format("%s.json", configuration.getEventName());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(configuration, writer);
            System.out.println("Configuration settings are successfully saved to " + fileName);
            logger.info("Configuration settings saved to " + fileName);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            logger.log(Level.SEVERE, "Failed to save configuration to " + fileName, e);
        }
    }

    public Configuration loadConfigurationFromGsonFile(String fileName) {
        Gson gson = new Gson();
        Configuration configuration = new Configuration();
        String filePath = String.format("/%s", fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Configuration config = gson.fromJson(reader, Configuration.class);
            System.out.println("Configuration settings successfully loaded from " + filePath);
            logger.info("Configuration settings loaded from " + filePath);
            return config;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: The file " + fileName + " does not exist.");
            logger.warning("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred while reading the file.");
            logger.log(Level.SEVERE, "Error reading the file " + filePath, e);
        }
        return null;
    }

    public List<String> listAllConfigurations() {
        File folder = new File(CONFIG_DIRECTORY);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        List<String> jsonFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                jsonFiles.add(file.getName());
            }
        }else {
            System.out.println("No Configurations Found.");
        }
        return jsonFiles;
    }

}
