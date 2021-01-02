package com.tessi.cinemaproject.web;

import com.tessi.cinemaproject.dao.FilmRepository;
import com.tessi.cinemaproject.dao.TicketRepository;
import com.tessi.cinemaproject.entites.Film;
import com.tessi.cinemaproject.entites.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping("/listFilms")
   public List<Film> film(){
     return filmRepository.findAll();
 }
 @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
 public byte[]images(@PathVariable(name="id") Long id) throws IOException {
  Film f =filmRepository.findById(id).get();
  String photoName=f.getPhoto();
     File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
     Path path = Paths.get(file.toURI());
     return Files.readAllBytes(path);
 }
 @PostMapping("/payerTickets")
 @Transactional
 public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm){
        List<Ticket>listTicket= new ArrayList<>();
       ticketForm.getTickets().forEach(idTicket->{
       System.out.println(idTicket);
       Ticket ticket =ticketRepository.findById(idTicket).get();
       ticket.setNomClient(ticketForm.getNomClient());
       ticket.setCodePayement(ticketForm.getCodePayement());
       ticket.setReserve(true);
       ticketRepository.save(ticket);
       listTicket.add(ticket);
   });
   return  listTicket;
 }
 @Data
    static class TicketForm {
        private String  nomClient;
        private  int codePayement;
        private List<Long>tickets=new ArrayList<>();
 }


}
