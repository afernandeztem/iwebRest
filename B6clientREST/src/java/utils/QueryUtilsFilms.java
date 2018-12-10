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
import model.Pelicula;

/**
 * Created by Rodrii on 19/07/2018.
 */
public final class QueryUtilsFilms {

    private static final String LOG_TAG = QueryUtilsFilms.class.getSimpleName();
    final static String NO_CONTRIBUTOR = "Unknown";
    private static final String URL = "https://api.themoviedb.org/3/search/movie?api_key=c44f3d48c47012b24473934393cf026c&language=es-ES&query=";
    private static final String URL_2 = "&page=1&include_adult=false";

    public static List<Pelicula> fetchPeliculas(String name) throws UnsupportedEncodingException {
        URL url = createURL(URL + URLEncoder.encode(name, "UTF-8") + URL_2);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println(LOG_TAG + "Error closing input stream");
        }

        List<Pelicula> peliculas = extractPeliculas(jsonResponse);
        return peliculas;
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

    private static List<Pelicula> extractPeliculas(String PeliculaJSON) {

        List<Pelicula> peliculas = new ArrayList<>();
        String title;
        String path;
        String overview;
        // Si la respuesta JSON estÃ¡ vacÃ­a devuelvo null, pues no hay datos
        // asociados a esa id
        if (PeliculaJSON.length() == 0) {
            return null;
        } else {
            try {

                JSONObject root = new JSONObject(PeliculaJSON);
                JSONArray results = root.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject pelicula = results.getJSONObject(i);
                    title = pelicula.getString("title");
                    path = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + pelicula.getString("poster_path");
                    overview = pelicula.getString("overview");
                    peliculas.add(new Pelicula(title, path, overview));
                }

            } catch (JSONException e) {
                System.out.println("QueryUtils: Problem parsing the Pelicula JSON results " + e);
            }
            return peliculas;
        }
    }

//Establece la conexiÃ³n Http
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
