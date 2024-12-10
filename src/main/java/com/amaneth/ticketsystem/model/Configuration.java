package com.amaneth.ticketsystem.model;


public class Configuration {

    private String eventName;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerReleaseRate;
    private int maxTicketCapacity;

    public Configuration() {
    }

    public Configuration(String eventName, int totalTickets, int ticketReleaseRate, int customerReleaseRate, int maxTicketCapacity) {
        this.eventName = eventName;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerReleaseRate = customerReleaseRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerReleaseRate() {
        return customerReleaseRate;
    }

    public void setCustomerReleaseRate(int customerReleaseRate) {
        this.customerReleaseRate = customerReleaseRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "eventName='" + eventName + '\'' +
                ", totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerReleaseRate=" + customerReleaseRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}

