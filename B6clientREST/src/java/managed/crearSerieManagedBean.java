/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.SerieClient;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import entity.Serie;
import java.io.UnsupportedEncodingException;
import utils.QueryUtilsUnsplash;

/**
 *
 * @author Francis
 */
@Named(value = "crearSerieManagedBean")
@RequestScoped
public class crearSerieManagedBean {

    /**
     * Creates a new instance of crearSerieManagedBean
     */
    public crearSerieManagedBean() {
    }

    private Serie serie;
    private SerieClient serieCliente;

    private int id;
    private String titulo;
    private String categoria;
    private String descripcion;
    private String valoracion;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String crear() throws UnsupportedEncodingException {

        serie = new Serie();

        serie.setTitulo(titulo);
        serie.setCategoria(categoria);

        if (descripcion.equals("")) {
            serie.setDescripcion(null);
        } else {
            serie.setDescripcion(descripcion);
        }

        if (valoracion.equals("vacio")) {
            serie.setValoracion(null);
        } else {
            serie.setValoracion(Integer.parseInt(valoracion));
        }

        if (!url.equals("")) {
            serie.setImagen(url);
        } else {
            serie.setImagen(QueryUtilsUnsplash.fetchImagenId(titulo));
        }

        serieCliente = new SerieClient();
        serieCliente.create_XML(serie);

        return "misSeries?refresh=1&faces-redirect=true";
    }
}
