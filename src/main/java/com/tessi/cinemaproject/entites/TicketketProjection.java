package com.tessi.cinemaproject.entites;

import org.springframework.data.rest.core.config.Projection;

import javax.persistence.*;

@Projection(name = "ticketProj",types = {com.tessi.cinemaproject.entites.Ticket.class})
public interface TicketketProjection {

    public  Long getId();
    public  String getNomClient();
    public  double getPrix();
    public Integer getcodePayement();
    public boolean getReserve();
    public Place getPlace();

}

