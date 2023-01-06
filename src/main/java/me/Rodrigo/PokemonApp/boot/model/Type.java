package me.Rodrigo.PokemonApp.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="type")
public class Type {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="color")
    private String color;

    @JsonIgnore
    @ManyToMany( mappedBy = "types" )
    private Set<Pokemon> pokemonSet = new HashSet<>();

    public Type() {};

    public Type(int id, String name, String color) {
        setId(id);
        setName(name);
        setColor(color);
    }
    public String getName() { return name; }
    public int getId() { return id; }

    public String getColor() { return color; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }

}
