package me.Rodrigo.PokemonApp.boot.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Pokemon {
    @Column
    private String name;

    @Id
    @Column
    private int id;

    @Column
    private String imgSrc;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "POKEMON_ID"),
            inverseJoinColumns = @JoinColumn(name = "TYPE_ID")
    )
    private Set<Type> types = new HashSet<>();


    // New class Type with a single name property
    // and addType() that takes in a type and a pokemon id to be used in the map table.
    // Have types table already generated with the 18 types
    //
    // getPokemon methods would have to first query pokemon table to get the id and then
    // the map table with
//    private List<String> types = new ArrayList<>();

    public Pokemon() {}
    public Pokemon(String name, int id, String imgSrc) {
        setId(id);
        setName(name);
        setImgSrc(imgSrc);
//        addTypes(types);
    }

    public int getId() { return id;}
    public String getName() { return name;}
    public String getImgSrc() { return imgSrc;}
    public Set<Type> getTypes() { return types;}

    public void setId(int newId) { id = newId; }
    public void setName(String name) { this.name = name;}
    public void setImgSrc(String imgSrc) { this.imgSrc = imgSrc;}
    public void setTypes(Set<Type> types) { this.types = types;}

//    public void addTypes(String[] newTypes) {
//       List l = Arrays.asList(types);
//        for (String type : newTypes) {
//            this.types.add(type);
//        }
//    }


}
