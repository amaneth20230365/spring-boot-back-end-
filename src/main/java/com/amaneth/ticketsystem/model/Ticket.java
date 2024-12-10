package com.amaneth.ticketsystem.model;

public class Ticket {
    private int ticketId;
    private int vendorId;
    private int customerId;

    public Ticket() {
    }

    public Ticket(int ticketId, int vendorId, int customerId) {
        this.ticketId = ticketId;
        this.vendorId = vendorId;
        this.customerId = customerId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", vendorId=" + vendorId +
                ", customerId=" + customerId +
                '}';
    }
}
