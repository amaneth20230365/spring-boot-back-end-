package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


@Service
public class TicketPoolService {
    private final ConfigurationService configurationService;
    private final ConcurrentLinkedDeque<String> simulationLogs = new ConcurrentLinkedDeque<String>();

    public TicketPoolService(ConfigurationService configurationService) {
        this.configurationService = configurationService;

    }
    List<Ticket> ticketsList = Collections.synchronizedList(new ArrayList<>());

    public static final Logger logger = Logger.getLogger(TicketPoolService.class.getName());

    public int getCurrentPoolSize() {
        return ticketsList.size();
    }

    public synchronized void addTicket(AtomicInteger totalTicketReleased, int ticketPoolCapacity,int vendorId) {
        if (ticketsList.size() + 1 > ticketPoolCapacity) {
            try {
                String message ="TicketPool is full. Waiting";
                System.out.println(message);
                simulationLogs.add(message);
                logger.info("TicketPool is full., waiting...");
                wait(); // Wait until there's space in the pool

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor was interrupted while waiting to add tickets.");

                logger.warning("ERROR: "+e.getMessage());
            }
        }else if (ticketsList.size()+ 1 <= ticketPoolCapacity) {
            for (int i = 0; i < 1; i++) {
                int ticketId = totalTicketReleased.get();
                Ticket ticket = new Ticket(ticketId,vendorId,1);
                ticketsList.add(ticket);
                String message ="Vendor added 1 ticket. Current pool size: " + ticketsList.size()+". Total tickets released: "+ticketId;
                System.out.println(message);
                simulationLogs.add(message);
                logger.info("Vendor added 1 ticket. Current pool size: " + ticketsList.size()+". Total tickets released: "+ticketId);
                notifyAll(); // Notify all waiting threads (likely customers)
            }
        }
    }

    public synchronized void removeTicket() {
        while (ticketsList.isEmpty()){
            try{
                String message="TicketPool is empty. No available tickets. Waiting...";
                System.out.println(message);
                simulationLogs.add(message);
                logger.info("TicketPool is empty. No available tickets.");
                wait();
            }catch (InterruptedException e){
                System.out.println(" was interrupted while waiting to buy tickets.");
            }
        }
        for (int i = 0; i < 1; i++) {
            ticketsList.remove(0);
        }
        String message=" bought 1 ticket. Current Pool size: "+ticketsList.size();
        System.out.println(message);
        simulationLogs.add(message);
        logger.info(" bought 1 ticket. Current pool size: "+ticketsList.size());
        notifyAll();
    }

    public ConcurrentLinkedDeque<String> getSimulationLogs() {
        return simulationLogs;
    }
}