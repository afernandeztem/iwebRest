/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author Rodrii
 */
public class Pelicula {
    
    private String title;
    private String path;
    private String overview;

    public Pelicula(String title, String path, String overview) {
        this.title = title;
        this.path = path;
        this.overview = overview;
    }
    
    public Pelicula(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    
    
}
