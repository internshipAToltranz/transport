/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb_session.TransportSessionBean;
import entities.MainLocation;
import entities.SubLocation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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

/**
 *
 * @author Owner
 */
@Path("transportService")
@Stateless
public class TransportWebService {
    
    @EJB 
    private TransportSessionBean tsb;
    
    private String msg="Success",msgFail="Failed";
    
    @POST 
    @Path("insertNewLocation")
    public void InsertNewLocation(InputStream is){
    
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
            JAXBContext context = JAXBContext.newInstance(MainLocation.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<MainLocation> je = um.unmarshal(sr, MainLocation.class);
            sr.close();
            MainLocation ml = new MainLocation();
            ml = je.getValue();            
            tsb.createLocation(ml);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
    
    //Get location going from an entered location ID
    @GET
    @Path("getlocator/{locationid}")
    public String getLocators(@PathParam("locationid")int locId){
        MainLocation mn=tsb.getLocator(locId);
        return mn.getLocationName(); 
    }
    
//Get only a sub location going from its sublocation Id
    @GET
    @Path("getsublocator/{sublocationid}")
    public String getSublocator(@PathParam("sublocationid")int subLocId){
        List<SubLocation> sbln=tsb.getSubLoc(subLocId);
        Iterator i=sbln.iterator();
        SubLocation sbl=new SubLocation();
        while(i.hasNext()){
        sbl=(SubLocation) i.next();
        }
        return sbl.getSublocationName(); 
    }
    
    //Get only sub location going to an entered location name
    @GET
    @Path("getsublocatoinlist/{locationname}")
    public String getSublocatoinList(@PathParam("locationname") String locName){
        List<MainLocation> mnln=tsb.getSubLocList(locName);
        Iterator i=mnln.iterator();
        String loc="List of Location and its sublocations:\n";
        MainLocation mn=new MainLocation();
        SubLocation sbln=new SubLocation();
        while(i.hasNext()){
        mn=(MainLocation) i.next();
        loc+=mn.getLocationName()+":\n";
        
        //get list of sub location of the entered main location
        List<SubLocation>subLoc=mn.getSubLocation();
        Iterator itr=subLoc.iterator();
        while(itr.hasNext()){
        sbln=(SubLocation)itr.next();
        loc+="\t-"+sbln.getSublocationName()+"\n";
        }
        }
        return loc; 
    }
    
    //Get all main locations
    @GET
    @Path("selectmainlocation")
    @Produces(MediaType.APPLICATION_XML)
    public String getMainLocationOnly() {
        
       List<MainLocation> mainLocationList = tsb.selectMainLocation();
      MainLocation mn=new MainLocation();
         String loc = "List of Location \n";
         Iterator i=mainLocationList.iterator();
         while(i.hasNext()){
         mn=(MainLocation) i.next();
         
         loc+=mn.getLocationName()+"\n";
         }
        return loc;
    }
    
    //Get all main locations and their sub locations
    @GET
    @Path("selectmainlocandsubloc")
    @Produces(MediaType.APPLICATION_XML)
    public String getMainLocAndSubLoc() {
        
       List<MainLocation> mainLocationList = tsb.selectMainLocation();
       MainLocation mn=new MainLocation();
       SubLocation sbln=new SubLocation();
       int countMain=1,countSub=1;
         String loc = "List of Location and their Sublocation \n";
         int locId;
         
         Iterator i=mainLocationList.iterator();
         while(i.hasNext()){
         mn=(MainLocation) i.next();
         loc+=countMain+"> "+mn.getLocationName()+":\n";
         locId=mn.getLocationId();
         countMain++;
         
         //get sub location list
         List<SubLocation> lis=tsb.subLocationName(locId);
         Iterator itr=lis.iterator();
         while(itr.hasNext()){
         sbln=(SubLocation) itr.next();
         loc+="\t "+countSub+") "+sbln.getSublocationName()+"\n";
         countSub++;
         }
         countSub=1;
         }
  
        return loc;
    }
    
    

    //Get all sublocations only
    @GET
    @Path("findsublocname")
    @Produces(MediaType.APPLICATION_XML)
    public String subLocName() {
        
        List<SubLocation> subLocationList = tsb.subLocationFinder();
        SubLocation subln=new SubLocation();
         String loc = "List of SubLocations: \n";
         List<String> lis1=new ArrayList<>();
         Iterator i=subLocationList.iterator();
         while(i.hasNext()){
         subln=(SubLocation) i.next();
         
         loc+=subln.getSublocationName()+"\n";
         lis1.add(loc);
         }
        return loc;
    }
    
     //Get all sublocations only
    @GET
    @Path("findsublocation")
    @Produces(MediaType.APPLICATION_XML)
    public List<SubLocation> subLocFinder() {
        
        List<SubLocation> subLocationList = tsb.subLocationFinder();
        
        return subLocationList;
    }
    
    //Get main location according to the sublocation entered
    @GET
    @Path("findPlacement/{sublocationname}")
    @Produces(MediaType.APPLICATION_XML)
    public String placementFinder(@PathParam("sublocationname")String subLocName) {
        
        MainLocation mn = tsb.placementFinder(subLocName);
        return mn.getLocationName();
    }
    
    //Delete Main Location
    @POST
    @Path("deletelocation/{locid}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteLocation(@PathParam("locid")int locId){
    String message=tsb.deleteLocation(locId);
    
    return message;
    }
    
    //Update already exist data
    @POST 
    @Path("updatelocation")
    public void updateLocation(InputStream is){
    
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
            JAXBContext context = JAXBContext.newInstance(MainLocation.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<MainLocation> je = um.unmarshal(sr, MainLocation.class);
            sr.close();
            MainLocation ml = new MainLocation();
            ml = je.getValue();            
            tsb.updateData(ml);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
    
    //get mainlocation's sublocation from the main Location's ID
    @GET
    @Path("getsublocationbyid/{locid}")
    public String getSublocById(@PathParam("locid") int locId){
    List<MainLocation> mnln=tsb.getSubLocListById(locId);
    Iterator i=mnln.iterator();
    String loc="List of Location and its sublocations:\n";
    MainLocation mn=new MainLocation();
    SubLocation sbln=new SubLocation();
    while(i.hasNext()){
    mn=(MainLocation) i.next();
    loc+=mn.getLocationName()+":\n";
    
    //get list of sub location of the entered main location
    List<SubLocation>subLoc=mn.getSubLocation();
    Iterator itr=subLoc.iterator();
    while(itr.hasNext()){
    sbln=(SubLocation)itr.next();
    loc+="\t-"+sbln.getSublocationName()+"\n";
    }
    }
    return loc;
    }
    
    //Mapping new sublocations
    @POST 
    @Path("mapsubloc")
    public void mapSubLoc(InputStream is){
    
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
            SubLocation sbl = new SubLocation();
            sbl = je.getValue();            
            tsb.mappingSubLocation(sbl);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
    
}
