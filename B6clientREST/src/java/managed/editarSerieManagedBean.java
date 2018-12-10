/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.SerieClient;
import client.UsuarioClient;
import entity.Serie;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francis
 */
@Named(value = "editarSerieManagedBean")
@RequestScoped
public class editarSerieManagedBean {

    /**
     * Creates a new instance of editarSerieManagedBean
     */
    public editarSerieManagedBean() {
    }
    
    private Serie serie;
    private SerieClient serieClient;
    
    
    //Atributos que vienen desde el .xhtml
    private int id;
    private String titulo;
    private String categoria;
    private String descripcion;
    private String valoracion;
  

    public Serie getSerie() {
        return serie;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }  
    
    public String goEdit (Serie s) {
        //System.out.println("Ha llegado a go Edit");
        this.serie = s;
        //System.out.println("Serie: " + serie.getTitulo());
        return "editarSerie?faces-redirect=true";
    } // :)

    public void onParameterReceived(){
        this.serieClient = new SerieClient();
        Response rSerie= this.serieClient.find_XML(Response.class, Integer.toString(id));
       
        if (rSerie.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            this.serie = rSerie.readEntity(genericType);
            
        }
        //título y categoría no pueden ser nulos
        this.setTitulo(serie.getTitulo());
        this.setCategoria(serie.getCategoria());
        
        if(serie.getDescripcion() != null) {
          this.setDescripcion(serie.getDescripcion());
        } else {
          this.setDescripcion("");
        }
        if(serie.getValoracion() != null) {
            this.setValoracion(serie.getValoracion().toString());
        }
    }
    
    
    public String editar () {
        
        this.serieClient = new SerieClient();
        
        System.out.println("PRE TESTING titulo val: " + titulo);
        //System.out.println("Testing s: " + s.getTitulo());
        
         Response rSerie= this.serieClient.find_XML(Response.class, Integer.toString(id));
       
        if (rSerie.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            this.serie = rSerie.readEntity(genericType);
            
        }
        serie.setId(id);
        serie.setTitulo(titulo);
        serie.setCategoria(categoria);
        serie.setDescripcion(descripcion);
        
        if(!valoracion.equalsIgnoreCase("vacio")){
        serie.setValoracion(Integer.parseInt(valoracion));
        } else {
            serie.setValoracion(null);
        }
        //Si es vacío, no la añado
        
        serieClient.edit_XML(serie, serie.getId().toString());
          
        //return "misSeries.jsf?refresh=1";
        //return "misSeries";
        //return msmb.navegarSeies();
        //return "index";         
        return "editarSerie.jsf?id=" + serie.getId();
        
    }
    
    public boolean esMiSerie (int idSerie){
        System.out.println("Comprobando dueño de la serie con id: " + idSerie);
        UsuarioClient userClient = new UsuarioClient();
        String idSerieStr = "" + idSerie;
        String r = userClient.findBoolSerieDeUsuario(idSerieStr);
        
       
        System.out.println("¿EsMiSerie? Respuesta:" + r);
        if (r.equals("ok")) {
            System.out.println("Sí, es mi serie, true");
            return true;
        } else {
            System.out.println("NO es mi serie, false");
            return false;
        }
        
           
    }
}
