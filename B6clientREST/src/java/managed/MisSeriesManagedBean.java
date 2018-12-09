/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.EntregaClient;
import client.SerieClient;
import client.HasEntregaClient;
import client.UsuarioClient;
import entity.Entrega;
import entity.Serie;
import entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceRef;
import utils.QueryUtilsUnsplash;

/**
 *
 * @author Rodrii
 */
@Named(value = "misSeriesManagedBean")
@RequestScoped
public class MisSeriesManagedBean implements Serializable {

    private List<Serie> series;
    private Usuario usuario;
    private String busqueda;
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

    public String obtenerMisSeries() {
        SerieClient client = new SerieClient();
        UsuarioClient userClient = new UsuarioClient();
        //Response r = client.findAll_XML(Response.class);
        
        Response r2 = userClient.findUsuarioConectado_XML(Response.class);
        if (r2.getStatus() == 200) {
            usuario = r2.readEntity(Usuario.class);
        } else {
            System.out.println("Error al obtener el usuario conectado.");
            //no debería dar error
        }
        
        Response r = userClient.findSeriesDeUsuario_XML(Response.class,usuario.getEmail());
        
        //if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>() {
            };
            List<Serie> allSeries = r.readEntity(genericType);
            this.series = allSeries;
        //} else {
        if (this.series == null) {
            System.out.println("No hay series asociadas al usuario");
            this.series = new ArrayList<Serie>();
        }
        //}
        
        return "misSeriesOnly";
    }
    
    private void obtenerSeries() {
        SerieClient client = new SerieClient();
        Response r = client.findAll_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>() {
            };
            List<Serie> allSeries = r.readEntity(genericType);
            this.series = allSeries;
        } else {
            this.series = new ArrayList<Serie>();
        }
    }

    public String buscar() {
        SerieClient client = new SerieClient();
        Response r = client.findConTitulo_XML(Response.class, this.busqueda);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>() {
            };
            List<Serie> allSeries = r.readEntity(genericType);
            this.series = allSeries;
        } else {
            this.series = new ArrayList<Serie>();
        }
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

    private Serie getBestValSerie() {
        SerieClient client = new SerieClient();
        Response r = client.getBestValSerie_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            return r.readEntity(genericType);
        }
        return null;
    }

    private Serie getWorstValSerie() {
        SerieClient client = new SerieClient();
        Response r = client.getWorstValSerie_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            return r.readEntity(genericType);
        }
        return null;
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

    public List<Serie> getSeries() {
        return series;
    }

    public void onParameterReceived() {
        // En progreso, Edu.
        EntregaClient clienteEntrega = new EntregaClient();
        SerieClient clienteSerie = new SerieClient();
        HasEntregaClient hasEntregaClient = new HasEntregaClient();
        if (this.eliminate != 0) {
            Response responseEntregas = hasEntregaClient.findEntregaConIdSerie_XML(Response.class, String.valueOf(eliminate));
            if (responseEntregas.getStatus() == 200) {
                GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
                };
                List<Entrega> entregasEliminar = responseEntregas.readEntity(genericType);
                for (Entrega e : entregasEliminar) {
                    clienteEntrega.remove(e.getId().toString());
                }
            }
            clienteSerie.remove(String.valueOf(eliminate));
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
    
    
    public void onParameterReceivedOnly() {
        // En progreso, Edu.
        EntregaClient clienteEntrega = new EntregaClient();
        SerieClient clienteSerie = new SerieClient();
        HasEntregaClient hasEntregaClient = new HasEntregaClient();
        if (this.eliminate != 0) {
            Response responseEntregas = hasEntregaClient.findEntregaConIdSerie_XML(Response.class, String.valueOf(eliminate));
            if (responseEntregas.getStatus() == 200) {
                GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
                };
                List<Entrega> entregasEliminar = responseEntregas.readEntity(genericType);
                for (Entrega e : entregasEliminar) {
                    clienteEntrega.remove(e.getId().toString());
                }
            }
            clienteSerie.remove(String.valueOf(eliminate));
            obtenerMisSeries();
            this.eliminate = 0;
        }
        System.out.println("WAKI Hola en series he recibido un parametro BIENN");
        if (this.refresh == 1) {
            System.out.println("WAKI HE ENTRADO EN EL IF DE PARAMETER");
            obtenerMisSeries();
            // this.refresh = false;
        } else {
            System.out.println("WAKI Soy menor que cero sorry xd serie");

        }

        // ...
    }

}
