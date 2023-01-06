package me.Rodrigo.PokemonApp.boot.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="pokemon")
public class Pokemon {
    @Column(name="name")
    private String name;

    @Id
    @Column(name="id")
    private int id;

    @Column(name="imgsrc")
    private String imgSrc;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pokemon_types",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @OrderBy
    private Set<Type> types = new LinkedHashSet<>();

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
