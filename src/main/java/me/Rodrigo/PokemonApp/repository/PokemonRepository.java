package me.Rodrigo.PokemonApp.repository;
import me.Rodrigo.PokemonApp.Pokemon;
import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer>{
}
