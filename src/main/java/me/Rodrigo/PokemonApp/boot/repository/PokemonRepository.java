package me.Rodrigo.PokemonApp.boot.repository;
import me.Rodrigo.PokemonApp.boot.model.Pokemon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer>{
    @Override
    List<Pokemon> findAll();
}
