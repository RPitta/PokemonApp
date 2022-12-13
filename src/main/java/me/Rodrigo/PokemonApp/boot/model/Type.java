package me.Rodrigo.PokemonApp.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Type {

    @Id
    @Column
    private int id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany( mappedBy = "types" )
    private Set<Pokemon> pokemonSet = new HashSet<>();

    public String getName() { return name; }
    public int getId() { return id; }

    public void setName(String name) { this.name = name; }
}
