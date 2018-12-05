/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Rodrii
 */
public class superHero {
    
    private String name;
    private String path;
    List<String> comics;

    public superHero(String name, String path, List<String> comics) {
        this.name = name;
        this.path = path;
        this.comics = comics;
    }
    
    public superHero(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getComics() {
        return comics;
    }

    public void setComics(List<String> comics) {
        this.comics = comics;
    }
    
    
    
}
