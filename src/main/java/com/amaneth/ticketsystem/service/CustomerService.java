package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Configuration;
import org.springframework.stereotype.Service;

import static com.amaneth.ticketsystem.service.VendorService.getTotalTicketsReleased;
import static com.amaneth.ticketsystem.service.VendorService.totalTicketsReleased;

@Service
public class CustomerService implements Runnable{
    private ConfigurationService configService;
    private TicketPoolService ticketPoolService;

    public CustomerService(ConfigurationService configService, TicketPoolService ticketPoolService) {
        this.configService = configService;
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void run() {
        try{
            Configuration configuration = new Configuration();

            int totalTickets = configuration.getTotalTickets();
            int ticketReleaseRate = configuration.getTicketReleaseRate();
            int customerReleaseRate = configuration.getCustomerReleaseRate();
            int ticketPoolCapacity = configuration.getTotalTickets();

            VendorService vendorService = new VendorService(configService, ticketPoolService);
            while (getTotalTicketsReleased() != totalTickets || ticketPoolService.getCurrentPoolSize() > 0){
                try {
                    synchronized (ticketPoolService){
                        if ((ticketPoolService.getCurrentPoolSize())>0){
                            ticketPoolService.removeTicket();
                            Thread.sleep(customerReleaseRate);
                        }else{
                            if(totalTicketsReleased.get() ==totalTickets && ticketPoolService.getCurrentPoolSize() == 0){
                                break;
                            }
                            else {
                                System.out.println("No tickets Left. ");
                                ticketPoolService.wait();
                            }
                        }
                    }
                }catch (Exception e){
                    System.out.println("ERROR: When consuming Ticket"+e.getMessage());
                }

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
