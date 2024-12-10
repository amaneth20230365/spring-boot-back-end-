package com.amaneth.ticketsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int TicketId;
    private String Name;
    private String Description;
    private String Status;

    public TicketPool() {
    }
    public TicketPool(String name, String description, String status) {
        Name = name;
        Description = description;
        Status = status;


    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

