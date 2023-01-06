package me.Rodrigo.PokemonApp.boot;

import me.Rodrigo.PokemonApp.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PokemonAppApplication {
	@Autowired
	private PokemonService pokemonService;
	public static void main(String[] args) {
		SpringApplication.run(PokemonAppApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadPokemon() {
		pokemonService.loadAllPokemon();
	}

}
