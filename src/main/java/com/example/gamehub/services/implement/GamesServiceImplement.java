package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.Games;
import com.example.gamehub.records.GameRecord;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GamesServiceImplement implements GamesService {

    @Autowired
    GamesRepository gamesRepository;

    private static ResponseEntity<String> verifyNonBlankFields(GameRecord gameRecord) {
        if (gameRecord.title().isBlank()) {
            return new ResponseEntity<>("Name cannot be blank", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.description().isBlank()) {
            return new ResponseEntity<>("Description cannot be blank", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.genres().isEmpty()) {
            return new ResponseEntity<>("Game must have at least one genre", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.platforms().isEmpty()) {
            return new ResponseEntity<>("Game must be available on at least one platform", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.price() < 0) {
            return new ResponseEntity<>("Price cannot be negative", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.rating() == null) {
            return new ResponseEntity<>("Rating cannot be null", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.releaseDate() == null) {
            return new ResponseEntity<>("Release date cannot be null", HttpStatus.FORBIDDEN);
        }
        if (gameRecord.discount() < 0) {
            return new ResponseEntity<>("Discount cannot be negative", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Override
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    @Override
    public List<GamesDTO> getAllGamesDTO() {
        return getAllGames().stream().map(GamesDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> createGame(GameRecord gameRecord) {
        Games game = new Games(gameRecord.title(), gameRecord.description(), gameRecord.developer(),
                gameRecord.description(), gameRecord.imageURL(), gameRecord.sales(), gameRecord.price(), gameRecord.stock(),
                gameRecord.releaseDate(), gameRecord.discount(), gameRecord.rating(), gameRecord.genres(),
                gameRecord.platforms());

        ResponseEntity<String> FORBIDDEN = verifyNonBlankFields(gameRecord);
        if (FORBIDDEN != null) return FORBIDDEN;

        gamesRepository.save(game);
        return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
    }

    @Override
    public Games findById(Long id) {
        return gamesRepository.findById(id).orElse(null);
    }

    @Override
    public Games findByTitle(String title) {
        return gamesRepository.findByTitle(title);
    }

    @Override
    public GamesDTO findGameDTOById(Long id) {
        return new GamesDTO(findById(id));
    }

    public ResponseEntity<String> deleteById(Long id) {
        gamesRepository.findById(id);
        gamesRepository.deleteById(id);
        return new ResponseEntity<>("Game Delete", HttpStatus.OK);
    }

    @Override

    public ResponseEntity<String> updateGame(Long id, GameRecord gameRecord) {
        Games games = findById(id);
        if (games == null) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        ResponseEntity<String> FORBIDDEN = verifyNonBlankFields(gameRecord);
        if (FORBIDDEN != null) return FORBIDDEN;
        games.setTitle(gameRecord.title());
        games.setDescription(gameRecord.description());
        games.setDeveloper(gameRecord.developer());
        games.setImageURL(gameRecord.imageURL());
        games.setSales(gameRecord.sales());
        games.setPrice(gameRecord.price());
        games.setStock(gameRecord.stock());
        games.setReleaseDate(gameRecord.releaseDate());
        games.setDiscount(gameRecord.discount());
        games.setRating(gameRecord.rating());
        games.setGenres(gameRecord.genres());
        games.setPlatforms(gameRecord.platforms());
//        games.setCoverURL(gameRecord.coverURL());
//        games.setLongDescription(gameRecord.longDescription());
        gamesRepository.save(games);

        return ResponseEntity.ok("Game updated successfully");
    }
}