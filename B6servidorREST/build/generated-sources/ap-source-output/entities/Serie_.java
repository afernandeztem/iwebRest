package entities;

import entities.HasEntrega;
import entities.HasUsuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-04T12:01:10")
@StaticMetamodel(Serie.class)
public class Serie_ { 

    public static volatile SingularAttribute<Serie, String> descripcion;
    public static volatile SingularAttribute<Serie, String> categoria;
    public static volatile SingularAttribute<Serie, String> imagen;
    public static volatile CollectionAttribute<Serie, HasUsuario> hasUsuarioCollection;
    public static volatile SingularAttribute<Serie, String> titulo;
    public static volatile SingularAttribute<Serie, Integer> valoracion;
    public static volatile SingularAttribute<Serie, Integer> id;
    public static volatile CollectionAttribute<Serie, HasEntrega> hasEntregaCollection;

}