/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JOHN
 */
@Entity
@IdClass(SubLocationKey.class)
@Table(name="transportSubLocation")
@XmlRootElement(name="SUBLOCATION")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubLocations implements Serializable {
    
    @Id
    @XmlElement(name="LOCATIONID")
    @Column(name="locationId")
    private String locationId;
    
    @Id
    @XmlElement(name="SUBLOCATIONID")
    @Column(name="subLocationId")
    private int subLocationId;
    
  
    @XmlElement(name="SUBLOCATIONNAME")
    @Column(name="subLocationName")
    private String subLocationName;

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
     * @return the subLocationId
     */
    public int getSubLocationId() {
        return subLocationId;
    }

    /**
     * @param subLocationId the subLocationId to set
     */
    public void setSubLocationId(int subLocationId) {
        this.subLocationId = subLocationId;
    }

    /**
     * @return the subLocationName
     */
    public String getSubLocationName() {
        return subLocationName;
    }

    /**
     * @param subLocationName the subLocationName to set
     */
    public void setSubLocationName(String subLocationName) {
        this.subLocationName = subLocationName;
    }
}
