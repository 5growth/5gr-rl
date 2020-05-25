/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common;

import com.rl.common.objects.DomainElem;
import com.rl.common.objects.LocalPAConfig;
import com.rl.common.objects.MonitoringConfig;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
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
public class Federation_XMLDomainParser {
    private ArrayList<NfviPop> pops;
  
    
    public Federation_XMLDomainParser(String xmlFile) {
        pops = new ArrayList();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParse --> Root Element :: " + rootNode.getName());

        //Parse domain List
        Element domains = rootNode.getChild("NfviPoPs");
        List<Element> list = domains.getChildren("NfviPoP");

        for (int i = 0; i < list.size(); i++) {

            Element DomainNode = (Element) list.get(i);
           
            String popId = DomainNode.getChildText("Id");
            System.out.println("XMLDomainParse --> Id : " + popId);
           
            String federatedVimId = DomainNode.getChildText("FederatedVimId");
            System.out.println("XMLDomainParse --> FederatedVimId : " + federatedVimId);

            String networkConnectivityEndpoints = DomainNode.getChildText("networkConnectivityEndpoints");
            System.out.println("XMLDomainParse --> networkConnectivityEndpoints : " + networkConnectivityEndpoints);
            
           

           

            //insert element in ArrayList
            NfviPop el = new NfviPop();
            el.setNfviPopId(popId);
            el.setVimId(popId);
            el.setNetworkConnectivityEndpoint(networkConnectivityEndpoints);

            pops.add(el);
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

    public NfviPop getPops() {
        if (pops.isEmpty()) 
            return new NfviPop();
        return pops.remove(0);
    }

    public void setPops(NfviPop pop) {
        pops.add(pop);
    }

  

}
