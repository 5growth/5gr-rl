/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.crosshaulplugin.sbi;

import com.ericsson.crosshaulplugin.sbi.objects.NFVIPop;
import com.ericsson.crosshaulplugin.sbi.objects.RANService;
import com.ericsson.crosshaulplugin.sbi.objects.NetworkResource;
import com.ericsson.crosshaulplugin.sbi.objects.Zone;
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
    private List<Zone> zonelist;
    private HashMap<String, NetworkResource> reslist;
    private HashMap<String, RANService> raninfolist;
    
    public XMLDomainParser(String xmlFile) {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        reslist = new HashMap();
        raninfolist = new HashMap();
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
        
        //Parse ZoneElement
        Element xmlzonelist = rootNode.getChild("Zonelist");
        List<Element> xmlzone = xmlzonelist.getChildren("Zone");
        for (int i = 0; i < xmlzone.size(); i++) {

            Element xmlzoneNode = (Element) xmlzone.get(i);
            //Id
            String id = xmlzoneNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlzoneNode.getChildText("id"));
            //Status
            String status = xmlzoneNode.getChildText("status");
            System.out.println("XMLDomainParser --> status : " + xmlzoneNode.getChildText("status"));

            //PopId
            String popid = xmlzoneNode.getChildText("popId");
            System.out.println("XMLDomainParser --> PopId : " + xmlzoneNode.getChildText("popId"));

            //Name
            String name = xmlzoneNode.getChildText("name");
            System.out.println("XMLDomainParser --> name : " + xmlzoneNode.getChildText("name"));
            
            //Property
            String property = xmlzoneNode.getChildText("property");
            System.out.println("XMLDomainParser --> property : " + xmlzoneNode.getChildText("property"));
           //insert element in ArrayList
            Zone el = new Zone(id, name, popid, status, property);

            zonelist.add(el);
        }
        
        //Parse ResourceElement
        Element xmlreslist = rootNode.getChild("ResourceList");
        Element xmlnetlist = xmlreslist.getChild("NetworkList");
        List<Element> xmlnet = xmlnetlist.getChildren("Network");
        for (int i = 0; i < xmlnet.size(); i++) {

            Element xmlnetNode = (Element) xmlnet.get(i);
            //Id
            String id = xmlnetNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlnetNode.getChildText("id"));
            //Status
            String bw = xmlnetNode.getChildText("bandwidth");
            System.out.println("XMLDomainParser --> bandwidth : " + xmlnetNode.getChildText("bandwidth"));

            //PopId
            String type = xmlnetNode.getChildText("type");
            System.out.println("XMLDomainParser --> type : " + xmlnetNode.getChildText("type"));

            //Name
            String delay = xmlnetNode.getChildText("delay");
            System.out.println("XMLDomainParser --> delay : " + xmlnetNode.getChildText("delay"));
            
            //Property
            String srcip = xmlnetNode.getChildText("srcip");
            System.out.println("XMLDomainParser --> srcip : " + xmlnetNode.getChildText("srcip"));
            
             //Property
            String dstip = xmlnetNode.getChildText("dstip");
            System.out.println("XMLDomainParser --> dstip : " + xmlnetNode.getChildText("dstip"));
            
             //Property
            String locid = xmlnetNode.getChildText("localid");
            System.out.println("XMLDomainParser --> localid : " + xmlnetNode.getChildText("localid"));
            
             //Property
            String remid = xmlnetNode.getChildText("remoteid");
            System.out.println("XMLDomainParser --> property : " + xmlnetNode.getChildText("property"));
            
             //Property
            String totcap = xmlnetNode.getChildText("total");
            System.out.println("XMLDomainParser --> total : " + xmlnetNode.getChildText("total"));
            
             //Property
            String rescap = xmlnetNode.getChildText("reserved");
            System.out.println("XMLDomainParser --> reserved : " + xmlnetNode.getChildText("reserved"));
            
             //Property
            String allcap = xmlnetNode.getChildText("allocated");
            System.out.println("XMLDomainParser --> allocated : " + xmlnetNode.getChildText("allocated"));
            
             //Property
            String freecap = xmlnetNode.getChildText("available");
            System.out.println("XMLDomainParser --> available : " + xmlnetNode.getChildText("available"));
            //insert element in ArrayList
            NetworkResource el = new NetworkResource(id, bw, type, delay, srcip, dstip, locid, remid,
                                totcap, rescap, allcap, freecap);
            reslist.put(id, el);
        }
        
        //Parse RANElement
        Element xmlramlist = rootNode.getChild("RANInfoList");
        List<Element> xmlran = xmlramlist.getChildren("RANinfo");
        for (int i = 0; i < xmlran.size(); i++) {

            Element xmlranNode = (Element) xmlran.get(i);
            //Id
            String id = xmlranNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlranNode.getChildText("id"));
            
            String providerid = xmlranNode.getChildText("providerid");
            System.out.println("XMLDomainParser --> providerid : " + xmlranNode.getChildText("providerid"));

            //tenantid
            String tenantid = xmlranNode.getChildText("tenantid");
            System.out.println("XMLDomainParser --> tenantid : " + xmlranNode.getChildText("tenantid"));

            //qosprofile
            String qosprofile = xmlranNode.getChildText("qosprofile");
            System.out.println("XMLDomainParser --> qosprofile : " + xmlranNode.getChildText("qosprofile"));
            
            //srcip
            String srcip = xmlranNode.getChildText("srcip");
            System.out.println("XMLDomainParser --> srcip : " + xmlranNode.getChildText("srcip"));
            
             //dstip
            String dstip = xmlranNode.getChildText("dstip");
            System.out.println("XMLDomainParser --> dstip : " + xmlranNode.getChildText("dstip"));
            
             //serviceid
            String serviceid = xmlranNode.getChildText("serviceid");
            System.out.println("XMLDomainParser --> serviceid : " + xmlranNode.getChildText("serviceid"));
            
            //ip
            String ip = xmlranNode.getChildText("ip");
            System.out.println("XMLDomainParser --> ip : " + xmlranNode.getChildText("ip"));
            
            //rxport
            String rxport = xmlranNode.getChildText("rxport");
            System.out.println("XMLDomainParser --> rxport : " + xmlranNode.getChildText("rxport"));
            
            //rxport
            String txport = xmlranNode.getChildText("rtxport");
            System.out.println("XMLDomainParser --> txport : " + xmlranNode.getChildText("txport"));
            
            RANService el = new RANService(id, providerid, tenantid, srcip, dstip, qosprofile, serviceid, ip , rxport, txport);

            raninfolist.put(id, el);
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

    public List<Zone> getZonelist() {
        return zonelist;
    }

    public void setZonelist(List<Zone> zonelist) {
        this.zonelist = zonelist;
    }

    public HashMap<String, NetworkResource> getReslist() {
        return reslist;
    }

    public void setReslist(HashMap<String, NetworkResource> reslist) {
        this.reslist = reslist;
    }

    public HashMap<String, RANService> getRaninfolist() {
        return raninfolist;
    }

    public void setRaninfolist(HashMap<String, RANService> raninfolist) {
        this.raninfolist = raninfolist;
    }

}
