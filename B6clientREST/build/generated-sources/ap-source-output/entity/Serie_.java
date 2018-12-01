package entity;

import entity.HasEntrega;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-01T13:00:32")
@StaticMetamodel(Serie.class)
public class Serie_ { 

    public static volatile SingularAttribute<Serie, String> descripcion;
    public static volatile SingularAttribute<Serie, String> categoria;
    public static volatile SingularAttribute<Serie, String> titulo;
    public static volatile SingularAttribute<Serie, Integer> valoracion;
    public static volatile SingularAttribute<Serie, Integer> id;
    public static volatile CollectionAttribute<Serie, HasEntrega> hasEntregaCollection;

}