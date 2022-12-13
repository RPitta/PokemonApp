package me.Rodrigo.PokemonApp.boot.controller;

import me.Rodrigo.PokemonApp.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class PokemonController {

    @Autowired
    // Marks this as something that needs dependency injection
    private PokemonService pokemonService;

    @GetMapping("/api/load")
    public ResponseEntity loadAllPokemon() {
        pokemonService.loadAllPokemon();
        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/api/pokemon")
    public ResponseEntity getAllPokemon() {  return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).
                                                                     body(pokemonService.getAllPokemon()); }

    @GetMapping("/api/pokemon/{id}")
    public ResponseEntity getPokemonById(@PathVariable("id") String id) {
        int pid = Integer.parseInt(id);

        // There are only 905 pokemon
        if (pid < 1 || pid > 905) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }

        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).
            body(pokemonService.getPokemonById(pid));
    }
}
