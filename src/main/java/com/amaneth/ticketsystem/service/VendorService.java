package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Configuration;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Service
public class VendorService implements Runnable {
    private ConfigurationService configurationService;
    private TicketPoolService ticketPoolService;

    public static final Logger logger = Logger.getLogger(VendorService.class.getName());

    public VendorService(ConfigurationService configurationService, TicketPoolService ticketPoolService) {
        this.configurationService = configurationService;
        this.ticketPoolService = ticketPoolService;
    }

    Configuration configuration = new Configuration();

    public static final AtomicInteger totalTicketsReleased = new AtomicInteger(0);

    int totalTickets =configuration.getTotalTickets();
    int ticketReleaseRate = configuration.getTicketReleaseRate();
    int customerReleaseRate = configuration.getCustomerReleaseRate();
    int ticketPoolCapacity =configuration.getTotalTickets();

    public static int getTotalTicketsReleased() {
        return totalTicketsReleased.get(); // Provide access to the current value
    }


    @Override
    public void run() {
        while (true) {
            System.out.println(totalTickets+"tickets");
            System.out.println(ticketReleaseRate+"release");
            System.out.println(customerReleaseRate+"customer");
            System.out.println(ticketPoolCapacity+"maxcapcity");
            synchronized (ticketPoolService) {
                if (totalTicketsReleased.get() < ticketPoolCapacity) {
                    break;
                }

                else if (ticketPoolService.getCurrentPoolSize() + 1 <= ticketPoolCapacity) {
                    totalTicketsReleased.incrementAndGet();
                    ticketPoolService.addTicket(totalTicketsReleased, ticketPoolCapacity);
                    try {
                        Thread.sleep(ticketReleaseRate);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: " + e.getMessage());
                        logger.warning(e.getMessage());
                    }
                }

                else {
                    System.out.println("Ticket pool is full, waiting for cutsomers...");
                    try {
                        ticketPoolService.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
}