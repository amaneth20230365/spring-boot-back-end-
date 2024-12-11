package com.amaneth.ticketsystem.controller;

import com.amaneth.ticketsystem.model.Configuration;
import com.amaneth.ticketsystem.repository.TicketPoolRepository;
import com.amaneth.ticketsystem.service.ConfigurationService;
import com.amaneth.ticketsystem.service.CustomerService;
import com.amaneth.ticketsystem.service.TicketPoolService;
import com.amaneth.ticketsystem.service.VendorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/threadController")
public class ThreadController {
    private final ConfigurationService configurationServiceImplementation;
    private final TicketPoolRepository ticketPoolRepository;
    private final TicketPoolService ticketPoolService;

    // gets the full name with package name


    public ThreadController(ConfigurationService configService, TicketPoolRepository ticketPoolRepository, TicketPoolService ticketPoolService) {
        this.configurationServiceImplementation = configService;
        this.ticketPoolRepository = ticketPoolRepository;
        this.ticketPoolService = ticketPoolService;


    }
    @PostMapping("/threadRunning")
    public String ThreadController() {
        TicketPoolService ticketPoolService = new TicketPoolService(configurationServiceImplementation);

        VendorService vendorService1 = new VendorService(configurationServiceImplementation, ticketPoolService);
        Thread thread = new Thread(vendorService1);
        thread.start();

        VendorService vendorService2 = new VendorService(configurationServiceImplementation, ticketPoolService);
        Thread thread2 = new Thread(vendorService1);
        thread2.start();

        CustomerService customerService = new CustomerService(configurationServiceImplementation, ticketPoolService);
        Thread thread3 = new Thread(customerService);
        thread3.start();

        CustomerService customerService2 = new CustomerService(configurationServiceImplementation, ticketPoolService);
        Thread thread4 = new Thread(customerService2);
        thread4.start();
        return "success";
    }
}
