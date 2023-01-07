package me.Rodrigo.PokemonApp.boot.repository;

import me.Rodrigo.PokemonApp.boot.model.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    @Query("SELECT t.id FROM Type t WHERE t.name = :name")
    Integer findByName(@Param("name") String name);
}
