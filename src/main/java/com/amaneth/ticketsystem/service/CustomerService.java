package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Configuration;
import org.springframework.stereotype.Service;

import static com.amaneth.ticketsystem.service.VendorService.getTotalTicketsReleased;
import static com.amaneth.ticketsystem.service.VendorService.totalTicketsReleased;

@Service
public class CustomerService implements Runnable {
    private final TicketPoolService ticketPoolService;

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerReleaseRate;

    public CustomerService(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // Setters for dynamic configuration
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerReleaseRate(int customerReleaseRate) {
        this.customerReleaseRate = customerReleaseRate;
    }

    @Override
    public void run() {
        try {
            while (getTotalTicketsReleased() != totalTickets || ticketPoolService.getCurrentPoolSize() > 0) {
                try {
                    synchronized (ticketPoolService) {
                        if (ticketPoolService.getCurrentPoolSize() > 0) {
                            ticketPoolService.removeTicket();
                            Thread.sleep(customerReleaseRate);
                        } else {
                            if (totalTicketsReleased.get() == totalTickets && ticketPoolService.getCurrentPoolSize() == 0) {
                                break;
                            } else {
                                System.out.println("No tickets left.");
                                ticketPoolService.wait();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("ERROR: When consuming ticket " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
