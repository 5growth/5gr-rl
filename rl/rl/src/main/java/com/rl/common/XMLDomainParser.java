/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common;

import com.rl.common.objects.DomainElem;
import com.rl.common.objects.LocalPAConfig;
import com.rl.common.objects.MonitoringConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private ArrayList<DomainElem> doms;
    private LocalPAConfig pa_net;
    private LocalPAConfig pa_comp;
    private MonitoringConfig mon;
    
    public XMLDomainParser(String xmlFile) {
        doms = new ArrayList();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParse --> Root Element :: " + rootNode.getName());

        //Parse domain List
        Element domains = rootNode.getChild("Domains");
        List<Element> list = domains.getChildren("Domain");

        for (int i = 0; i < list.size(); i++) {

            Element DomainNode = (Element) list.get(i);
            //Domain type
            String type = DomainNode.getChildText("Type");
            System.out.println("XMLDomainParse --> Type : " + DomainNode.getChildText("Type"));
            //Domain Name
            String name = DomainNode.getChildText("Name");
            System.out.println("XMLDomainParse --> Name : " + DomainNode.getChildText("Name"));

            //Domain Id
            Long id = Long.parseLong(DomainNode.getChildText("Id"));
            System.out.println("XMLDomainParse --> Id : " + id);
            
            //Domain Id
            Long mecid = Long.parseLong(DomainNode.getChildText("MecId"));
            System.out.println("XMLDomainParse --> MecId : " + id);
            
            //Domain IP
            String ip = DomainNode.getChildText("Ip");
            System.out.println("XMLDomainParse --> Ip : " + DomainNode.getChildText("Ip"));

            //Domain Port
            Long port = Long.parseLong(DomainNode.getChildText("Port"));
            System.out.println("XMLDomainParse --> Port : " + port);

            //insert element in ArrayList
            DomainElem el = new DomainElem(ip, port, name, type, id, mecid);

            doms.add(el);
        }
        //Parse Local PA Config
        Element paconfigs = rootNode.getChild("LocalPAConfig");
        //Parse PA Compute Config
        Element PAComp = paconfigs.getChild("Compute");
        
        String compstatus = PAComp.getChildText("Status");
        System.out.println("XMLDomainParse --> PA compute Status : " + PAComp.getChildText("Status"));
        
        //Domain Name
        String compname = PAComp.getChildText("Name");
        System.out.println("XMLDomainParse --> PA compute Name : " + PAComp.getChildText("Name"));

        //Domain IP
        String compip = PAComp.getChildText("Ip");
        System.out.println("XMLDomainParse --> PA Compute Ip : " + PAComp.getChildText("Ip"));

        //Domain Port
        Long compport = Long.parseLong(PAComp.getChildText("Port"));
        System.out.println("XMLDomainParse --> PA Compute Port : " + compport);

        //insert element in LocalPAConfig
        pa_comp = new LocalPAConfig( compip, compport, compname, compstatus);

        //Parse PA Network Config
        Element PANet = paconfigs.getChild("Network");
 
        String netstatus = PANet.getChildText("Status");
        System.out.println("XMLDomainParse --> PA network Status : " + PANet.getChildText("Status"));
        
        //Domain Name
        String netname = PANet.getChildText("Name");
        System.out.println("XMLDomainParse --> PA network Name : " + PANet.getChildText("Name"));

        //Domain IP
        String netip = PANet.getChildText("Ip");
        System.out.println("XMLDomainParse --> PA network Ip : " + PANet.getChildText("Ip"));

        //Domain Port
        Long netport = Long.parseLong(PANet.getChildText("Port"));
        System.out.println("XMLDomainParse --> PA network Port : " + netport);

        //insert element in LocalPAConfig
        pa_net = new LocalPAConfig( netip, netport, netname, netstatus); 
        
        Element monconfigs = rootNode.getChild("MonitoringConfig");
        
        String monip = monconfigs.getChildText("Ip");
        System.out.println("XMLDomainParse --> Mon network Ip : " + monconfigs.getChildText("Ip"));
        
        Long monport = Long.parseLong(monconfigs.getChildText("Port"));
        System.out.println("XMLDomainParse --> Mon network Port : " + monconfigs.getChildText("Port"));
        
        String monname = monconfigs.getChildText("Name");
        System.out.println("XMLDomainParse --> PA network Name : " + monconfigs.getChildText("Name"));
        
        mon = new MonitoringConfig(monip, monport, monname);
        
        //Parse AppD
        //TODO R2: Build parser for AppD
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
    
    //get/set function
    public DomainElem getDomainElem() {
        if (doms.isEmpty()) 
            return new DomainElem();
        return doms.remove(0);
    }

    public void setDomainElem(DomainElem domelem) {
        doms.add(domelem);
    }

    public LocalPAConfig getPa_net() {
        return pa_net;
    }

    public void setPa_net(LocalPAConfig pa_net) {
        this.pa_net = pa_net;
    }

    public LocalPAConfig getPa_comp() {
        return pa_comp;
    }

    public void setPa_comp(LocalPAConfig pa_comp) {
        this.pa_comp = pa_comp;
    }

    public MonitoringConfig getMon() {
        return mon;
    }

    public void setMon(MonitoringConfig mon) {
        this.mon = mon;
    }

}
