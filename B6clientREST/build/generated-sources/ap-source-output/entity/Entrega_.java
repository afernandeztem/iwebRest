package entity;

import entity.HasEntrega;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-09T20:37:18")
@StaticMetamodel(Entrega.class)
public class Entrega_ { 

    public static volatile SingularAttribute<Entrega, Date> fechaEntrega;
    public static volatile SingularAttribute<Entrega, Integer> id;
    public static volatile CollectionAttribute<Entrega, HasEntrega> hasEntregaCollection;
    public static volatile SingularAttribute<Entrega, String> anotacion;

}