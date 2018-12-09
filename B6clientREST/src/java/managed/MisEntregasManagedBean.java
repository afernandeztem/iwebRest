/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Entrega;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import client.EntregaClient;
import client.HasEntregaClient;
import client.SerieClient;
import client.UsuarioClient;
import entity.Serie;
import entity.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

@Named(value = "misEntregasManagedBean")
@RequestScoped
public class MisEntregasManagedBean {

    private List<Entrega> entregas;
    private String busqueda;
    private Date t1, t2;
    private List<Entrega> resultadoBusqueda = null;
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
     * Creates a new instance of MisEntregasManagedBean
     */
    public MisEntregasManagedBean() {
    }

    public String navegarEntregas() {
        obtenerEntregas();
        return "misEntregas";
    }

    private void obtenerEntregas() {
        EntregaClient client = new EntregaClient();
        Response r = client.findAll_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = r.readEntity(genericType);
            this.entregas = allEntregas;
        } else {
            this.entregas = new ArrayList<Entrega>();
        }
    }

    public String buscar() {
        EntregaClient client = new EntregaClient();
        Response r = client.filtroPorAnotacion_XML(Response.class, this.busqueda);
        if (r.getStatus() == 200) {
            GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = r.readEntity(genericType);
            this.entregas = allEntregas;
        } else {
            this.entregas = new ArrayList<Entrega>();
        }
        return "misEntregas";
    }

    public String filtrarPorPeriodoDeTiempo() {
        EntregaClient client = new EntregaClient();
        
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tiempo1 = dateFormat.format(t1);
            String tiempo2 = dateFormat.format(t2);
        Response rTiempo = client.filtrarPorTiempo_XML(Response.class, tiempo1, tiempo2);
        if (rTiempo.getStatus() == 200) {
            GenericType<List<Entrega>> genericType = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = rTiempo.readEntity(genericType);
            this.entregas = allEntregas;
        } else {
            this.entregas = new ArrayList<Entrega>();
        }
         
        return "misEntregas";
    }

    public List<Entrega> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }

    public Date getT1() {
        return t1;
    }

    public void setT1(Date t1) {
        this.t1 = t1;
    }

    public Date getT2() {
        return t2;
    }

    public void setT2(Date t2) {
        this.t2 = t2;
    }

    public void setResultadoBusqueda(List<Entrega> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String entregasAsociadasASerie(int id) {
        //this.entregas = this.findEntregasConIdSerie(id);
        return "misEntregas";
    }

    public String getSerieconEntrega(Entrega entrega) {
        HasEntregaClient client = new HasEntregaClient();
        Response r = client.findSerieConEntrega_XML(Response.class, entrega.getId().toString());
        if (r.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>() {
            };
            return r.readEntity(genericType).getTitulo();
        }
        return null;
    }

    public void onParameterReceived() {
        // En progreso, Edu.
        EntregaClient clienteEntrega = new EntregaClient();
        Response r = clienteEntrega.find_XML(Response.class, String.valueOf(eliminate));
        if (this.eliminate != 0) {
            if (r.getStatus() == 200) {
                GenericType<Entrega> genericType = new GenericType<Entrega>() {
                };
                Entrega e = r.readEntity(genericType);
                clienteEntrega.remove(e.getId().toString());
                obtenerEntregas();
                this.eliminate = 0;
            }
        }
        
        if (this.refresh == 1) {
            obtenerEntregas();
            // this.refresh = false;
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
    
    public String obtenerMisEntregas() {
        
        Usuario usuario = null;
        List<Serie> series = new ArrayList<>();
        SerieClient client = new SerieClient();
        UsuarioClient userClient = new UsuarioClient();
        //Response r = client.findAll_XML(Response.class);
        List<Entrega> misEntregas= new ArrayList<>();
        HasEntregaClient hasEntregaClient = new HasEntregaClient();
        
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
            series = allSeries;
        //} else {
        if (series == null) {
            System.out.println("No hay series asociadas al usuario");
            series = new ArrayList<Serie>();
        }
        //}
        for(Serie s: series){
            
            Response rEntrega= hasEntregaClient.findEntregaConIdSerie_XML(Response.class, Integer.toString(s.getId()));
       
       if (rEntrega.getStatus() == 200) {
            GenericType<List<Entrega>> genericType2 = new GenericType<List<Entrega>>() {
            };
            List<Entrega> allEntregas = rEntrega.readEntity(genericType2);
            if(entregas==null){
                this.entregas = allEntregas;
            }else{
                this.entregas.addAll(allEntregas);
            }
        }
            
        }
        
        return "misEntregasOnly";
    }

}
