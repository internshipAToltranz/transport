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
    public String addLocation(InputStream is) {

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
            
            int result=tsb.addNewLocation(l);
            if(result == 1)
            {
         
                return SUCCESS_RESULT;
            }

        } 
        
        catch (Exception e) {
            System.out.println(e.getMessage());

        }
              
        
        
        return FAILURE_RESULT;

    }

    
    
    
    @POST
    @Path("mapsublocation")
    public String mapSubLocation(InputStream is) {

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
            int result=tsb.mapSubLocation(sl);
            if(result == 1)
            {
         
                return SUCCESS_RESULT;
            }

        } 
        
        catch (Exception e) {
            System.out.println(e.getMessage());

        }
              
        
        
        return FAILURE_RESULT;


    }
    
    
    @GET
    @Path("specificlocation/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String getLocationById(@PathParam("locationId")int locationId) {
        
        Location l=tsb.getLocationById(locationId);
        return "LocationId"+"."+l.getLocationId()+"       "+"LocationName"+"."+l.getLocationName();
     } 
    
    
    
    
    @GET
    @Path("specificsublocation/{subLocationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String getSubLocationById(@PathParam("subLocationId")int subLocationId) {
        
        SubLocation sl=tsb.getSubLocationById(subLocationId);
        return "LocationId"+"."+sl.getLocationId()+"       "+"SubLocationId"+"."+sl.getSubLocationId()+"       "+"LocationName"+"."+sl.getSubLocationName();
     } 
    
    
    
  
    
   
    @GET
    @Path("getonemapdata/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public Location getUser(@PathParam("locationId") int locationId){
      
        return tsb.getLocation(locationId);
    }
    
    
    @GET
    @Path("locationlist")
    @Produces(MediaType.APPLICATION_XML)
    public List<Location> getLocationList() {
        
        List<Location> list = tsb.getLocationList();
        return list;
    }
    
    
    @GET
    @Path("sublocationlist")
    @Produces(MediaType.APPLICATION_XML)
    public List<SubLocation> getSubLocationList() {
        
        List<SubLocation> list = tsb.getSubLocationList();
        return list;
    }
    
    
    
            
  
    @POST
    @Path("updatesub")
    @Produces(MediaType.APPLICATION_XML)
    public String updateSub(InputStream is) {
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
            int result=tsb.updateSub(sl);
            if(result == 1)
            {
         
                return SUCCESS_RESULT;
            }

        } 
        
        catch (Exception e) {
            System.out.println(e.getMessage());

        }
              
        return FAILURE_RESULT;


   }
    
    
    
    
    
    @DELETE
    @Path("deleteall/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String delete(@PathParam("locationId") int id) {
        int result=tsb.deleteAllData(id);
        if(result == 1){
         return SUCCESS_RESULT;
      }
      return FAILURE_RESULT;
    }
    
    
    
    
    @DELETE
    @Path("deletesub/{locationId}/{subLocationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String searchByKey(@PathParam("locationId")int x, @PathParam("subLocationId")int y) {
        
        int result = tsb.deleteSubData(x,y);
        if(result == 1){
         return SUCCESS_RESULT;
      }
      return FAILURE_RESULT;
    } 
    
    
    
    
    

    
}
