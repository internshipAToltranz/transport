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
    
    int subLocationId;
    String locationId;

    public SubLocationKey(int subLocationId, String locationId) {
        this.subLocationId = subLocationId;
        this.locationId = locationId;
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
    
    
}
