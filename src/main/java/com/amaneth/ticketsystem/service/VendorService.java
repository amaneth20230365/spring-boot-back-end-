package com.amaneth.ticketsystem.service;


import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Service
public class VendorService implements Runnable {
    private final TicketPoolService ticketPoolService;
    private int vendorId;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerReleaseRate;
    private int maxTicketCapacity;

    public static final AtomicInteger totalTicketsReleased = new AtomicInteger(0);


    public VendorService(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // Setters for dynamic initialization
    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerReleaseRate(int customerReleaseRate) {
        this.customerReleaseRate = customerReleaseRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public static int getTotalTicketsReleased() {
        return totalTicketsReleased.get();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ticketPoolService) {
                if (totalTicketsReleased.get() >= totalTickets) {
                    break;
                } else if (ticketPoolService.getCurrentPoolSize() <= maxTicketCapacity) {
                    totalTicketsReleased.incrementAndGet();
                    ticketPoolService.addTicket(totalTicketsReleased, maxTicketCapacity, vendorId);
                    try {
                        Thread.sleep(ticketReleaseRate);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: " + e.getMessage());
                        Logger.getLogger(VendorService.class.getName()).warning(e.getMessage());
                    }
                }
            }
        }
    }
}
