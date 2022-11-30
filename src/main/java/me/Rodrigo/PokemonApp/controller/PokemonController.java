package me.Rodrigo.PokemonApp.controller;

import me.Rodrigo.PokemonApp.Pokemon;
import me.Rodrigo.PokemonApp.services.PokemonService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
public class PokemonController {

    @Autowired
    // Marks this as something that needs dependency injection
    private PokemonService pokemonService;

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
