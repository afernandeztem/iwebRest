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
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import model.superHero;
import utils.QueryUtilsSuperHero;

/**
 *
 * @author Rodrii
 */
@Named(value = "heroeManagedBean")
@RequestScoped
public class heroeManagedBean {

    private static final String URL = "https://gateway.marvel.com:443/v1/public/characters?name=";
    private static final String URL_2 = "&apikey=63605a09865bc7f7e9b8b75792c19804";
    private String ts;
    private String publicKey = "63605a09865bc7f7e9b8b75792c19804";
    private String privateKey = "d03184de68b4df8feeda3fff72d8e7927953df41";

    private String name;

    /**
     * Creates a new instance of heroeManagedBean
     */
    public heroeManagedBean() {
    }

    public void onParameterReceived() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        superHero heroe = new superHero();
        ts = Integer.toString(new Random(System.currentTimeMillis()).nextInt());
        /*MessageDigest md = MessageDigest.getInstance("MD5");
        String url = URL + URLEncoder.encode(name, "UTF-8") + URL_2 + "&ts=" + ts + "&hash=" +  md.digest((ts+publicKey+privateKey).getBytes()).toString();*/
        MessageDigest md = MessageDigest.getInstance("MD5");
        String toHash = ts + privateKey + publicKey;
        String hash = new BigInteger(1, md.digest(toHash.getBytes())).toString(16);
        String url = URL + URLEncoder.encode(name, "UTF-8") + URL_2 + "&ts=" + ts + "&hash=" +  hash;
        System.out.println("yeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(url);
        heroe = QueryUtilsSuperHero.fetchDatos(url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
