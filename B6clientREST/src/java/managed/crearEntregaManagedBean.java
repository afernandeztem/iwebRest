/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import entity.Entrega;
import entity.Serie;
import entity.HasEntrega;

import client.EntregaClient;
import client.HasEntregaClient;
import client.SerieClient;
import java.util.ArrayList;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francis
 */
@Named(value = "crearEntregaManagedBean")
@RequestScoped
public class crearEntregaManagedBean {

    /**
     * Creates a new instance of crearEntregaManagedBean
     */
    public crearEntregaManagedBean() {
    }
    
    private int idEntrega;
    
    private HasEntrega hasEntrega;
    private SerieClient serieClient;
    private EntregaClient entregaClient;
    private HasEntregaClient hasEntregaClient;

    public HasEntrega getHasEntrega() {
        return hasEntrega;
    }

    public void setHasEntrega(HasEntrega hasEntrega) {
        this.hasEntrega = hasEntrega;
    }
    private List<Serie> series;

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
    
    private Serie serie;
    private String serieId;
    private int idserie;

    public int getIdserie() {
        return idserie;
    }

    public void setIdserie(int idserie) {
        this.idserie = idserie;
    }

    public String getSerieId() {
        return serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    private Entrega entrega;
    private String anotacion;
    private Date fecha_entrega;
    
 
    public String navegarCrearEntrega(){
         obtenerSeries();
         return "crearEntrega";
    }


    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }
    
    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

   
    public String crear() throws DatatypeConfigurationException{
        
        entregaClient= new EntregaClient();
        serieClient= new SerieClient();
        hasEntregaClient = new HasEntregaClient();
        
        entrega= new Entrega();
        
        if(!anotacion.equals("")){
           entrega.setAnotacion(anotacion);
        }
       
        
        if(fecha_entrega!=null){
        
        entrega.setFechaEntrega(fecha_entrega);
        }
        
        //creo la entrega
        this.entregaClient.create_XML(entrega);
        
        //busco la entrega en la bd 
        
        Entrega nuevaEntrega;
        Response r = this.entregaClient.findAll_XML(Response.class);
        List<Entrega> listaEntrega;
        
        if (r.getStatus() == 200) {
            GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = r.readEntity(genericType);
            listaEntrega = allEntregas;
        } else {
            listaEntrega = new ArrayList<Entrega>();
        }
        
        //busco la entrega que acabo de crear para obtener el id y
        //poder recargarla
        
        nuevaEntrega = listaEntrega.get(listaEntrega.size()-1);
        
        this.hasEntrega= new HasEntrega();
        
        Response rSerie= this.serieClient.find_XML(Response.class, serieId);
       
        if (rSerie.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            this.serie = rSerie.readEntity(genericType);
            
        }
      
        hasEntrega.setIdSerie(serie);
        hasEntrega.setIdEntrega(nuevaEntrega);
        
        this.hasEntregaClient.create_XML(hasEntrega);
       
       
        return "index?faces-redirect=true";
            
    }
    
    
     
    
    private void obtenerSeries() {
        
        this.serieClient = new SerieClient();
        Response r = this.serieClient.findAll_XML(Response.class);
        
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>() {
            };
            List<Serie> allSeries = r.readEntity(genericType);
            this.series = allSeries;
        } else {
            this.series = new ArrayList<Serie>();
        }
        
    }
}
