package com.tessi.cinemaproject.service;

import com.tessi.cinemaproject.dao.*;
import com.tessi.cinemaproject.entites.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemInitServiceImpl implements  ICinemaInitService {
    @Autowired // injection de depondance
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired // injection de depondance
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired // injection de depondance
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired // injection de depondance
    private CategorieRepository categorieRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ProjectionRepository projectionRepository;




    @Override
    public void initVilles() {
        Stream.of("Tunis","Sousse","Mahdia","Sfax").forEach(nameVille->{
            Ville ville =new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });

    }

    @Override
    public void initCinemas() {
       villeRepository.findAll().forEach(ville -> {
           Stream.of("Zafir Cinema","IMAX","Founou","DAOUILIZ").forEach(nameCinema ->{
               Cinema cinema= new Cinema();
               cinema.setName(nameCinema);
               cinema.setNombreSalles(3+(int)(Math.random()*7));
               cinema.setVille(ville);
               cinemaRepository.save(cinema);
           });
       });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
          for(int i=0;i<cinema.getNombreSalles();i++){
              Salle salle = new Salle();
              salle.setName("Salle "+(i+1));
              salle.setCinema(cinema);
              salle.setNombrePlace(15+(int)(Math.random()*20));
              salleRepository.save(salle);

          }
        });
    }

    @Override
    public void initPlaces() {
      salleRepository.findAll().forEach(salle -> {
          for (int i =0;i<salle.getNombrePlace();i++){
              Place place = new Place();
              place.setNumero(i+1);
              place.setSalle(salle);
              placeRepository.save(place);
          }
      });
    }

    @Override
    public void initSeances() {
        DateFormat dateForamt= new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
            Seance seance= new Seance();
            try {
                seance.setHeurDebut(dateForamt.parse(s));
                seanceRepository.save(seance);
            }catch (ParseException e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initCategoris() {
     Stream.of("Histoire","Action","Fiction","Drama").forEach(cat->{
         Categorie categorie = new Categorie();
         categorie.setName(cat);
         categorieRepository.save(categorie);
     });
    }

    @Override
    public void initFilms() {
        double []durees = new double[]{ 1,1.5,2,2.5,3};
        List<Categorie>categories =categorieRepository.findAll();
      Stream.of("Aladdin","Charles Angel","AquaMan","BRIGHT","DALIDA","DOCTEUR","DORA","JOCKER","PAPILLON","PARASITE","le Parrain","TITANIC","TOGO")
              .forEach( filmTitre->{
                  Film film =new Film();
                  film.setTitre(filmTitre);
                  film.setDuree(durees[new Random().nextInt(durees.length)]);
                  film.setPhoto(filmTitre.replaceAll(" ","")+".jpg");
                  film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                  filmRepository.save(film);

              });
    }

    @Override
    public void initProjections() {
        double [] prices=new double[]{30,50,60,70,90,100};
        List<Film>films=filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema ->{
                cinema.getSalles().forEach(salle -> {
                     int index=new Random().nextInt(films.size());
                      Film film=films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix((prices[new Random().nextInt(prices.length)]));
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });


                });

            });
        });

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p->{
           p.getSalle().getPlaces().forEach(place -> {
               Ticket ticket = new Ticket();
               ticket.setPlace(place);
               ticket.setPrix(p.getPrix());
               ticket.setProjection(p);
               ticket.setReserve(false);
               ticketRepository.save(ticket);
           });
        });

    }
}
