/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi;

import com.ericsson.xenplugin.sbi.objects.NFVIPop;
import com.ericsson.xenplugin.sbi.objects.ComputeResource;
import com.ericsson.xenplugin.sbi.objects.XenService;
import com.ericsson.xenplugin.sbi.objects.Zone;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
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
    private HashMap<String, ComputeResource> reslist;
    private HashMap<String, XenService> xeninfolist;
    
    public XMLDomainParser(String xmlFile) {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        reslist = new HashMap();
        xeninfolist = new HashMap();
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
        Element xmlnetlist = xmlreslist.getChild("ComputeList");
        List<Element> xmlnet = xmlnetlist.getChildren("Compute");
        for (int i = 0; i < xmlnet.size(); i++) {
            Element xmlnetNode = (Element) xmlnet.get(i);
            String tmp = xmlnetNode.getChildText("type");
            if (tmp.compareTo("mem") == 0) {
             //Id
            String id = xmlnetNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlnetNode.getChildText("id"));
            //Id
            String type = xmlnetNode.getChildText("type");
            System.out.println("XMLDomainParser --> type : " + xmlnetNode.getChildText("type"));
            //Id
            String memsize = xmlnetNode.getChildText("memsize");
            System.out.println("XMLDomainParser --> memsize : " + xmlnetNode.getChildText("memsize"));
            //Id
            String AccelerationCap = xmlnetNode.getChildText("AccelerationCap");
            System.out.println("XMLDomainParser --> AccelerationCap : " + xmlnetNode.getChildText("AccelerationCap"));
            //Id
            String oversubsriptionpolicy = xmlnetNode.getChildText("oversubsriptionpolicy");
            System.out.println("XMLDomainParser --> oversubsriptionpolicy : " + xmlnetNode.getChildText("oversubsriptionpolicy"));
            //Id
            String numasupported = xmlnetNode.getChildText("numasupported");
            System.out.println("XMLDomainParser --> numasupported : " + xmlnetNode.getChildText("numasupported"));
            //Property
            String total = xmlnetNode.getChildText("total");
            System.out.println("XMLDomainParser --> total : " + xmlnetNode.getChildText("total"));
            //Property
            String available = xmlnetNode.getChildText("available");
            System.out.println("XMLDomainParser --> available : " + xmlnetNode.getChildText("available"));
            //Property
            String reserved = xmlnetNode.getChildText("reserved");
            System.out.println("XMLDomainParser --> reserved : " + xmlnetNode.getChildText("reserved"));
            //Property
            String allocated = xmlnetNode.getChildText("allocated");
            System.out.println("XMLDomainParser --> allocated : " + xmlnetNode.getChildText("allocated"));
            
            //insert element in ArrayList
            ComputeResource el = new ComputeResource(id, type, AccelerationCap, memsize, oversubsriptionpolicy, 
            Boolean.parseBoolean(numasupported), null, null, null, false,
            total, available, reserved, allocated);
            reslist.put(id, el);
            } else if (tmp.compareTo("cpu") == 0) {
                //Id
            String id = xmlnetNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlnetNode.getChildText("id"));
            //Id
            String type = xmlnetNode.getChildText("type");
            System.out.println("XMLDomainParser --> type : " + xmlnetNode.getChildText("id"));
            //Id
            String AccelerationCap = xmlnetNode.getChildText("AccelerationCap");
            System.out.println("XMLDomainParser --> AccelerationCap : " + xmlnetNode.getChildText("AccelerationCap"));
            //Id
            String architecture = xmlnetNode.getChildText("architecture");
            System.out.println("XMLDomainParser --> architecture : " + xmlnetNode.getChildText("architecture"));
            //Id
            String numvcpu = xmlnetNode.getChildText("numvcpu");
            System.out.println("XMLDomainParser --> numvcpu : " + xmlnetNode.getChildText("numvcpu"));
            //Id
            String cpuclock = xmlnetNode.getChildText("cpuclock");
            System.out.println("XMLDomainParser --> cpuclock : " + xmlnetNode.getChildText("cpuclock"));
            //Id
            String oversubsriptionpolicy = xmlnetNode.getChildText("oversubsriptionpolicy");
            System.out.println("XMLDomainParser --> oversubsriptionpolicy : " + xmlnetNode.getChildText("oversubsriptionpolicy"));
            //Id
            String pinningsupported = xmlnetNode.getChildText("pinningsupported");
            System.out.println("XMLDomainParser --> pinningsupported : " + xmlnetNode.getChildText("pinningsupported"));
           //Property
            String total = xmlnetNode.getChildText("total");
            System.out.println("XMLDomainParser --> total : " + xmlnetNode.getChildText("total"));
            //Property
            String available = xmlnetNode.getChildText("available");
            System.out.println("XMLDomainParser --> available : " + xmlnetNode.getChildText("available"));
            //Property
            String reserved = xmlnetNode.getChildText("reserved");
            System.out.println("XMLDomainParser --> reserved : " + xmlnetNode.getChildText("reserved"));
            //Property
            String allocated = xmlnetNode.getChildText("allocated");
            System.out.println("XMLDomainParser --> allocated : " + xmlnetNode.getChildText("allocated"));
            
            ComputeResource el = new ComputeResource(id, type, AccelerationCap, null, oversubsriptionpolicy, false,
                    architecture, numvcpu, cpuclock, Boolean.parseBoolean(pinningsupported),
                    total, available, reserved, allocated);
            reslist.put(id, el);
            } else {
                System.out.println("XMLDomainParse --> Error compute type. Unknown Type = " + tmp);
                exit(-1);
            }
        }
        
        //Parse XenElement
        Element xmlxenlist = rootNode.getChild("XENInfoList");
        List<Element> xmlxen = xmlxenlist.getChildren("XENInfo");
        for (int i = 0; i < xmlxen.size(); i++) {
            Element xmlranNode = (Element) xmlxen.get(i);
             //zoneid
            String zoneid = xmlranNode.getChildText("zoneid");
            System.out.println("XMLDomainParser --> zoneid : " + xmlranNode.getChildText("zoneid"));
            
            String floatingip = xmlranNode.getChildText("floatingip");
            System.out.println("XMLDomainParser --> floatingip : " + xmlranNode.getChildText("floatingip"));
            //master
            String master = xmlranNode.getChildText("master");
            System.out.println("XMLDomainParser --> master : " + xmlranNode.getChildText("master"));
            //username
            String username = xmlranNode.getChildText("username");
            System.out.println("XMLDomainParser --> username : " + xmlranNode.getChildText("username"));
            //password
            String password = xmlranNode.getChildText("password");
            System.out.println("XMLDomainParser --> password : " + xmlranNode.getChildText("password"));
            
            XenService el = new XenService(zoneid, master, username, password, floatingip);
            xeninfolist.put(zoneid, el);
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

    public HashMap<String, ComputeResource> getReslist() {
        return reslist;
    }

    public void setReslist(HashMap<String, ComputeResource> reslist) {
        this.reslist = reslist;
    }

    public HashMap<String, XenService> getXeninfolist() {
        return xeninfolist;
    }

    public void setXeninfolist(HashMap<String, XenService> raninfolist) {
        this.xeninfolist = raninfolist;
    }

}
