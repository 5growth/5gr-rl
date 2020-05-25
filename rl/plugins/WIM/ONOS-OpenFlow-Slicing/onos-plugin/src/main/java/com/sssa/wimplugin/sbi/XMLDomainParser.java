
package com.sssa.wimplugin.sbi;

import com.sssa.wimplugin.sbi.objects_.NFVIPop;
import com.sssa.wimplugin.sbi.objects_.NetworkResource;
import com.sssa.wimplugin.sbi.objects_.Zone;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class XMLDomainParser {
    
    private List<NFVIPop> nfvipoplist;
    private List<Zone> zonelist;
    private HashMap<String, NetworkResource> networkresourceslist;
    
    public XMLDomainParser(String xmlFile) { 
        
        nfvipoplist = new ArrayList();
        zonelist = new ArrayList();
        networkresourceslist = new HashMap();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParser --> Root Element :: " + rootNode.getName());
        
        
        //Parse NFVIPOPElement
        Element xmlpoplist = rootNode.getChild("NVFIPopList");
        List<Element> xmlpop = xmlpoplist.getChildren("NfviPop");
        System.out.println("XMLDomainParser --> Number of NfviPops: " + xmlpop.size());
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

            nfvipoplist.add(el);
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
            networkresourceslist.put(id, el);
        }
               
        
    }
  
    public static Document getSAXParsedDocument(final String fileName)
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
        return nfvipoplist;
    }

    public void setPoplist(List<NFVIPop> poplist) {
        this.nfvipoplist = poplist;
    }

    public List<Zone> getZonelist() {
        return zonelist;
    }

    public void setZonelist(List<Zone> zonelist) {
        this.zonelist = zonelist;
    }

    public HashMap<String, NetworkResource> getReslist() {
        return networkresourceslist;
    }

    public void setReslist(HashMap<String, NetworkResource> reslist) {
        this.networkresourceslist = reslist;
    }

}
