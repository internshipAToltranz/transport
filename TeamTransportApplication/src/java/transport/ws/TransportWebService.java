/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.ws;

import transport.ejb.TransportSessionBean;
import transport.entities.Location;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import transport.entities.SubLocation;

/**
 *
 * @author JOHN
 */
@Stateless
@Path("transport")
public class TransportWebService {
    @EJB
    private TransportSessionBean tsb;
    private static final String SUCCESS_RESULT="1"+"   "+"Success";
    private static final String FAILURE_RESULT="-1"+"   "+"Failure";

    @POST
    @Path("addnewlocation")
    public void addLocation(InputStream is) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);

            }
            String xmldata = sb.toString();
            XMLInputFactory fc = XMLInputFactory.newFactory();
            StreamSource xml = new StreamSource(new StringReader(xmldata));
            XMLStreamReader sr = fc.createXMLStreamReader(xml);
            JAXBContext context = JAXBContext.newInstance(Location.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<Location> je = um.unmarshal(sr, Location.class);
            sr.close();
            Location l = new Location();
            l = je.getValue();            
            tsb.addNewLocation(l);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    
    
    
    @POST
    @Path("mapsublocation")
    public void mapSubLocation(InputStream is) {

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);

            }
            String xmldata = sb.toString();
            XMLInputFactory fc = XMLInputFactory.newFactory();
            StreamSource xml = new StreamSource(new StringReader(xmldata));
            XMLStreamReader sr = fc.createXMLStreamReader(xml);
            JAXBContext context = JAXBContext.newInstance(SubLocation.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<SubLocation> je = um.unmarshal(sr, SubLocation.class);
            sr.close();
            SubLocation sl = new SubLocation();
            sl = je.getValue();            
            tsb.mapSubLocation(sl);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        


    }
    
    
    @GET
    @Path("getlocation/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String getLocationById(@PathParam("locationId")int locationId) {
        
        Location l=tsb.getLocationById(locationId);
        return l.getLocationId()+"   "+l.getLocationName(); 
     } 
    
    
    
    
    @GET
    @Path("getsublocation/{subLocationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String getSubLocationById(@PathParam("subLocationId")int subLocationId) {
        
        List<SubLocation> sbl=tsb.getSubLocationById(subLocationId);
        Iterator i=sbl.iterator();
        SubLocation sl=new SubLocation();
       
        while(i.hasNext()){
        sl=(SubLocation) i.next();
        }
        return sl.getSubLocationId()+"   "+sl.getSubLocationName();
     } 
    
    
    
    @GET
    @Path("locationlist")
    @Produces(MediaType.APPLICATION_XML)
    public String getLocationList() {
        
         List<Location> locationList = tsb.getLocationList();
         Location l=new Location();
         String loc = "List Of Location \n";
         Iterator i=locationList.iterator();
         
         while(i.hasNext())
          {
              l=(Location) i.next();
              loc+=l.getLocationId()+"  "+l.getLocationName()+"\n";
          }
        return loc;
    }
    
    
    
    @GET
    @Path("sublocationlist")
    @Produces(MediaType.APPLICATION_XML)
    public String getSubLocationList() {
        
         List<SubLocation> subLocationList = tsb.getSubLocationList();
         SubLocation sl=new SubLocation();
         String loc = "List Of SubLocations: \n";
         List<String> list=new ArrayList<>();
         Iterator i=subLocationList.iterator();
         
         while(i.hasNext())
          {
             sl=(SubLocation) i.next();
             loc+=sl.getSubLocationId()+"  "+sl.getSubLocationName()+"\n";
             list.add(loc);
          }
        return loc;
    }
    
    
    
    
    
    
    @GET
    @Path("getalldata")
    @Produces(MediaType.APPLICATION_XML)
    public String getAllData() {
         
         Location l=new Location();
         SubLocation sl=new SubLocation();
         
         int countMain=1,countSub=1;
         String loc = "List Of Location And Their Sublocation \n";
         int locId;
       
       List<Location> ll = tsb.getLocationList();
         Iterator i=ll.iterator();
         while(i.hasNext()){
         l=(Location) i.next();
         loc+=countMain+"> "+l.getLocationName()+":\n";
         locId=l.getLocationId();
         countMain++;
         
        
       List<SubLocation> sll=tsb.getSubLocByLoc(locId);
         Iterator itr=sll.iterator();
         while(itr.hasNext()){
         sl=(SubLocation) itr.next();
         loc+="\t "+countSub+") "+sl.getSubLocationName()+"\n";
         countSub++;
         }
         countSub=1;
         }
  
        return loc;
    }
    
    
   
    
    @GET
    @Path("searchlocation/{subLocationName}")
    @Produces(MediaType.APPLICATION_XML)
    public String placementFinder(@PathParam("subLocationName")String subLocName) {
        
        String loc="Search Location By SubLocationName:\n";
        Location subLocationList = tsb.searchloc(subLocName);
        return loc+subLocationList.getLocationName();
    }
  
    
    @GET
    @Path("searchsublocation/{locationName}")
    public String getSublocatoinList(@PathParam("locationName") String locName){
        
        String loc="Search SubLocation By LocationName:\n";
        Location l=new Location();
        SubLocation sl=new SubLocation();
        
        List<Location> ll=tsb.searchsub(locName);
        Iterator i=ll.iterator();
        
        while(i.hasNext())
          {
             l=(Location) i.next();
             loc+=l.getLocationName()+":\n";
        
        
             List<SubLocation>sll=l.getSubLocation();
             Iterator itr=sll.iterator();
             while(itr.hasNext()){
             sl=(SubLocation)itr.next();
             loc+="\t-"+sl.getSubLocationName()+"\n";
          }
        }
        return loc; 
    }
    
    
    @POST
    @Path("update")
    public void update(InputStream is) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);

            }
            String xmldata = sb.toString();
            XMLInputFactory fc = XMLInputFactory.newFactory();
            StreamSource xml = new StreamSource(new StringReader(xmldata));
            XMLStreamReader sr = fc.createXMLStreamReader(xml);
            JAXBContext context = JAXBContext.newInstance(SubLocation.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<SubLocation> je = um.unmarshal(sr, SubLocation.class);
            sr.close();
            SubLocation sl = new SubLocation();
            sl = je.getValue();            
            tsb.update(sl);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }
    
    
      @DELETE
      @Path("delete/{locationId}")
      @Produces(MediaType.APPLICATION_XML)
      public String delete(@PathParam("locationId")int locId){
    
        String message=tsb.delete(locId);
    
        return message;
     }
    
}
