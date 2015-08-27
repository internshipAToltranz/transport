/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import travel.entities.Region;

/**
 *
 * @author JOHN
 */
@Stateless
public class TravelSessionBean {
    
    @PersistenceContext
    EntityManager em;
    
    public boolean addRegion(Region r){
        
       boolean regionexist=false;
       List<Region> regionlist=getAllRegion();
        for(Region r1 : regionlist)
        {
            if (r1.getrId()== r.getrId()){
                
               regionexist=true;
              }
            break;
        }
        if(!regionexist){
            regionlist.add(r);
              em.persist(r);
              return true;
        }
        return false;
        
    }
    
    public Region getOneRegion(int n){
        boolean regionexist = false;
        List<Region> regionlist=getAllRegion();
        for(Region r2 : regionlist)
          
          {
            if (r2.getrId() == n) {
                regionexist = true;
                em.find(Region.class, n);
                return r2;
            
          }
        
        
     }
        return null;
    }
    
    
    public List<Region> getAllRegion(){
        
        
          Query q = em.createNamedQuery("region.findregion");
          List list = q.getResultList();
          q.getResultList();
          return list;
        
        }
    
}
