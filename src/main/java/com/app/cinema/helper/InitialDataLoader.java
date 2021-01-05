package com.app.cinema.helper;

import com.app.cinema.model.*;
import com.app.cinema.repository.*;
import com.app.cinema.service.interfaces.MovieService;
import com.app.cinema.service.interfaces.UserService;
import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
@AllArgsConstructor
public class InitialDataLoader implements ApplicationRunner {

    private static final String moviesJsonUrl="https://raw.githubusercontent.com/erik-sytnyk/movies-list/master/db.json";

    private final UserRepository userRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final MovieRepository movieRepository;
    private final ChairRepository chairRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieService movieService;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<User> byEmail=userRepository.findByEmail("test@mail.com");
        if (byEmail.isEmpty()) loadData();
    }

    private void loadData() {
        Faker faker=new Faker();
        User user=new User("Adam", "Kowalski", "test@mail.com", true, "test", null);
        userService.save(user);
        Cinema cinema=new Cinema();
        cinema.setName("Helios2");
        for (int i=0; i < 5; i++) {
            CinemaHall cinemaHall=new CinemaHall();
            cinemaHall.setName(faker.animal().name());
            cinemaHall.setChairs(this.getChairs(faker, cinemaHall));
            cinemaHall.setScreenSizeInch((float) faker.number().randomDouble(2, 30, 60));
            cinemaHall.setCinemaBuilding(cinema);
            cinema.addCinemaHall(cinemaHall);
        }
        cinemaRepository.save(cinema);
        List<Movie> movies=this.getMovies(faker);
        movieRepository.saveAll(movies);
    }

    private List<Movie> getMovies(Faker faker) {
        List<Movie> movies = new ArrayList<>();
        try (InputStream inputStream=new URL(moviesJsonUrl).openStream()) {
            String json=new String(inputStream.readAllBytes());
            JsonObject jsonObject=JsonParser.parseString(json).getAsJsonObject();
            JsonArray moviesArrayJson=jsonObject.getAsJsonArray("movies");
            moviesArrayJson.forEach(jsonElement -> {
                JsonObject object=jsonElement.getAsJsonObject();
                Movie movie = new Movie();
                movie.setPlot(object.get("plot").getAsString());
                movie.setPosteUrl(object.get("posterUrl").getAsString());
                movie.setActors(object.get("actors").getAsString());
                movie.setDirector(object.get("director").getAsString());
                movie.setRunTimeMin(object.get("runtime").getAsInt());
                movie.setYear(object.get("year").getAsInt());
                movie.setTitle(object.get("title").getAsString());
                long timeMs=faker.number().numberBetween(1619863200000L, 1651399200000L);
                movie.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMs), TimeZone.getDefault().toZoneId()));

                List<String> genresNames = new ArrayList<>();
                JsonArray generesJsonArray=object.getAsJsonArray("genres");
                generesJsonArray.forEach(jsonElement1 -> {
                    genresNames.add(jsonElement1.getAsString());
                });

                movie.setGenres(movieService.saveGenres(genresNames));
                movies.add(movie);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private List<Chair> getChairs(Faker faker, CinemaHall cinemaHall) {
        List<Chair> chairs=new ArrayList<>();
        int maxChairs=faker.number().numberBetween(24, 36);
        for (int j=0; j < maxChairs; j++) {
            Chair chair=new Chair();
            chair.setNumber(j);
            if (j > 20) {
                chair.setType(ChairType.VIP);
            } else {
                chair.setType(ChairType.NORMAL);
            }
            chair.setCinemaHall(cinemaHall);
            chairs.add(chair);
        }
        return chairs;
    }

}
