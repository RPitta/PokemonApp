package me.Rodrigo.PokemonApp.boot.configuration;

import me.Rodrigo.PokemonApp.boot.services.PokemonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean(name={"myBean"})
    public PokemonService loadPokemon() {
        return new PokemonService();
    }
}
