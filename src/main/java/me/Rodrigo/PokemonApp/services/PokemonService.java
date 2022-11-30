package me.Rodrigo.PokemonApp.services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import me.Rodrigo.PokemonApp.Pokemon;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

//Pokemon p1 = new Pokemon("asd", 1,  "asd",  new String[]{"pogfdg", "fsjlkdj"});
//        Pokemon p2 = new Pokemon("fsdf", 2,  "fasdfa",  new String[]{"asd", "asdas"});

@Service
public class PokemonService {

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

    public static List<Pokemon> parseAllPokemon(String response) {
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
                    String spriteUrl = getPokemonSprite(res);

                    add(new Pokemon(name, i + 1, spriteUrl, types));
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

        return new Pokemon(name, id, spriteUrl, types);
    }

    public void loadAllPokemon() {
        // Gets all 151 pokemon data and saves it to the database

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
