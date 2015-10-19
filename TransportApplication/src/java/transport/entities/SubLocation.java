/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
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
@IdClass(SubLocationKey.class)
@Table(name = "transportSubLocation")
@NamedQuery(name="sublocation.findsublocation",query = "select sl from SubLocation sl")
@XmlRootElement(name = "SUBLOCATION")
@XmlAccessorType(XmlAccessType.FIELD)
@SequenceGenerator(name="sseq", initialValue=1, allocationSize=1000000000)
public class SubLocation implements Serializable {

    @Id
    @XmlElement(name = "LOCATIONID")
    @Column(name = "locationId")
    private int locationId;

    @Id
    @XmlElement(name = "SUBLOCATIONID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sseq")
    @Column(name = "subLocationId")
    private int subLocationId;

    @XmlElement(name = "SUBLOCATIONNAME")
    @Column(name = "subLocationName")
    private String subLocationName;

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
   

