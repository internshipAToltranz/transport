/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOHN
 */
@Entity
@Table(name="transportLocation")
@NamedQuery(name="location.findlocation",query = "select l from Location l")
@XmlRootElement(name="LOCATIONS")
@XmlAccessorType(XmlAccessType.FIELD)
@SequenceGenerator(name="seq", initialValue=1, allocationSize=1000000000)
public class Location implements Serializable {      
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @XmlElement(name="LOCATIONID")
    @Column(name="locationId")
    private int locationId;
    
    @XmlElement(name="LOCATIONNAME")
    @Column(name="locationName")
    private String locationName;
    
    @XmlElement(name="SUBLOCATION")
    @OneToMany(orphanRemoval=true,cascade={CascadeType.ALL})
    @JoinColumn(name="locationId")
    private List<SubLocation> subLocation;

    /**
     * @return the locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return the subLocation
     */
    public List<SubLocation> getSubLocation() {
        return subLocation;
    }

    /**
     * @param subLocation the subLocation to set
     */
    public void setSubLocation(List<SubLocation> subLocation) {
        this.subLocation = subLocation;
    }

    
}
