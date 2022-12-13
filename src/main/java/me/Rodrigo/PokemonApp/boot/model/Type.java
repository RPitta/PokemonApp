package me.Rodrigo.PokemonApp.boot.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Type {

    @Id
    @Column
    private int id;

    @Column
    private String name;

    public String getName() { return name; }
    public int getId() { return id; }

    public void setName(String name) { this.name = name; }
}
