/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.SerieClient;
import entity.Entrega;
import entity.Serie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Rodrii
 */
@Named(value = "misSeriesManagedBean")
@RequestScoped
public class MisSeriesManagedBean implements Serializable {


    private List<Serie> series;
    /*private String busqueda;
    private List<Serie> resultadoBusqueda = null;
    private Integer refresh = 0;
    private int eliminate = 0;

    
    
    public int getEliminate() {
        return eliminate;
    }

    public void setEliminate(int eliminate) {
        this.eliminate = eliminate;
    }
    

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }
    */

    /**
     * Creates a new instance of IndexManagedBean
     */
    public MisSeriesManagedBean() {
    }

    
    /*@PostConstruct
    public void init() {
        this.series = resultadoBusqueda;
        //this.obtenerSeries();
    }*/
    public String navegarSeies() {
        obtenerSeries();
        return "misSeries";
    }

    
    private void obtenerSeries() {
        SerieClient client = new SerieClient();
        Response r = client.findAll_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> allSeries = r.readEntity(genericType);
            this.series = allSeries;
        }else{
            this.series = new ArrayList<Serie>();
        }
    }


    /*
    public String buscar() {
        this.series = this.findSerieConTitulo(this.busqueda);
        return "misSeries";
    }

    public String bestSerie() {
        if (this.series == null) {
            this.series = new ArrayList<>();
        } else {
            this.series.clear();
        }
        this.series.add(this.getBestValSerie());
        return "misSeries";
    }

    public String worstSerie() {
        if (this.series == null) {
            this.series = new ArrayList<>();
        } else {
            this.series.clear();
        }
        this.series.add(this.getWorstValSerie());
        return "misSeries";
    }

    public List<Serie> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(List<Serie> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public String getBusqueda() {
        return busqueda;
    }

    
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
*/
    public List<Serie> getSeries() {
        return series;
    }
/*
    public void onParameterReceived() {
        // En progreso, Edu.
			if(this.eliminate != 0){
				List<Entrega> entregasEliminar = this.findEntregasConIdSerie(eliminate);
				for(Entrega e : entregasEliminar){
					this.removeEntrega(e);
				}
				this.removeSerie(this.findSerieConId(eliminate));
				obtenerSeries();
				this.eliminate = 0;
			}
        System.out.println("WAKI Hola en series he recibido un parametro BIENN");
        if (this.refresh == 1) {
            System.out.println("WAKI HE ENTRADO EN EL IF DE PARAMETER");
            obtenerSeries();
            // this.refresh = false;
        } else {
            System.out.println("WAKI Soy menor que cero sorry xd serie");

        }
        
        // ...

    }

    */
}
