package com.amaneth.ticketsystem.controller;

import com.amaneth.ticketsystem.model.Configuration;
import com.amaneth.ticketsystem.service.ConfigurationServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
@CrossOrigin("http://localhost:3000")
public class ConfigurationController {

    private final ConfigurationServiceImplementation configService;

    public ConfigurationController(ConfigurationServiceImplementation configService) {
        this.configService = configService;
    }

    @PostMapping("/save")
//@RequestBody= Spring Boot converts this JSON into a Configuration object and passes it as the configuration parameter.
    public Configuration saveConfiguration(@RequestBody Configuration configuration) {
        return configService.saveConfiguration(configuration);
    }

    @GetMapping("/load")
    public Configuration loadConfiguration(@RequestParam String fileName) {
        return configService.loadConfigurationFromGsonFile(fileName);
    }

    @GetMapping("/list")
    public List<String> listAllConfigurations() {
        return configService.listAllConfigurations();
    }
}
