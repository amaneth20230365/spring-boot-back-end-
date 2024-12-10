package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


@Service
public class TicketPoolService {
    private final ConfigurationService configurationService;

    public TicketPoolService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
    List<Ticket> ticketsList = Collections.synchronizedList(new ArrayList<>());

    public static final Logger logger = Logger.getLogger(TicketPoolService.class.getName());

    public int getCurrentPoolSize() {
        return ticketsList.size();
    }

    public synchronized void addTicket(AtomicInteger totalTicketsReleased, int ticketPoolCapacity) {
        if (ticketsList.size() + 1 > ticketPoolCapacity) {
            try {
                System.out.println("TicketPool is full.");
                System.out.println("Waiting");
                logger.info("TicketPool is full., waiting...");
                wait(); // Wait until there's space in the pool

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor was interrupted while waiting to add tickets.");
                logger.warning("ERROR: "+e.getMessage());
            }
        }else if (ticketsList.size()+ 1 <= ticketPoolCapacity) {
            for (int i = 0; i < 1; i++) {
                Ticket ticket = new Ticket(1,1,1);
                ticketsList.add(ticket);
                System.out.println("Vendor added 1 ticket. Current pool size: " + ticketsList.size()+". Total tickets released: "+totalTicketsReleased);
                logger.info("Vendor added 1 ticket. Current pool size: " + ticketsList.size()+". Total tickets released: "+totalTicketsReleased);
                notifyAll(); // Notify all waiting threads (likely customers)
            }
        }
    }

    public synchronized void removeTicket() {
        while (ticketsList.isEmpty()){
            try{
                System.out.println("TicketPool is empty. No available tickets.");
                System.out.println(" is waiting.");
                logger.info("TicketPool is empty. No available tickets.");
                wait();
            }catch (InterruptedException e){
                System.out.println(" was interrupted while waiting to buy tickets.");
            }
        }
        for (int i = 0; i < 1; i++) {
            ticketsList.remove(0);
        }
        System.out.println(" bought 1 ticket. Current Pool size: "+ticketsList.size());
        logger.info(" bought 1 ticket. Current pool size: "+ticketsList.size());
        notifyAll();
    }
}