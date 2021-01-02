package com.tessi.cinemaproject;

import com.tessi.cinemaproject.entites.Film;
import com.tessi.cinemaproject.entites.Salle;
import com.tessi.cinemaproject.entites.Ticket;
import com.tessi.cinemaproject.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjectApplication implements CommandLineRunner {
    @Autowired
    private ICinemaInitService cinemaInitService;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
        cinemaInitService.initVilles();
        cinemaInitService.initCinemas();
        cinemaInitService.initSalles();
        cinemaInitService.initPlaces();
        cinemaInitService.initSeances();
        cinemaInitService.initCategoris();
        cinemaInitService.initFilms();
        cinemaInitService.initProjections();
       cinemaInitService.initTickets();

    }
}
