/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.ejb;

import transport.entities.Location;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import transport.entities.SubLocation;
import transport.entities.SubLocationKey;

/**
 *
 * @author JOHN
 */
@Stateless
public class TransportSessionBean {
    @PersistenceContext
    EntityManager em;
    
    
    
         public int addNewLocation(Location l){
             List<Location> locationList=getLocationList();
             boolean locationExists=false;
             
             for(Location l1: locationList)
             {
                if(l1.getLocationId()== l.getLocationId()){
                 locationExists = true;
                 break;
                 }
             }		
            
             if(!locationExists)
             {
                em.persist(l);
                return 1;
             }
              
             return 0;
         }
    
        
        public int mapSubLocation(SubLocation sl){
             
             em.persist(sl);
             return 1;
        
         }
    
        public Location getLocationById(int locationId) {
         
             TypedQuery<Location> query = em.createQuery(
               "SELECT l FROM Location l WHERE l.locationId = :locationId", Location.class);
                return query.setParameter("locationId", locationId).getSingleResult();
         } 
    
    
    
    
    
        public SubLocation getSubLocationById(int subLocationId) {
         
             TypedQuery<SubLocation> query = em.createQuery(
               "SELECT sl FROM SubLocation sl WHERE sl.subLocationId = :subLocationId", SubLocation.class);
                return query.setParameter("subLocationId", subLocationId).getSingleResult();
         } 
    
    
         
       
        
        
        
        public Location getLocation(int id){
         
            List<Location> locations = getLocationList();

            for(Location l: locations){
         
             if(l.getLocationId()== id){
                return l;
             }
          }
               return null;
         }
    
         
        
        public List<Location> getLocationList(){
        
             Query q = em.createNamedQuery("location.findlocation");
             List list = q.getResultList();
             q.getResultList();

             return list;
        
        }
    
        
        public List<SubLocation> getSubLocationList(){
        
             Query q = em.createNamedQuery("sublocation.findsublocation");
             List list = q.getResultList();
             q.getResultList();

             return list;
        
        }
         
         
        
    
        
        public int updateSub(SubLocation sl){
       
             List<SubLocation> sublocationList = getSubLocationList();

             for(SubLocation sl1: sublocationList){
             if(sl1.getLocationId()== sl.getLocationId() & sl1.getSubLocationId()==sl.getSubLocationId()){
             
                em.merge(sl);
                return 1;
            }
        }		
       return 0;
    }
        
        
    
      
      
        public int deleteAllData(int id){
      
            List<Location> locationList = getLocationList();

            for(Location l: locationList){
         
            if(l.getLocationId()== id){
           
               Location l1=new Location();
               l1=em.find(Location.class, id);
               em.remove(l1);
               return 1;   
          }
        }		
         return 0;
     }
    
    
        public int deleteSubData(int x,int y){
        
        List<SubLocation> sublocationList = getSubLocationList();

        for(SubLocation sl: sublocationList){
         
          if(sl.getLocationId()==x & sl.getSubLocationId()==y){
             
             SubLocation sl1=new SubLocation();
             sl1=em.find(SubLocation.class,new SubLocationKey(x,y));
             em.remove(sl1);
             return 1;
          }

       }
           return 0;
    }
    
        
        
        
        
        




} 

    
    
    
    
  
    
    
    
    
    
    

