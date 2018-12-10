/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.Pelicula;
import utils.QueryUtilsFilms;

/**
 *
 * @author Rodrii
 */
@Named(value = "heroeManagedBean")
@RequestScoped
public class heroeManagedBean {
    private String name;
    private List<Pelicula> peliculas;

    /**
     * Creates a new instance of heroeManagedBean
     */
    public heroeManagedBean() {
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
    

    public void onParameterReceived() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<Pelicula> peliculas  = new ArrayList<>();
        /*MessageDigest md = MessageDigest.getInstance("MD5");
        String url = URL + URLEncoder.encode(name, "UTF-8") + URL_2 + "&ts=" + ts + "&hash=" +  md.digest((ts+publicKey+privateKey).getBytes()).toString();*/
        peliculas = QueryUtilsFilms.fetchPeliculas(this.name);
        this.peliculas = peliculas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
