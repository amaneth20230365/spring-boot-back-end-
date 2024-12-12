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
    private static final String CONFIG_DIRECTORY = "configurations"; // Directory to store JSON files

    private Configuration currentConfiguration; // Store the currently loaded configuration

    public ConfigurationServiceImplementation() {
        File directory = new File(CONFIG_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public Configuration saveConfiguration(Configuration configuration) {
        saveConfigurationToGsonFile(configuration);
        this.currentConfiguration = configuration; // Update the current configuration
        return configuration;
    }

    public void saveConfigurationToGsonFile(Configuration configuration) {
        String fileName = String.format("%s.json", configuration.getEventName());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(new File(CONFIG_DIRECTORY, fileName))) {
            gson.toJson(configuration, writer);
            logger.info("Configuration settings saved to " + fileName);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save configuration to " + fileName, e);
        }
    }

    public Configuration loadConfigurationFromGsonFile(String fileName) {
        File file = new File(CONFIG_DIRECTORY,fileName); // Load from the configurations directory
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            this.currentConfiguration = gson.fromJson(reader, Configuration.class); // Update the current configuration
            logger.info("Configuration settings loaded from " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            logger.warning("File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the file " + file.getAbsolutePath(), e);
        }
        return this.currentConfiguration;
    }

    public Configuration getCurrentConfiguration() {
        return this.currentConfiguration;
    }

    public List<String> listAllConfigurations() {
        File folder = new File(CONFIG_DIRECTORY);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        List<String> jsonFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                jsonFiles.add(file.getName());
            }
        }
        return jsonFiles;
    }
}
