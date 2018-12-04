package entities.service;

import entities.Serie;
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
@Path("entities.serie")
public class SerieFacadeREST extends AbstractFacade<Serie> {

    @PersistenceContext(unitName = "B6servidorRESTPU")
    private EntityManager em;

    public SerieFacadeREST() {
        super(Serie.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Serie entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Serie entity) {
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
    public Serie find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("serieConTitulo/{titulo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findConTitulo(@PathParam("titulo") String titulo) {
        
         Query q;
        q = this.em.createQuery("SELECT s FROM Serie s WHERE s.titulo LIKE CONCAT('%',:clave, '%')");
        q.setParameter("clave", titulo);
        List<Serie> result = (List) q.getResultList();
        
        return result;
    }
    
   
    @GET
    @Path("mejorSerie")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Serie getBestValSerie() {
        
        Query q;
        q = this.em.createQuery("SELECT s FROM Serie s WHERE s.valoracion IS NOT NULL ORDER BY s.valoracion DESC");
        List<Serie> lista = (List) q.getResultList();
        if (lista.size()>0) {
            return lista.get(0);
        } else {
            return null;
        }
    }
    
    
    
    @GET
    @Path("peorSerie")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Serie getWorstValSerie() {
         Query q;
        q = this.em.createQuery("SELECT s FROM Serie s WHERE s.valoracion IS NOT NULL ORDER BY s.valoracion ASC");
        List<Serie> lista = (List) q.getResultList();
        if (lista.size()>0) {
            return lista.get(0);
        } else {
            return null;
        }
       
    }
    
    
}