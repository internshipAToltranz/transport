/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.ejb;

import java.util.Iterator;
import transport.entities.Location;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import transport.entities.SubLocation;

/**
 *
 * @author JOHN
 */
@Stateless
public class TransportSessionBean {
    @PersistenceContext
    EntityManager em;
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    
         public void addNewLocation(Location l){
             em.persist(l);
         }
    
        
        public void mapSubLocation(SubLocation sl){
             
             em.persist(sl);
             
        
         }
    
        public Location getLocationById(int locationId) {
         
             Location l=em.find(Location.class, locationId);
             return l; 
         } 
    
    
    
    
    
        public List<SubLocation> getSubLocationById(int subLocationId) {
         
            em = emf.createEntityManager();
            
            Query q=em.createQuery("SELECT sl FROM SubLocation sl where sl.subLocationId=:subLocationId");
            
            List<SubLocation> list = (List<SubLocation>) q.setParameter("subLocationId", subLocationId).getResultList();

            return list;
         } 
    
    
         
       
        
        
        
        
         
       public List<Location> getLocationList(){
        
             em = emf.createEntityManager();
             List<Location> list = (List<Location>)em.createQuery("SELECT l FROM Location l").getResultList();
            
             return list; 
        
        }
    
        public List<SubLocation> getSubLocByLoc(int locId){
        
         em = emf.createEntityManager();
         Query q=em.createQuery("SELECT sl FROM SubLocation sl where sl.locationId=:locId");
         List<SubLocation> list = (List<SubLocation>)q.setParameter("locId", locId).getResultList();

         return list;
        } 
        
        
        
        
        
        
        
        
        
        public List<SubLocation> getSubLocationList(){
        
             em = emf.createEntityManager();
             List<SubLocation> list = (List<SubLocation>)em.createQuery("SELECT sl FROM SubLocation sl").getResultList();

             return list;
        
        }
         
         
        public Location searchloc(String subLocName){
         
             Query q=em.createQuery("SELECT l FROM Location l JOIN l.subLocation sl WHERE l.locationId= sl.locationId "
                                     + "and ((sl.subLocationName =:subLocationName)or(l.locationName=:subLocationName))");
             List<Location> list=(List<Location>)q.setParameter("subLocationName",subLocName).getResultList();
             Iterator i=list.iterator();
             Location ml = null;
             while (i.hasNext()) {
                 ml = (Location) i.next();
             }
             return ml;
        }
        
        
          
        public List<Location> searchsub(String locName){
         
            em = emf.createEntityManager();
            Query q=em.createQuery("SELECT l FROM Location l where l.locationName=:locName");
            List<Location> list = (List<Location>)q.setParameter("locName", locName).getResultList();

             return list;
    }


       public void update(SubLocation sl){
           
           em.merge(sl);
           
       }
       
        
       public String delete(int locId){
         
           String msg="";
           boolean chk=false;
           List<Location> list =(List<Location>)em.createQuery("SELECT l FROM Location l  WHERE l.locationId=:locId")
                .setParameter("locId",locId).getResultList();
          
           if(list.isEmpty()){
        
               msg+="Location not deleted or it may not exist";
           }
    
           else{
                 Location l=new Location();
                 l=em.find(Location.class, locId);
                 msg+=l.getLocationName()+". Successfully deleted";
                 em.remove(l);
            }    
    
           return msg;
    }
       
    
   
    

} 

    
    
    
    
  
    
    
    
    
    
    

