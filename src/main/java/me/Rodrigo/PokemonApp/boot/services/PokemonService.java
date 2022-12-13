package me.Rodrigo.PokemonApp.boot.services;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.Rodrigo.PokemonApp.boot.model.Pokemon;
import me.Rodrigo.PokemonApp.boot.model.PokemonType;
import me.Rodrigo.PokemonApp.boot.model.Type;
import org.json.JSONArray;
import me.Rodrigo.PokemonApp.boot.repository.PokemonRepository;
import me.Rodrigo.PokemonApp.boot.repository.TypeRepository;
import me.Rodrigo.PokemonApp.boot.repository.PokemonTypeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Pokemon p1 = new Pokemon("asd", 1,  "asd",  new String[]{"pogfdg", "fsjlkdj"});
//        Pokemon p2 = new Pokemon("fsdf", 2,  "fasdfa",  new String[]{"asd", "asdas"});

@Service
public class PokemonService {
    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    PokemonTypeRepository pokemonTypeRepository;

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

    public List<Pokemon> parseAllPokemon(String response) {
        // Parses JSON response of 151 pokemon and maps it to a List<Pokemon>
        JSONObject pokemon = new JSONObject(response);
        JSONArray allPokemon  = pokemon.getJSONArray("results");

        List<Pokemon> l = new ArrayList<Pokemon>() {
            {
                for (int i = 0; i < allPokemon.length(); i++) {
                    JSONObject obj = allPokemon.getJSONObject(i);
                    String name  = obj.get("name").toString();
                    String url = obj.get("url").toString();

                    // Another request to pokeApi must be made to get a pokemon's type and sprite info
                    String res = getPokemon(url);
                    String[] types = getPokemonType(res);

                    // Here lookup up type in db and save pokemonId and typeId to pokemonType table
                    for (String type : types) {
                        System.out.println(type);
                        int typeId = typeRepository.findByName(type);
                        pokemonTypeRepository.insertPokemonType(i + 1, typeId);
                    }



                    String spriteUrl = getPokemonSprite(res);

                    add(new Pokemon(name, i + 1, spriteUrl));
                }
            }
        };

      return l;
    }

    public static Pokemon parsePokemon(String response) {
        // Parses JSON response of 151 pokemon and maps it to a List<Pokemon>
        JSONObject pokemon = new JSONObject(response);

        String name = pokemon.get("name").toString();
        int id = pokemon.getInt("id");
        String[] types = getPokemonType(response);
        String spriteUrl = getPokemonSprite(response);

        return new Pokemon(name, id, spriteUrl);
    }

    public void loadAllPokemon() {
        // Gets all 151 pokemon data and saves it to the database
        // saveall for pokemonTypesRepository()
        String res = getPokemon("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0");
        List<Pokemon> pokemon = parseAllPokemon(res);
//        pokemon.forEach(p -> pokemonRepository.saveA);

//        pokemonRepository.saveAll(pokemon);
    }
    public List<Pokemon> getAllPokemon() {
        String res = getPokemon("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0");
        return parseAllPokemon(res);
    }

    public Pokemon getPokemonById(int id) {
        String url = String.format("https://pokeapi.co/api/v2/pokemon/%s", id);
        String res = getPokemon(url);
        return parsePokemon(res);
    }



}
