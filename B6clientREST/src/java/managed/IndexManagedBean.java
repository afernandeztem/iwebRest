/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Usuario;
import client.UsuarioClient;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author UX390U
 */
@Named(value = "indexManagedBean")
@RequestScoped
public class IndexManagedBean {
    
    private String nombre;
    private String urlFoto;
    private Usuario usuario;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    public IndexManagedBean() {
    }
    
    public Usuario obtenerUsario(){
        UsuarioClient client = new UsuarioClient();
        Response r = client.findUsuarioConectado_XML(Response.class);
        
        
        Usuario usuarioObtenido= r.readEntity(Usuario.class);
        if(usuarioObtenido==null){
            System.out.println("Usuario no encontrado");
        }
            this.usuario = usuarioObtenido;
            this.nombre = usuarioObtenido.getNombre();
            this.urlFoto= usuarioObtenido.getUrlFoto();
        
        return usuarioObtenido;
    }
    
    
}
