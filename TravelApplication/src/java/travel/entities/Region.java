/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQuery(name="region.findregion",query = "select r from Region r")
@Table(name="TravelRegion")
@XmlRootElement(name="REGIONS")
@XmlAccessorType(XmlAccessType.FIELD)
public class Region implements Serializable {
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name="RId")
    @XmlElement(name="RID")
    private int rId;
    
    @Column(name="Region")
    @XmlElement(name="REGION",nillable=true)
    private String region;
    
    @XmlElement(name="PLACES")
    @OneToMany(orphanRemoval=true,cascade={CascadeType.ALL})
    @JoinColumn(name="RId")
    private List<Place> place;

    /**
     * @return the rId
     */
    public int getrId() {
        return rId;
    }

    /**
     * @param rId the rId to set
     */
    public void setrId(int rId) {
        this.rId = rId;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the place
     */
    public List<Place> getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(List<Place> place) {
        this.place = place;
    }
    
}
