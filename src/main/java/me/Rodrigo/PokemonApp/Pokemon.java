package me.Rodrigo.PokemonApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    // New class Type with a single name property
    // and addType() that takes in a type and a pokemon id to be used in the map table.
    // Have types table already generated with the 18 types
    //
    // getPokemon methods would have to first query pokemon table to get the id and then
    // the map table with
//    private List<String> types = new ArrayList<>();

    public Pokemon(String name, int id, String imgSrc, String[] types) {
        setId(id);
        setName(name);
        setImgSrc(imgSrc);
//        addTypes(types);
    }

    public int getId() { return id;}
    public String getName() { return name;}
    public String getImgSrc() { return imgSrc;}
//    public List<String> getTypes() { return types;}

    public void setId(int newId) { id = newId; }
    public void setName(String name) { this.name = name;}
    public void setImgSrc(String imgSrc) { this.imgSrc = imgSrc;}
//    public void addType(String type) { this.types.add(type);}

//    public void addTypes(String[] newTypes) {
//       List l = Arrays.asList(types);
//        for (String type : newTypes) {
//            this.types.add(type);
//        }
//    }


}
