/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel.service;

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
import travel.ejb.TravelSessionBean;
import travel.entities.Region;

/**
 *
 * @author JOHN
 */
@Stateless
@Path("Travel")
public class TravelWebService {
    
    @EJB
    private TravelSessionBean tsb;
    
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
            JAXBContext context = JAXBContext.newInstance(Region.class);
            Unmarshaller um = context.createUnmarshaller();
            JAXBElement<Region> je = um.unmarshal(sr, Region.class);
            sr.close();
            Region r = new Region();
            r = je.getValue();
            tsb.addRegion(r);
            

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        
    
    }

    @GET
    @Path("getassociation/{RId}")
    @Produces(MediaType.APPLICATION_XML)
    public Region getOneAssociation(@PathParam("RId") int rId) {
        
        Region r = tsb.getOneRegion(rId);
        return r;
        
        
     }

    
    @GET
    @Path("getallassociation")
    @Produces(MediaType.APPLICATION_XML)
    public List<Region> getAllAssociation() {
        List<Region> list = tsb.getAllRegion();
        return list;
    }
    
    
}
