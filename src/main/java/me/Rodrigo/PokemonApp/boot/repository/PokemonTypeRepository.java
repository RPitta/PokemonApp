package me.Rodrigo.PokemonApp.boot.repository;
import me.Rodrigo.PokemonApp.boot.model.PokemonType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PokemonTypeRepository extends CrudRepository<PokemonType, Integer>{
    @Modifying
    @Query(value = "insert into POKEMON_TYPE p (p.POKEMON_ID, p.TYPE_ID) VALUES (:pokemonId, :typeId)", nativeQuery = true)
    @Transactional
    void insertPokemonType(@Param("pokemonId") Integer pokemonId, @Param("typeId") Integer typeId);


}
