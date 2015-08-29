/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transport.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
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
import transport.ejb.TransportSessionBean;
import transport.entities.Location;

/**
 *
 * @author JOHN
 */
@Stateless
@Path("Transport")
public class TransportWebService {
    
    @EJB
    private TransportSessionBean tsb;
    
    @POST
    @Path("makeassociation")
    public void makeAssociate(InputStream is) {

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
            tsb.addLocation(l);
            

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        
    
    }

    @GET
    @Path("getassociation/{locationId}")
    @Produces(MediaType.APPLICATION_XML)
    public Location getOneAssociation(@PathParam("locationId") String n) {
        
        Location l = tsb.getOneLocation(n);
        return l;
        
        
     }

    
    @GET
    @Path("getallassociation")
    @Produces(MediaType.APPLICATION_XML)
    public List<Location> getAllAssociation() {
        List<Location> list = tsb.getAllLocation();
        return list;
    }
    
    
    
    
    
    
    
}
