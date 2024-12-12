package com.amaneth.ticketsystem.controller;

import com.amaneth.ticketsystem.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simulationLogs")
@CrossOrigin("http://localhost:3000")
public class SimulationLogs {
    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping("/getlogs")
    public List<String> getLogs() {
        return ticketPoolService.getSimulationLogs().stream().collect(Collectors.toList());
    }
}
