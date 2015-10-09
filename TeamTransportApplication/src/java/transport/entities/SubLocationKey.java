/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author JOHN
 */
public class SubLocationKey implements Serializable{
    
    private int locationId;
    private int subLocationId;
    

    public SubLocationKey() {
    }

    public SubLocationKey(int locationId, int subLocationId) {
        this.locationId = locationId;
        this.subLocationId = subLocationId;
    }
    

    

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SubLocationKey))
       return false;
       if(obj==this)
           return true;
       
       return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

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

    
    
}
