/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.service;

import entities.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author user
 */
@Stateless
@Path("entities.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "B6servidorRESTPU")
    private EntityManager em;
    
    

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("usuarioPorEmail/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuario findConEmail(@PathParam("email") String email) {

        Query q;
        q = this.em.createQuery("SELECT u FROM Usuario u WHERE UPPER(u.email) = UPPER(:clave)");
        q.setParameter("clave", email);

        if (q.getResultList().size() > 0) {
            Usuario result = (Usuario) q.getResultList().get(0);

            return result;
        } else {
            return null;
        }
    }

    @POST
    @Path("/welcome")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(UsuarioProxy proxy, @Context HttpServletRequest req) {
        HttpSession session= req.getSession(true);

        Usuario u = this.findConEmail(proxy.email);
        if (u != null) {
            //actualizo la información del usuario existente

            u.setNombre(proxy.name);
            u.setUrlFoto(proxy.imageUrl);
            u.setEmail(proxy.email);
            this.edit(u);

        } else {

            Usuario nuevo = new Usuario();
            //No podemos setear la id. La id se autogenera
            //nuevo.setId(proxy.id);
            nuevo.setNombre(proxy.name);
            nuevo.setUrlFoto(proxy.imageUrl);
            nuevo.setEmail(proxy.email);

            this.create(nuevo);
        }

        
        
        return "ok";
    }

    public static class UsuarioProxy implements Serializable {

        public int id;
        public String name;
        public String imageUrl;
        public String email;
        public String token;

        public UsuarioProxy(int id, String name, String imageUrl, String email, String token) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.email = email;
            this.token = token;
        }

        public UsuarioProxy() {
            // Nada, ha habido alguna cosa rara al pasar los parámetros.
        }

        UsuarioProxy(Usuario u) {
            this.id = u.getId();
            this.name = u.getNombre();
            this.imageUrl = u.getUrlFoto();
            this.email = u.getEmail();
            this.token = token;
        }

    }

    /*
    @POST
    @Path("/preferencias")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<CategoriaProxy> nuevaPreferenciaUsuario(CategoriaProxy categoria, @HeaderParam("bearer") String token) throws NotAuthenticatedException, AgendamlgNotFoundException {
        String idUsuario = TokensUtils.getUserIdFromJwtTokenOrThrow(TokensUtils.decodeJwtToken(token));
        Usuario usuario = usuarioFacade.find(idUsuario);
        categoriaFacade.afegirPreferenciaUsuari(usuario, categoria.id);
        return categoriaFacade.buscarPreferenciasUsuario(usuario).stream().map(CategoriaProxy::new).collect(Collectors.toList());
    }
     */
 /*id: profile.getId(),
      name: profile.getName(),
      imageUrl: profile.getImageUrl(),
      email: profile.getEmail(),
      token: googleUser.getAuthResponse().id_token,*/
}
