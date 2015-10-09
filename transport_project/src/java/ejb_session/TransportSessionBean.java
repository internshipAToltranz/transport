/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_session;

import entities.MainLocation;
import entities.SubLocation;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author Owner
 */
@Stateless
public class TransportSessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //Persist data to entities
    @PersistenceContext 
    EntityManager em;
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    //Insert data into database
    public void createLocation(MainLocation ml){
    
     em.persist(ml);
    }
    
    //fetch main Location and their sublocation repectively going to an enetered location ID
    public MainLocation getLocator(int locId){
        MainLocation mn=em.find(MainLocation.class, locId);
    return mn;  
    }
    
    //Get a sublocation name going from its id
    public List<SubLocation> getSubLoc(int subLocId){
         em = emf.createEntityManager();
         List<SubLocation> list = (List<SubLocation>)em.createQuery("SELECT s FROM SubLocation s where s.subLocationId=:subLocId")
                 .setParameter("subLocId", subLocId).getResultList();

    return list;
    }
    
    //Get a sublocation list from an entered location name
    public List<MainLocation> getSubLocList(String locName){
         em = emf.createEntityManager();
         List<MainLocation> list = (List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m where m.locationName=:locName")
                 .setParameter("locName", locName).getResultList();

    return list;
    }
    
    //select all main locations
    public List<MainLocation> selectMainLocation(){
         em = emf.createEntityManager();
         List<MainLocation> list = (List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m").getResultList();
            
        return list;  
        }
    
    //select all main locations but not is not used
    public List<MainLocation> selectMainLocationAndSubLocation(){
         em = emf.createEntityManager();
         List<MainLocation> list = (List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m JOIN m.subLocation s WHERE m.locationId= s.locationId").getResultList();
            
        return list;  
        }
    
     //select all sub locations
    public List<SubLocation> subLocationFinder(){
        
         em = emf.createEntityManager();
         List<SubLocation> list = (List<SubLocation>)em.createQuery("SELECT s FROM SubLocation s").getResultList();

        return list;
        }
    
    //select all sub locations from an entered location name
    public List<SubLocation> subLocationName(int locId){
        
         em = emf.createEntityManager();
         List<SubLocation> list = (List<SubLocation>)em.createQuery("SELECT s FROM SubLocation s where s.locationId=:locId")
                 .setParameter("locId", locId).getResultList();

        return list;
        }
    
     //select main location going from an entered subLocation
    public MainLocation placementFinder(String subLocName){
        List<MainLocation> list=(List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m JOIN m.subLocation s WHERE m.locationId= s.locationId and ((s.sublocationName =:subLocName)or(m.locationName=:subLocName))")
                .setParameter("subLocName",subLocName).getResultList();
            Iterator i=list.iterator();
            MainLocation ml = null;
            while (i.hasNext()) {
                ml = (MainLocation) i.next();
            }
    return ml;
    }
    
    //Delete main location
    public String deleteLocation(int locId){
    String msg="";
    boolean chk=false;
    List<MainLocation> lis =(List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m  WHERE m.locationId=:locId")
                .setParameter("locId",locId).getResultList();
    if(lis.isEmpty()){
        msg+="Location not deleted or it may not exist";
    }
    else{
        MainLocation mn=new MainLocation();
        mn=em.find(MainLocation.class, locId);
        msg+=mn.getLocationName()+". Successfully deleted";
        em.remove(mn);
    }    
    return msg;
    }
    
    //Update record to the database
    public void updateData(MainLocation ml){
    
        em.merge(ml);
        
    }
    
   //Get sublocation By ID
    public List<MainLocation> getSubLocListById(int locId){
    em = emf.createEntityManager();
    List<MainLocation> list = (List<MainLocation>)em.createQuery("SELECT m FROM MainLocation m where m.locationId=:locId")
    .setParameter("locId", locId).getResultList();
    
    return list;
    }
    
    //Insert data into database
    public void mappingSubLocation(SubLocation sbl){
    
     em.persist(sbl);
    }
    
}
