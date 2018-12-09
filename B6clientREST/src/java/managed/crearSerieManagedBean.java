/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.HasUsuarioClient;
import client.SerieClient;
import client.UsuarioClient;
import entity.Entrega;
import entity.HasUsuario;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import entity.Serie;
import entity.Usuario;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
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
    private Usuario usuario;
    private UsuarioClient usuarioClient;
    private HasUsuarioClient hasUsuarioClient;

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
        
        usuarioClient = new UsuarioClient();
        //cuando creo la serie le asigno el usuario
        Response r = this.usuarioClient.findUsuarioConectado_XML(Response.class);
        if (r.getStatus() == 200) {
            this.usuario = r.readEntity(Usuario.class);
        } else {
            System.out.println("Error al obtener el usuario conectado.");
            //no debería dar error
        }
        
        //busco la serie que acabo de crear
        
        Serie nuevaSerie;
        Response r2 = serieCliente.findAll_XML(Response.class);
        List<Serie> listaSeries;
        
        if (r2.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>() {
            };
            List<Serie> allSeries = r2.readEntity(genericType);
            listaSeries = allSeries;
        } else {
            listaSeries = new ArrayList<Serie>();
        }
        
        //busco la entrega que acabo de crear para obtener el id y
        //poder recargarla
        
        nuevaSerie = listaSeries.get(listaSeries.size()-1);
        HasUsuario hasUsuario= new HasUsuario();
        
        hasUsuario.setIdSerie(nuevaSerie);
        hasUsuario.setIdUsuario(usuario);
        
        hasUsuarioClient = new HasUsuarioClient();
        this.hasUsuarioClient.create_XML(hasUsuario);
        
       

        return "misSeries?refresh=1&faces-redirect=true";
    }
}
