package com.amaneth.ticketsystem.controller;

import com.amaneth.ticketsystem.model.Configuration;
import com.amaneth.ticketsystem.service.ConfigurationService;
import com.amaneth.ticketsystem.service.CustomerService;
import com.amaneth.ticketsystem.service.TicketPoolService;
import com.amaneth.ticketsystem.service.VendorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.amaneth.ticketsystem.service.VendorService.totalTicketsReleased;

@RestController
@RequestMapping("/api/threadController")
@CrossOrigin("http://localhost:3000")
public class ThreadController {
    private final TicketPoolService ticketPoolService;
    private final VendorService vendorService;
    private final ConfigurationService configurationService;

    public ThreadController(TicketPoolService ticketPoolService, VendorService vendorService, ConfigurationService configurationService) {
        this.ticketPoolService = ticketPoolService;
        this.vendorService = vendorService;
        this.configurationService = configurationService;
    }

    @PostMapping("/threadRunning")
    public void threadController() {
        Configuration configuration = configurationService.getCurrentConfiguration();

        if (configuration == null) {
            System.out.println("No configuration loaded. Please load a configuration file first.");
        }

        int totalTickets = configuration.getTotalTickets();
        int ticketReleaseRate = configuration.getTicketReleaseRate();
        int customerReleaseRate = configuration.getCustomerReleaseRate();
        int maxTicketCapacity = configuration.getMaxTicketCapacity();

        // VendorService 1
        VendorService vendor1 = new VendorService(ticketPoolService);
        vendor1.setVendorId(1);
        vendor1.setTotalTickets(totalTickets);
        vendor1.setTicketReleaseRate(ticketReleaseRate);
        vendor1.setCustomerReleaseRate(customerReleaseRate);
        vendor1.setMaxTicketCapacity(maxTicketCapacity);

        Thread vendorThread1 = new Thread(vendor1);
        vendorThread1.start();

        // VendorService 2
        VendorService vendor2 = new VendorService(ticketPoolService);
        vendor2.setVendorId(2);
        vendor2.setTotalTickets(totalTickets);
        vendor2.setTicketReleaseRate(ticketReleaseRate);
        vendor2.setCustomerReleaseRate(customerReleaseRate);
        vendor2.setMaxTicketCapacity(maxTicketCapacity);

        Thread vendorThread2 = new Thread(vendor2);
        vendorThread2.start();

//        CustomerService 1
        CustomerService customer1 = new CustomerService(ticketPoolService);
        customer1.setTotalTickets(totalTickets);
        customer1.setTicketReleaseRate(ticketReleaseRate);
        customer1.setCustomerReleaseRate(customerReleaseRate);

        Thread customerThread1 = new Thread(customer1);
        customerThread1.start();

        //  CustomerService 2
        CustomerService customer2 = new CustomerService(ticketPoolService);
        customer2.setTotalTickets(totalTickets);
        customer2.setTicketReleaseRate(ticketReleaseRate);
        customer2.setCustomerReleaseRate(customerReleaseRate);

        Thread customerThread2 = new Thread(customer2);
        customerThread2.start();

        if (totalTicketsReleased.get() == totalTickets && ticketPoolService.getCurrentPoolSize() == 0) {
            System.out.println("Simulation was successful.");;
        }
    }
}
