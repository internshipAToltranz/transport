package transport.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import transport.entities.SubLocation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-19T15:12:37")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile SingularAttribute<Location, String> locationName;
    public static volatile SingularAttribute<Location, Integer> locationId;
    public static volatile ListAttribute<Location, SubLocation> subLocation;

}