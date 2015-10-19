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
import java.nio.charset.StandardCharsets;
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
import java.util.Base64;

/**
 *
 * @author JOHN
 */
@Stateless
@Path("transport")
public class TransportWebService {
    
    //Global variable declaration
    @EJB
    private TransportSessionBean tsb;
    String encodedSubLocName="";
    String encodedSubLocId="";
    String encodedLocId="";
    //Adding new location
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

    
    
    //Mapping other sublocation to a Location
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
    




//search place using pattern match
    @GET
    @Path("patternsearch/{subLocationName}")
    @Produces(MediaType.APPLICATION_XML)
    
    public org.w3c.dom.Document patternSearch(@PathParam("subLocationName")String subLocName) throws Exception {
        
        Base64.Encoder encoder = Base64.getEncoder();
        encodedSubLocName = encoder.encodeToString(subLocName.getBytes(StandardCharsets.UTF_8) );
        
        org.w3c.dom.Document doc = tsb.compareMatch(encodedSubLocName);
        return doc;
    }
    
    
    


//search places
    @GET
    @Path("search/{subLocationName}")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document Search(@PathParam("subLocationName")String subLocName) throws Exception{
        
       Base64.Encoder encoder = Base64.getEncoder();
       encodedSubLocName = encoder.encodeToString(subLocName.getBytes(StandardCharsets.UTF_8) );
       
        
       org.w3c.dom.Document doc = tsb.search(encodedSubLocName);
        
       return doc;
    }
    
    
    
    
    
    //get location by its id
    @GET
    @Path("getlocation/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document getLocationById(@PathParam("locationId")int locId) throws Exception{
        
        String s=Integer.toString(locId);
        Base64.Encoder encoder = Base64.getEncoder();
        encodedLocId = encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8) );
        org.w3c.dom.Document doc=tsb.getLocationById(encodedLocId);
        
        return doc;
     } 
      
    //get sub location by its Id
    @GET
    @Path("getsublocation/{subLocationId}")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document getSubLocationById(@PathParam("subLocationId")int subLocationId) throws Exception{
        
        String s=Integer.toString(subLocationId);
        Base64.Encoder encoder = Base64.getEncoder();
        encodedLocId = encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8) );
        org.w3c.dom.Document doc=tsb.getSubLocationById(encodedLocId);
        return doc;
        
     } 
    
    
    //Get all main location list
    @GET
    @Path("locationlist")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document getLocationList() throws Exception{
        
        org.w3c.dom.Document doc=tsb.getLocationList();
        return doc;
        
        
    }
       
    
    //Get all subLocation list
    @GET
    @Path("sublocationlist")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document getSubLocationList() throws Exception{
        
         org.w3c.dom.Document doc=tsb.getSubLocationList();
         
         return doc;
    }
    
    
    
    
    
    //Get specific location with its subLocation
    @GET
    @Path("getOneLocWithSubLoc/{locationId}")
    public org.w3c.dom.Document subLocListToSpecificLocId(@PathParam("locationId") int locId) throws Exception{
        
        
        String s=Integer.toString(locId);
        Base64.Encoder encoder = Base64.getEncoder();
        encodedLocId = encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8) );
        org.w3c.dom.Document doc=tsb.subLocListToSpecificLocId(encodedLocId);
        
        return doc;
        
    }
    
    
    
    
    //fetch all records
    @GET
    @Path("getalllocandsubloc")
    @Produces(MediaType.APPLICATION_XML)
    public org.w3c.dom.Document getAllLocWithSubLoc() throws Exception{
        
         org.w3c.dom.Document doc=tsb.getAllLocWithSubLoc();
         
         return doc;
    }
    
    
    
    @GET
    @Path("getalldata")
    @Produces(MediaType.APPLICATION_XML)
    public List<Location> getAllData() {
        
        List<Location> list = tsb.getAllData();
        return list;
    }
    
    
    
    
    //Updating sublocation
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
    
    
    //recors delete
    @DELETE
    @Path("delete/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public String delete(@PathParam("locationId")int locId){
    
        String s=tsb.delete(locId);
    
        return s;
     }
    
    
   
  

}
