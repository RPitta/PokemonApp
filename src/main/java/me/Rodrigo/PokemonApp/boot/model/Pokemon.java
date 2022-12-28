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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "POKEMON_ID"),
            inverseJoinColumns = @JoinColumn(name = "TYPE_ID")
    )
    private Set<Type> types = new HashSet<>();

    public Pokemon() {}
    public Pokemon(String name, int id, String imgSrc) {
        setId(id);
        setName(name);
        setImgSrc(imgSrc);
    }

    public int getId() { return id;}
    public String getName() { return name;}
    public String getImgSrc() { return imgSrc;}
    public Set<Type> getTypes() { return types;}

    public void setId(int newId) { id = newId; }
    public void setName(String name) { this.name = name;}
    public void setImgSrc(String imgSrc) { this.imgSrc = imgSrc;}
    public void setTypes(Set<Type> types) { this.types = types;}

}
