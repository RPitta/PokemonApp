package me.Rodrigo.PokemonApp.boot.services;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.Rodrigo.PokemonApp.boot.model.Pokemon;
import me.Rodrigo.PokemonApp.boot.model.Type;
import org.json.JSONArray;
import me.Rodrigo.PokemonApp.boot.repository.PokemonRepository;
import me.Rodrigo.PokemonApp.boot.repository.TypeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PokemonService {
    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    TypeRepository typeRepository;

    public Pokemon assignType(int pokemonId, int typeId) {
        Set<Type> typeSet = null;
        Pokemon p = pokemonRepository.findById(pokemonId).get();
        Type t = typeRepository.findById(typeId).get();
        typeSet = p.getTypes();
        typeSet.add(t);
        p.setTypes(typeSet);
        return pokemonRepository.save(p);

    }

    public static String getPokemon(String urlString) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            // Create new HTTP connection
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("User-Agent", "");

            // Request setup
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            // Make request
            int status = con.getResponseCode();

            // Read response
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            return responseContent.toString();

        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static String[] getPokemonType(String response) {
        JSONObject pokemon = new JSONObject(response);
        JSONArray types  = pokemon.getJSONArray("types");

        List<String> typesArr = new ArrayList<String>();

        for (int i = 0; i < types.length(); i++) {
            JSONObject type = types.getJSONObject(i);
            JSONObject obj = type.getJSONObject("type");
            typesArr.add(obj.get("name").toString());
        }

        String[] arr =  new String[typesArr.size()];
        return typesArr.toArray(arr);
    }

    public static String getPokemonSprite(String response) {
        // Get's pokemon's front sprite
        JSONObject pokemon = new JSONObject(response);
        JSONObject sprites  = pokemon.getJSONObject("sprites");

        return sprites.get("front_default").toString();
    }

    public void parseAllPokemon(String response) {
        // Parses JSON response of 151 pokemon and maps it to a List<Pokemon>
        JSONObject pokemon = new JSONObject(response);
        JSONArray allPokemon  = pokemon.getJSONArray("results");


        for (int i = 0; i < allPokemon.length(); i++) {
            JSONObject obj = allPokemon.getJSONObject(i);
            String name  = obj.get("name").toString();
            String url = obj.get("url").toString();

            // Another request to pokeApi must be made to get a pokemon's type and sprite info
            String res = getPokemon(url);
            String[] types = getPokemonType(res);
            String spriteUrl = getPokemonSprite(res);
            Pokemon p = new Pokemon(name, i + 1, spriteUrl);
            pokemonRepository.save(p);

            for (String type : types) {
                int typeId = typeRepository.findByName(type);
                assignType(i + 1, typeId);
            }
        }
    }

    public void loadAllPokemon() {
        // Gets all 151 pokemon data and saves it to the database
        String res = getPokemon("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0");
        parseAllPokemon(res);
    }

    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }
    public Pokemon getPokemonById(int id) {
        return pokemonRepository.findById(id).get();
    }
}
