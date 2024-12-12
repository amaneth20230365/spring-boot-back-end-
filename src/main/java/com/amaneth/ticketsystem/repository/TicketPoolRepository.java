package com.amaneth.ticketsystem.repository;

import com.amaneth.ticketsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<Ticket, Integer> {

}
