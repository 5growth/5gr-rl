/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi;


import com.ericsson.dummyplugin.sbi.objects.MECRegionResource;
import java.io.File;
import java.io.IOException;
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

    private HashMap<String, MECRegionResource> reslist;
    
    public XMLDomainParser(String xmlFile) {
        reslist = new HashMap();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParser --> Root Element :: " + rootNode.getName());
        
        
        
        
        //Parse ResourceElement
        Element xmlreslist = rootNode.getChild("ResourceList");
        Element xmlnetlist = xmlreslist.getChild("MECRegionList");
        List<Element> xmlnet = xmlnetlist.getChildren("MECRegion");
        for (int i = 0; i < xmlnet.size(); i++) {

            Element xmlnetNode = (Element) xmlnet.get(i);
            //Id
            String id = xmlnetNode.getChildText("id");
            System.out.println("XMLDomainParser --> id : " + xmlnetNode.getChildText("id"));
            
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
            MECRegionResource el = new MECRegionResource(id, latitude, longitude, altitude, range);
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

    public HashMap<String, MECRegionResource> getReslist() {
        return reslist;
    }

    public void setReslist(HashMap<String, MECRegionResource> reslist) {
        this.reslist = reslist;
    }

}
