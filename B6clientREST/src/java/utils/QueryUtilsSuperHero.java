/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import model.superHero;

/**
 * Created by Rodrii on 19/07/2018.
 */
public final class QueryUtilsSuperHero {

    private static final String LOG_TAG = QueryUtilsSuperHero.class.getSimpleName();
    final static String NO_CONTRIBUTOR = "Unknown";

    public static superHero fetchDatos(String requestUrl){
        URL url = createURL(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println(LOG_TAG + "Error closing input stream");
        }

        superHero heroe = extractDatos(jsonResponse);
        return heroe;
    }

    // Crea la URL
    private static URL createURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            System.out.println(LOG_TAG + "Error with creating URL " + e);
        }
        return url;
    }

    private static superHero extractDatos(String PeliculaJSON) {

        superHero heroe = new superHero();
        // Si la respuesta JSON está vacía devuelvo null, pues no hay datos
        // asociados a esa id
        if (PeliculaJSON.length() == 0) {
            return null;
        } else {
            try {

                JSONObject root = new JSONObject(PeliculaJSON);
                JSONObject data = root.getJSONObject("data");

                if (data.getInt("total") >= 1) {
                    JSONArray results = data.getJSONArray("results");
                    JSONObject primerHeroe = results.getJSONObject(0);
                    JSONObject thumbnail = primerHeroe.getJSONObject("thumbnail");
                    heroe.setPath(thumbnail.getString("path")+ "." + thumbnail.getString("extension"));
                    
                    
                    JSONObject comics = primerHeroe.getJSONObject("comics");
                    JSONArray items = comics.getJSONArray("items");
                    
                    List<String> comicsHeroes = new ArrayList<>();
                    for(int i=0; i<items.length(); i++){
                        JSONObject comic = items.getJSONObject(i);
                        comicsHeroes.add(comic.getString("name"));
                    }
                    

                }

            } catch (JSONException e) {
                System.out.println("QueryUtils: Problem parsing the Pelicula JSON results " + e);
            }
            return heroe;
        }
    }

    //Establece la conexión Http
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000);
            urlConnection.setConnectTimeout(100500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                System.out.println(LOG_TAG + " Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println(LOG_TAG + " Problem retrieving the Pelicula JSON results." + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
