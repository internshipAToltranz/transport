package travel.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import travel.entities.Place;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-26T12:00:54")
@StaticMetamodel(Region.class)
public class Region_ { 

    public static volatile ListAttribute<Region, Place> place;
    public static volatile SingularAttribute<Region, Integer> rId;
    public static volatile SingularAttribute<Region, String> region;

}