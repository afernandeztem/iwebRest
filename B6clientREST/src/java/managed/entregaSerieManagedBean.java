/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.HasEntregaClient;
import entity.Entrega;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francis
 */
@Named(value = "entregaSerieManagedBean")
@RequestScoped
public class entregaSerieManagedBean {

    /**
     * Creates a new instance of entregaSerieManagedBean
     */
    public entregaSerieManagedBean() {
    }
    
    private int idSerie;
    private List<Entrega> entregas;
    private HasEntregaClient hasEntregaClient;

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }
    
    
    public void onParameterReceived(){
        
        this.hasEntregaClient= new HasEntregaClient();
        
       
        Response r= this.hasEntregaClient.findEntregaConIdSerie_XML(Response.class, Integer.toString(idSerie));
       
       if (r.getStatus() == 200) {
            GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = r.readEntity(genericType);
            this.entregas = allEntregas;
        } else {
            this.entregas = new ArrayList<Entrega>();
        }
       
    }
    
    public String cambiarFormato(Date date){
        
        String d=null;
        
        if(date!=null){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        d = dateFormat.format(date);
        }
        
        return d;
    }
    
}
