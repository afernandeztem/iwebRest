/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;


import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class SessionManagedBean implements Serializable{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        System.out.println("Token cambiado! token: " + token);
        this.token = token;
    }
    
    
    
}