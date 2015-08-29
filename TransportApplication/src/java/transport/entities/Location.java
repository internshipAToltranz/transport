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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JOHN
 */
@Entity
@Table(name="transportLocation")
@NamedQuery(name="location.findlocation",query = "select l from Location l")
@XmlRootElement(name="LOCATIONS")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {
    
    @Id
    @XmlElement(name="LOCATIONID")
    @Column(name="locationId")
    private String locationId;
    
    @XmlElement(name="LOCATIONNAME")
    @Column(name="locationName")
    private String locationName;
    
    @XmlElement(name="SUBLOCATION")
    @OneToMany(orphanRemoval=true,cascade={CascadeType.ALL})
    @JoinColumn(name="locationId")
    private List<SubLocations> subLocations;

    /**
     * @return the locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(String locationId) {
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
     * @return the subLocations
     */
    public List<SubLocations> getSubLocations() {
        return subLocations;
    }

    /**
     * @param subLocations the subLocations to set
     */
    public void setSubLocations(List<SubLocations> subLocations) {
        this.subLocations = subLocations;
    }
}
