/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="TravelPlace")
@XmlRootElement(name="PLACES")
@XmlAccessorType(XmlAccessType.FIELD)
public class Place implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="PId")
    @XmlElement(name="PID")
    private int pId;
    
    @Column(name="RId")
    //@XmlElement(name="RID")
    private String rId;
    
    @Column(name="Place")
    @XmlElement(name="PLACE",nillable=true)
    private String place;

    /**
     * @return the pId
     */
    public int getpId() {
        return pId;
    }

    /**
     * @param pId the pId to set
     */
    public void setpId(int pId) {
        this.pId = pId;
    }

    /**
     * @return the rId
     */
    public String getrId() {
        return rId;
    }

    /**
     * @param rId the rId to set
     */
    public void setrId(String rId) {
        this.rId = rId;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    
}
