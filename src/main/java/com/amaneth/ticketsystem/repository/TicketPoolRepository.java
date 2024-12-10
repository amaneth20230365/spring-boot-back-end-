package com.amaneth.ticketsystem.repository;

import com.amaneth.ticketsystem.model.Ticket;
import com.amaneth.ticketsystem.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Integer> {

}
