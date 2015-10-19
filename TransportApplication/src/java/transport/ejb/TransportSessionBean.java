/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.ejb;

import java.io.StringReader;
import static java.lang.Math.max;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import transport.entities.Location;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import transport.entities.SubLocation;

/**
 *
 * @author JOHN
 */
@Stateless
public class TransportSessionBean {
    
    //Global variable declaration
    @PersistenceContext
    EntityManager em;
    String encodedString,input;
    
    
        public org.w3c.dom.Document loadXML(String xml) throws Exception {
               DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));

                org.w3c.dom.Document doc = db.parse(is);
                return doc;
        }
    
    //Add New Location
        
        public void addNewLocation(Location l){
             
            em.persist(l);
             
        }
    
        
    //Add New SubLocation   
        
        public void mapSubLocation(SubLocation sl){
             
            em.persist(sl);
             
        }
        
        
        
   
        
    
    public org.w3c.dom.Document compareMatch(String encodedInput) throws Exception{
        
             encodedString = encodedInput; 
             Base64.Decoder decoder = Base64.getDecoder();
             byte[] decodedByteArray = decoder.decode(encodedString);
             input=new String(decodedByteArray);
         
         List<Location> list = (List<Location>)em.createQuery("SELECT l FROM Location l").getResultList();
         Iterator i=list.iterator();
         Location l=new Location();
         SubLocation sl=new SubLocation();
         String locName="",subLocName="",xmlHolder="";
         String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><MAINLOCATION>";
         
         while(i.hasNext()){
        l=(Location) i.next();
        locName=l.getLocationName();
        
        //get list of sub location of the entered main location
        List<SubLocation>subLoc=l.getSubLocation();
        Iterator itr=subLoc.iterator();
        while(itr.hasNext()){
        sl=(SubLocation)itr.next();
        subLocName=sl.getSubLocationName();
        double distance=distance(input,subLocName);
        double percent=percentage(distance,input,subLocName);
        xmlHolder+=decision(percent,locName,subLocName);
        }
        } 
         //if no data match in sublocation
         if("".equals(xmlHolder)){
         list = (List<Location>)em.createQuery("SELECT l FROM Location l").getResultList();
         i=list.iterator();
         while(i.hasNext()){
         l=(Location) i.next();
         locName=l.getLocationName();
         double distance=distance(input,locName);
         double percent=percentage(distance,input,locName);
         xmlHolder+=decision(percent,locName,locName);
         }
        }
         
         if("".equals(xmlHolder)){
            xmlHolder+="<LOCATIONNAME>"+input.toUpperCase()+"</LOCATIONNAME>"+"<SUBLOCATIONNAME>NO_MATCH_FOUND</SUBLOCATIONNAME>";
            }
         
         xmlData+=xmlHolder+"</MAINLOCATION>";
         //Dom document
         org.w3c.dom.Document doc=loadXML(xmlData);
        return doc;  
        }
    

//Distance calculation
        public int distance(String input, String subLocName) {
        input = input.toLowerCase();
        subLocName = subLocName.toLowerCase();
        // i == 0
        int [] costs = new int [subLocName.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= input.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= subLocName.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), input.charAt(i - 1) == subLocName.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[subLocName.length()];
    }

//percentage calculation
    public double percentage(double distance,String input,String subLocName){
        double percent = 0; 
        percent=100-(distance*100)/max(input.length(),subLocName.length());
        return percent; 
    }
    
    //decision taking
    public String decision(double percentage,String locName,String subLocName){
        String xml="";
        if(percentage>=60){
        xml="<LOCATIONNAME>"+locName+"</LOCATIONNAME>"+"<SUBLOCATIONNAME>"+subLocName+"</SUBLOCATIONNAME>";
        }
        return xml;
    }









    //SEARCH PLACE 
        public org.w3c.dom.Document search(String encodedInput) throws Exception{
            
             encodedString = encodedInput; 
             Base64.Decoder decoder = Base64.getDecoder();
             byte[] decodedByteArray = decoder.decode(encodedString);
             input=new String(decodedByteArray);
             
             String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><LOCATION>",xmlHolder="";
             Location l = null;
             
             Query q=em.createQuery("SELECT l FROM Location l JOIN l.subLocation sl WHERE l.locationId= sl.locationId "
                                     + "and ((sl.subLocationName =:subLocationName)or(l.locationName=:subLocationName))");
             q.setParameter("subLocationName",input);
             
             List<Location> list=(List<Location>)q.getResultList();
             Iterator i=list.iterator();
                
                //Location List handler
                while (i.hasNext()) 
                {
                   l = (Location) i.next();
                   
                }
              
             xmlHolder+="<LOCATIONNAME>"+l.getLocationName()+"</LOCATIONNAME>"
                       +"<SUBLOCATIONNAME>"+input.toUpperCase()+"</SUBLOCATIONNAME>"
                    ;
             xmlData+=xmlHolder+"</LOCATION>"; 
            
              org.w3c.dom.Document doc=loadXML(xmlData);
              return doc;
        }
    
    
        
        
        
        
        
    

    //Get Location By Id   
        
        public org.w3c.dom.Document getLocationById(String encodedInput) throws Exception{
            
             encodedString = encodedInput; 
             Base64.Decoder decoder = Base64.getDecoder();
             byte[] decodedByteArray = decoder.decode(encodedString);
             input=new String(decodedByteArray);
             int locId = Integer.parseInt(input);
             
             String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><LOCATION>",xmlHolder="";
             Location l=em.find(Location.class, locId);
             
             xmlHolder+="<LOCATIONID>"+l.getLocationId()+"</LOCATIONID>"
                     +"<SUBLOCATIONID>"+l.getLocationName()+"</SUBLOCATIONID>";
             
             xmlData+=xmlHolder+"</LOCATION>";
            
             org.w3c.dom.Document doc=loadXML(xmlData);
             return doc;
         } 
        
        
        
        
        
    

    //Get SubLocation By Id   
        
        public org.w3c.dom.Document getSubLocationById(String encodedInput) throws Exception{
            
             encodedString = encodedInput; 
             Base64.Decoder decoder = Base64.getDecoder();
             byte[] decodedByteArray = decoder.decode(encodedString);
             input=new String(decodedByteArray);
             int subLocationId = Integer.parseInt(input);
             
            String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><SUBLOCATION>",xmlHolder="";
            SubLocation sl=new SubLocation();
            Query q=em.createQuery("SELECT sl FROM SubLocation sl where sl.subLocationId=:subLocationId");
            q.setParameter("subLocationId", subLocationId);
            
            List<SubLocation> slist = (List<SubLocation>)q.getResultList();
            Iterator i=slist.iterator();
       
            ////Location List handler  
            while(i.hasNext())
              {
                 sl=(SubLocation) i.next();
               }
            
             xmlHolder+="<SUBLOCATIONID>"+sl.getSubLocationId()+"</SUBLOCATIONID>"
                        +"<SUBLOCATIONNAME>"+sl.getSubLocationName()+"</SUBLOCATIONNAME>";
             xmlData+=xmlHolder+"</SUBLOCATION>";
            
             org.w3c.dom.Document doc=loadXML(xmlData);
         
            
           return doc;
         } 
    
    



    
//Get LocationList    
        
        public  org.w3c.dom.Document getLocationList() throws Exception{
           
           String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><LOCATIONLIST>",xmlHolder="";
           
           Location l=new Location();
           
           Query q = em.createNamedQuery("location.findlocation");
           List<Location> llist = (List<Location>)q.getResultList();
           Iterator i=llist.iterator();
         
           //Location List handler
           while(i.hasNext())
             {
               l=(Location) i.next();
               xmlHolder+="<LOCATION>"+l.getLocationName()+"</LOCATION>";
             }
           
            xmlData+=xmlHolder+"</LOCATIONLIST>";
         
         //Dom document
         org.w3c.dom.Document doc=loadXML(xmlData);
         
            
           return doc;
        }
        
        
    



    //Get SubLocationList     
        
        public  org.w3c.dom.Document getSubLocationList() throws Exception{
           
           String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><SUBLOCATIONLIST>",xmlHolder="";
           
           SubLocation sl=new SubLocation();
           
           Query q = em.createNamedQuery("sublocation.findsublocation");
           List<SubLocation> slist = (List<SubLocation>)q.getResultList();
           Iterator i=slist.iterator();
           
           //SubLocation list handler
           while(i.hasNext())
             {
               sl=(SubLocation) i.next();
               xmlHolder+="<SUBLOCATION>"+sl.getSubLocationName()+"</SUBLOCATION>";
               
             }
             xmlData+=xmlHolder+"</SUBLOCATIONLIST>";
         
            //Dom document
            org.w3c.dom.Document doc=loadXML(xmlData);
         
            
           return doc;
           }

    


        
    //One Main Location and its corresponding subLocation
        public org.w3c.dom.Document subLocListToSpecificLocId(String encodedInput) throws Exception{
             
             encodedString = encodedInput; 
             Base64.Decoder decoder = Base64.getDecoder();
             byte[] decodedByteArray = decoder.decode(encodedString);
             input=new String(decodedByteArray);
             int locId = Integer.parseInt(input); 
             
            
            String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><LOCATION>",xmlHolder="";
            
            Location l=new Location();
            SubLocation sl=new SubLocation(); 
            Query q=em.createQuery("SELECT l FROM Location l where l.locationId=:locationId");
            q.setParameter("locationId", locId);
            List<Location> ll = (List<Location>)q.getResultList();
            Iterator i=ll.iterator();
        
            //Location List handler
            while(i.hasNext())
               {
                 l=(Location) i.next();
                 xmlHolder+="<LOCATIONNAME>"+l.getLocationName()+"</LOCATIONNAME>";
                            
                 List<SubLocation>sll=l.getSubLocation();
                 Iterator itr=sll.iterator();
             
                 //subLocation List handler
                 while(itr.hasNext())
                   {
                    sl=(SubLocation)itr.next();
                    xmlHolder+="<SUBLOCATION>"+"<SUBLOCATIONNAME>"+sl.getSubLocationName()+"</SUBLOCATIONNAME>"+"</SUBLOCATION>";
                   }
                }
            xmlData+=xmlHolder+"</LOCATION>";
         
            //Dom document
            org.w3c.dom.Document doc=loadXML(xmlData);
         
            
           return doc;
             
        }


        
    // Get All Location and SubLocation
        
        public org.w3c.dom.Document getAllLocWithSubLoc() throws Exception{
            
            String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><LOCATION>",xmlHolder="";
            
            Location l=new Location();
            SubLocation sl=new SubLocation();
            int countLoc=1,countSloc=1;
            int locId;
            
            Query q = em.createNamedQuery("location.findlocation");
            List<Location> llist = (List<Location>)q.getResultList();
            Iterator i=llist.iterator();
           
            //Location List handler
            while(i.hasNext())
              {
                 l=(Location) i.next();
                 //getalldata+=countLoc+"> "+l.getLocationName()+":\n";
                 xmlHolder+="<LOCATIONNAME>"+l.getLocationName()+"</LOCATIONNAME>";
                 locId=l.getLocationId();
                 countLoc++;
                 
                 Query q1 = em.createQuery("SELECT sl FROM SubLocation sl where sl.locationId=:locId");
                 q1.setParameter("locId", locId);
                 List<SubLocation> slist = (List<SubLocation>)q1.getResultList();
                 Iterator itr=slist.iterator();
                 
                 //subLocation List handler
                 while(itr.hasNext())
                     {
                        sl=(SubLocation) itr.next();
                        //getalldata+="\t "+countSloc+") "+sl.getSubLocationName()+"\n";
                        xmlHolder+="<SUBLOCATION>"+"<SUBLOCATIONNAME>"+sl.getSubLocationName()+"</SUBLOCATIONNAME>"+"</SUBLOCATION>";
                        countSloc++;
                     }
                 countSloc=1;
               }
  
           xmlData+=xmlHolder+"</LOCATION>";
         
            //Dom document
            org.w3c.dom.Document doc=loadXML(xmlData);
         
            
           return doc;
        }
        
    

    //get all data in your databse
        public List<Location> getAllData(){
        
             Query q = em.createNamedQuery("location.findlocation");
             List list = q.getResultList();
             q.getResultList();

             return list;
        
        }

    
    //Update SubLocation    
        public void update(SubLocation sl){
           
           em.merge(sl);
           
       }   
        
        
    //Location removal and all its sub location
        public String delete(int locId){
         
           String msg="";
           boolean chk=false;
           
           Query q=em.createQuery("SELECT l FROM Location l  WHERE l.locationId=:locationId");
           q.setParameter("locationId",locId);
           List<Location> list =(List<Location>)q.getResultList();
          
           //Empty record validation message 
           if(list.isEmpty()){
        
              msg+="Location not deleted or it may not exist";
           }
    
           //Record Deleting handler
           else{
                 Location l=new Location();
                 l=em.find(Location.class, locId);
                 em.remove(l);
                 msg+=l.getLocationName()+". Successfully deleted";
                 
            }    
    
          
           return msg;
    
        }
        
        

        

        
} 

    
    
    
    
  
    
    
    
    
    
    

