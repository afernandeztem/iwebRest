/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.service;

import entities.Entrega;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author user
 */
@Stateless
@Path("entities.entrega")
public class EntregaFacadeREST extends AbstractFacade<Entrega> {

    @PersistenceContext(unitName = "B6servidorRESTPU")
    private EntityManager em;

    public EntregaFacadeREST() {
        super(Entrega.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Entrega entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Entrega entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Entrega find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrega> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrega> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    @GET
    @Path("hulks")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getHulks() {
        
       Query q;
        q = this.em.createQuery("SELECT e.anotacion FROM Entrega e WHERE UPPER(e.anotacion) LIKE UPPER('%hulk%')");

        return q.getResultList().toString();
    }
    
      @GET
    @Path("getFechaEntregaNotNull")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrega> getFechaEntregaNotNull() {
         Query q;
        q = this.em.createQuery("Select e FROM Entrega e WHERE e.fechaEntrega IS NOT NULL");
        return (List) q.getResultList();
    }

      @GET
    @Path("filtroAnotacion/{anotacion}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrega> filtroPorAnotacion(@PathParam("anotacion") String anotacion) {
        Query q = this.em.createQuery("SELECT e FROM Entrega e WHERE e.anotacion LIKE :anotacion");
        q.setParameter("anotacion", "%" + anotacion + "%");

        List<Entrega> lista = (List<Entrega>) q.getResultList();
        return lista;
    }

    @GET
    @Path("filtrarPorTiempo/{d1}/{d2}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrega> filtrarPorTiempo(@PathParam("d1") String d1, @PathParam("d2") String d2) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(d1);
        Date date2 = format.parse(d2);

        //No debemos usar Between porque compara de izquierda a derecha, si el año es igual no lo mira
        //Fuente https://stackoverflow.com/questions/5125076/sql-query-to-select-dates-between-two-dates
        Query q = this.em.createQuery("select e from Entrega e where e.fechaEntrega >= :d1 AND e.fechaEntrega <= :d2");

        q.setParameter("d1", date1);
        q.setParameter("d2", date2);

        List<Entrega> lista = (List) q.getResultList();
        return lista;
    }

    
}
