/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.EntregaClient;
import client.HasEntregaClient;
import client.UsuarioClient;
import entity.Entrega;
import entity.Serie;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author Francis
 */
@Named(value = "editarEntregaManagedBean")
@RequestScoped
public class editarEntregaManagedBean {

    /**
     * Creates a new instance of editarEntregaManagedBean
     */
    public editarEntregaManagedBean() {
    }
    
    private Entrega entrega;
    private EntregaClient entregaClient;

    //Atributos que vienen desde el .xhtml
    private int id;
    private String anotacion;
    private Date fecha_entrega;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public Date getFecha_entrega() {
        return fecha_entrega;
    }
    
    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }
    
    public void onParameterReceived() {
        
        this.entregaClient = new EntregaClient();
        
        Response rSerie= this.entregaClient.find_XML(Response.class, Integer.toString(id));
       
        if (rSerie.getStatus() == 200) {
            GenericType<Entrega> genericType = new GenericType<Entrega>() {
            };
            this.entrega = rSerie.readEntity(genericType);
            
        }
        
        
        if (entrega.getAnotacion() != null) {
            this.setAnotacion(entrega.getAnotacion());
        } else {
            this.setAnotacion("");
        }
        if (entrega.getFechaEntrega() != null) {
            Date date = entrega.getFechaEntrega();
            this.setFecha_entrega(date);
        } else {
            this.setFecha_entrega(null);
        }        
        
    }
    
    public String editar() throws DatatypeConfigurationException {
        
        this.entregaClient = new EntregaClient();
        
         Response rSerie= this.entregaClient.find_XML(Response.class, Integer.toString(id));
       
        if (rSerie.getStatus() == 200) {
            GenericType<Entrega> genericType = new GenericType<Entrega>() {
            };
            this.entrega = rSerie.readEntity(genericType);
            
        }
        
        entrega.setId(id);
        entrega.setAnotacion(anotacion);
        /*String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.format(fecha_entrega);*/
        
        if (fecha_entrega != null) {
             
            entrega.setFechaEntrega(fecha_entrega);
        } else {
            entrega.setFechaEntrega(null);
        }
        
        
        entregaClient.edit_XML(entrega, entrega.getId().toString());
        
        //return "misEntregas.jsf?refresh=1";
        //return "misSeries";
        //return msmb.navegarSeies();
        //return "index";         
        return "editarEntrega.jsf?id=" + entrega.getId();
        
    }
    
    public boolean esMiEntrega (int idEntrega){
        System.out.println("Comprobando dueño de la serie con id: " + idEntrega);
        UsuarioClient userClient = new UsuarioClient();
        String idSerieStr = "" + idEntrega;
        
        HasEntregaClient hasEntregaClient = new HasEntregaClient();
        Serie serie = null;
        
        Response rEntrega = hasEntregaClient.findSerieConEntrega_XML(Response.class, Integer.toString(idEntrega));
        if (rEntrega.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            serie= rEntrega.readEntity(genericType);
        }
        
        String r = userClient.findBoolSerieDeUsuario(Integer.toString(serie.getId()));
        
       
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
