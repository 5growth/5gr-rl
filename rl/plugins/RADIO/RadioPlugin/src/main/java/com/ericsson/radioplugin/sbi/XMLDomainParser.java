/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.radioplugin.sbi;

import com.ericsson.radioplugin.sbi.objects.NFVIPop;
import com.ericsson.radioplugin.sbi.objects.RadioCoverageAreaResource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



/**
 *
 * @author fhuser
 */
public class XMLDomainParser {
    private List<NFVIPop> poplist;
    private HashMap<String, RadioCoverageAreaResource> reslist;
    
    public XMLDomainParser(String xmlFile) {
        poplist = new ArrayList();
        reslist = new HashMap();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParser --> Root Element :: " + rootNode.getName());
        //Parse NFVIPOPElement
        Element xmlpoplist = rootNode.getChild("NVFIPopList");
        List<Element> xmlpop = xmlpoplist.getChildren("NfviPop");
        for (int i = 0; i < xmlpop.size(); i++) {

            Element xmlpopNode = (Element) xmlpop.get(i);
            //Connectivity Endpoint
            String endp = xmlpopNode.getChildText("ConnectivityEndpoints");
            System.out.println("XMLDomainParser --> ConnectivityEndpoints : " + xmlpopNode.getChildText("ConnectivityEndpoints"));
            //GeographicalLocationInfo
            String locinfo = xmlpopNode.getChildText("geographicalLocationInfo");
            System.out.println("XMLDomainParser --> geographicalLocationInfo : " + xmlpopNode.getChildText("geographicalLocationInfo"));

            //PopId
            String popid = xmlpopNode.getChildText("popId");
            System.out.println("XMLDomainParser --> PopId : " + xmlpopNode.getChildText("popId"));

            //VimId
            String vimid = xmlpopNode.getChildText("vimId");
            System.out.println("XMLDomainParse --> vimId : " + xmlpopNode.getChildText("vimId"));
           //insert element in ArrayList
            NFVIPop el = new NFVIPop(endp, locinfo, popid, vimid);

            poplist.add(el);
        }
        
 
        
        //Parse ResourceElement
        Element xmlreslist = rootNode.getChild("ResourceList");
        Element xmlnetlist = xmlreslist.getChild("RadioCoverageAreaList");
        List<Element> xmlnet = xmlnetlist.getChildren("RadioCoverageArea");
        for (int i = 0; i < xmlnet.size(); i++) {

            Element xmlnetNode = (Element) xmlnet.get(i);
            //Id
            String id = xmlnetNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlnetNode.getChildText("id"));
            
            //minbw
            String minbw = xmlnetNode.getChildText("minbandwidth");
            System.out.println("XMLDomainParser --> minbandwidth : " + xmlnetNode.getChildText("minbandwidth"));

            //minbw
            String maxbw = xmlnetNode.getChildText("maxbandwidth");
            System.out.println("XMLDomainParser --> maxbandwidth : " + xmlnetNode.getChildText("maxbandwidth"));

            //delay
            String delay = xmlnetNode.getChildText("delay");
            System.out.println("XMLDomainParser --> delay : " + xmlnetNode.getChildText("delay"));
            
            //latitude
            String latitude = xmlnetNode.getChildText("latitude");
            System.out.println("XMLDomainParser --> latitude : " + xmlnetNode.getChildText("latitude"));
            
            //longitude
            String longitude = xmlnetNode.getChildText("longitude");
            System.out.println("XMLDomainParser --> longitude : " + xmlnetNode.getChildText("longitude"));
            
            //altitude
            String altitude = xmlnetNode.getChildText("altitude");
            System.out.println("XMLDomainParser --> altitude : " + xmlnetNode.getChildText("altitude"));
            
            //range
            String range = xmlnetNode.getChildText("range");
            System.out.println("XMLDomainParser --> range : " + xmlnetNode.getChildText("range"));
            //insert element in ArrayList
            RadioCoverageAreaResource el = new RadioCoverageAreaResource(id, minbw, maxbw, delay, latitude,
                        longitude, altitude, range);
            reslist.put(id, el);
        }
               
        
    }
  
    private static Document getSAXParsedDocument(final String fileName)
    {
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try
        {
            document = builder.build(new File(fileName));
        }catch (JDOMException |NullPointerException | NumberFormatException | IOException e)
        {
            System.out.println("XMLDomainParse --> Error Parsing xml file");
            e.printStackTrace();
        }
        return document;
    }
    
    //get method

    public List<NFVIPop> getPoplist() {
        return poplist;
    }

    public void setPoplist(List<NFVIPop> poplist) {
        this.poplist = poplist;
    }


    public HashMap<String, RadioCoverageAreaResource> getReslist() {
        return reslist;
    }

    public void setReslist(HashMap<String, RadioCoverageAreaResource> reslist) {
        this.reslist = reslist;
    }

}
