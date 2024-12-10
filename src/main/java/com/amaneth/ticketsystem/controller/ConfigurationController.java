package com.amaneth.ticketsystem.controller;

import com.amaneth.ticketsystem.model.Configuration;
import com.amaneth.ticketsystem.service.ConfigurationServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    private final ConfigurationServiceImplementation configService;


    public ConfigurationController(ConfigurationServiceImplementation configService) {
        this.configService = configService;
    }

    @PostMapping("/save")
    public Configuration saveConfiguration(@RequestBody Configuration configuration) {
        return configService.saveConfiguration(configuration);

    }

    @GetMapping("/load")
    public String loadConfiguration(@RequestParam String fileName) {
         configService.loadConfigurationFromGsonFile(fileName);

    return "filename: " + fileName;
    }

    @GetMapping("/list")
    public List<String> listAllConfigurations() {
        return configService.listAllConfigurations();
    }


}
