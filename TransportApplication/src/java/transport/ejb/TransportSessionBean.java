/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import transport.entities.Location;

/**
 *
 * @author JOHN
 */
@Stateless
public class TransportSessionBean {
    @PersistenceContext
    EntityManager em;
    
    
    
    public void addLocation(Location l){
        
              em.persist(l);
              
        }
    
    
    
    
    
    public Location getOneLocation(String n){
        
        Location l=new Location();
        l=em.find(Location.class, n);
        return l;
        
    }
    
    
    
    
    
    public List<Location> getAllLocation(){
        Query q = em.createNamedQuery("location.findlocation");
        List list = q.getResultList();
        q.getResultList();

        return list;
        
    }
    
    


}

    

