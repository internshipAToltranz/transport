package transport.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import transport.entities.SubLocations;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-29T14:01:44")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile SingularAttribute<Location, String> locationName;
    public static volatile SingularAttribute<Location, String> locationId;
    public static volatile ListAttribute<Location, SubLocations> subLocations;

}