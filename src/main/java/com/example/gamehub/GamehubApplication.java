package com.example.gamehub;

import com.example.gamehub.models.GameGenre;
import com.example.gamehub.models.GamePlatform;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.Rating;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.repositories.GamesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GamehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamehubApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(CustomerRepository customerRepository, GamesRepository gamesRepository) {
		return args -> {
			List<GameGenre> genres = new ArrayList<>();
			GameGenre gameGenre =  GameGenre.ACTION;
			genres.add(gameGenre);
			gameGenre =  GameGenre.SHOOTER;
			genres.add(gameGenre);
			List<GamePlatform> platforms = new ArrayList<>();
			platforms.add(GamePlatform.PC);
			platforms.add(GamePlatform.PS4);
			platforms.add(GamePlatform.PS5);
			platforms.add(GamePlatform.XBOX);
			Games game = new Games("PUBG", "juegazo", "PUBG", "PUBG", "https://cdn.akamai.steamstatic.com/steam/apps/578080/header.jpg?t=1701911655", 1687303L, 0.0, LocalDate.now(), 0.0f, Rating.M, genres, platforms);
			gamesRepository.save(game);
		};
	}
}
