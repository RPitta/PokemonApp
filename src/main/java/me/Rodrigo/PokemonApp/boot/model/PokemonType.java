package me.Rodrigo.PokemonApp.boot.model;

import javax.persistence.*;

@Entity
@Table
public class PokemonType {
    @Id
    @Column
    private int refId;

    @ManyToOne(fetch = FetchType.EAGER)

    private Pokemon pokemon;

    @ManyToOne(fetch = FetchType.EAGER)

    private Type type;

//    public PokemonType()
    public Type getType() {
        return type;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }
}
