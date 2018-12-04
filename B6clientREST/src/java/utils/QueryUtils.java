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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by Rodrii on 19/07/2018.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    final static String NO_CONTRIBUTOR = "Unknown";
    private static final String URL = "https://api.unsplash.com/search/photos/?client_id=8aed9fd040bd00f28e43a883034bb7da3a0212a366987997c3fae40a5fef2145&query=";
    private static final String URL_FINAL = "https://source.unsplash.com/";
    private static final String TAM = "/290x160";

    // llamado para extraer tanto la Valoración como el la url del Póster
    public static String fetchImagenId(String queryParameter) {
        String requestUrl = URL + queryParameter;
        URL url = createURL(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println(LOG_TAG + "Error closing input stream");
        }

        String imagen = extractImagenId(jsonResponse);
        return imagen;
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

    // devuelve la id y el póster a partir de la respuesta JSON
    private static String extractImagenId(String PeliculaJSON) {

        String id = null;
        int width = -1;
        int height = 0;
        // Si la respuesta JSON está vacía devuelvo null, pues no hay datos
        // asociados a esa id
        if (PeliculaJSON.length() == 0) {
            return null;
        } else {
            try {

                JSONObject root = new JSONObject(PeliculaJSON);
                if (root.has("results")) {

                    JSONArray resultados = root.getJSONArray("results");
                    for (int i = 0; i < resultados.length() && width < height; i++) {
                        JSONObject primeraImagen = resultados.getJSONObject(i);
                        id = primeraImagen.getString("id");
                        width = primeraImagen.getInt("width");
                        height = primeraImagen.getInt("height");
                    }
                }

            } catch (JSONException e) {
                System.out.println("QueryUtils: Problem parsing the Pelicula JSON results " + e);
            }
            if (id == null) {
                return "assets/drawable/notfound.png";
            } else {
                return URL_FINAL + id + TAM;
            }
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
