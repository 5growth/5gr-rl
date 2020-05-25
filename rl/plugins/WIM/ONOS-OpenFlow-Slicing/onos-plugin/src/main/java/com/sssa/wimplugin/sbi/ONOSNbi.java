
package com.sssa.wimplugin.sbi;


//import static com.sssa.wimplugin.Controller.Hex2Decimal.decHex;
//import com.sssa.wimplugin.Utility.XMLUtility;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import static com.sssa.wimplugin.Controller.Hex2Decimal.hexDec;


import java.util.Iterator;


import org.json.simple.parser.JSONParser;

//import com.sssa.wimplugin.Controller.Route;
//import com.sssa.wimplugin.Controller.RouteId;
//import com.sssa.wimplugin.Controller.NodePortTuple;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ONOSNbi {
   

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

    ONOSNbi http = new ONOSNbi();
             //http.getRateperLink();
          //http.getFlowIdFromIntent("0x2b5");
          // http.getCPManMessages();
       // http.getIntents();
        //  http.getTopology();
        //http.getDevicesPorts();
       // http.sendGet("127.0.0.1", "127.0.0.1", "1");
         // http.sendGetPingFile("127.0.0.1", "127.0.0.1", "1");
       //  http.sendHostToHostIntent("00:00:00:00:00:05/None", "00:00:00:00:00:05/None");
       //http.sendServiceRequest();
         // http.parseXML();
           // http.getDisjointRoutes("of:0000000000000001","1", "of:000000000000000a", "1");
           
          http.showJSON("SLICE-1", "interf1", "interf2", "1", "7", "1", "1", "101");
    }
       
       
        /*****************************************************************************/       
            public void testCode()
        {
       
            /*Pattern pattern = Pattern.compile("\\d+(\\.\\d+)? ms",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
      String csvRow = "";
     
                 Matcher matcher = pattern.matcher(" 9.88 Mbits/sec   1684 ms  117/ 8517 (14.5%)");
                 if (matcher.find()) {
                 System.out.println("matcher.group(0): "+matcher.group(0));

          }*/
                 String command = "ping 10.0.0.1";
                   
                    try{
                   
                    Runtime r = Runtime.getRuntime();
                    Process p = r.exec(command);
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                            String inputLine;
                            while ((inputLine = in.readLine()) != null)
                            {
                                System.out.println(inputLine);
                            }   }
                           
                    }catch(IOException e){}
                   
           }
       
        /*****************************************************************************
       
        public void parseXML()
        {
       
                // for(int ind=1; ind<=1; ind++)
                   //     {
                    Document d = XMLUtility.getInstance().loadXML("paths/path0-_0.xml");
                   
                    String dstIp = ((Element) d.getDocumentElement().getElementsByTagName("Response").item(0)).getAttribute("dst");
         
                   
                    String direction1 = ((Element) d.getDocumentElement().getElementsByTagName("Response").item(0)).getAttribute("direction");
                    String direction = ((Element) d.getDocumentElement().getElementsByTagName("direction").item(0)).getNodeName();
                    String direction2 = ((Element) d.getDocumentElement().getElementsByTagName("direction").item(0)).getTextContent();

                    System.out.println(dstIp);
                    System.out.println(direction);
                    System.out.println(direction1);
                    System.out.println(direction2);
 
                    //} 
                       // }
       
}
* 
       
   /***********************************************************************************/
            public void showJSON(String sliceID, String interfId1, String interfId2, String src, String dest, String srcPort, String dstPort, String VLAN)
            {
            
        JSONObject vpls = new JSONObject();
      
        JSONArray vplsElements = new JSONArray();
        
        JSONObject vplsElem1 = new JSONObject();
        
        vplsElem1.put("name", sliceID);
        
        vplsElem1.put("encapsulation", "NONE");
        
        JSONArray vplsInterfaces = new JSONArray();
        
        JSONObject interf1 = new JSONObject();
        
        interf1.put("name", interfId1);
        interf1.put("connect point", src.concat("/").concat(srcPort));
        interf1.put("vlan", VLAN);
        
        JSONObject interf2 = new JSONObject();
        
        interf2.put("name", interfId2);
        interf2.put("connect point", dest.concat("/").concat(dstPort));
        interf2.put("vlan", VLAN);
     
        vplsInterfaces.put(0, interf1);
        vplsInterfaces.put(1, interf2);
        
        vplsElem1.put("interfaces", vplsInterfaces);
        
        vplsElements.put(0, vplsElem1);
        
        vpls.put("vpls", vplsElements);
       
        
        String jsonData=vpls.toString();
      
        System.out.println("jsonData vpls slice");
        System.out.println(jsonData);
      
            
            }
    /**********************************************************************************/        
        public void parseJSON()
        {
      /*  String test = " {\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_SRC\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:00:00:00:00:01\"},{\"port\":\"6\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"groupId\":0,\"priority\":1,\"deviceId\":\"of:0000000000000004\",\"timeout\":0,\"life\":108,\"packets\":8066,\"isPermanent\":true,\"lastSeen\":1490198107552,\"bytes\":12065704,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":4,\"type\":\"IN_PORT\"},{\"type\":\"ETH_DST\",\"mac\":\"00:00:00:00:00:0B\"}]},\"id\":\"26458651902350877\",\"state\":\"ADDED\"}";
           String requestID = "";
          Pattern pattern3 = Pattern.compile("\"priority\":(.*?)\"");
           Matcher matcher3 = pattern3.matcher(test);
                if (matcher3.find())
                   {
                      requestID = matcher3.group(1);

                   
                    }
               
                System.out.println("requestID: "+matcher3.group(1));
               
                System.out.println("paths/path" + "1" + "-_" + "1" + ".xml");*/
           
            String test = "http://localhost:8181/onos/v1/links?device=of%3A000000000000000a&port=7";
            String sw = "";
            String port = "";
           
          /*  Pattern pat1 = Pattern.compile("http://localhost:8181/onos/v1/links?device=\\w+");
             Matcher mat1 = pat1.matcher(test);
                if (mat1.find())
                   {
                    sw = mat1.group(1);
                    }
        
               
             Pattern pat2 = Pattern.compile(sw.concat("port=(.*?)"));
             Matcher mat2 = pat2.matcher(test);
                if (mat2.find())
                   {
                      port = mat2.group(1);
                    }*/
           
            sw = test.substring(43, 64);
           port = test.substring(70, 71);
        
                System.out.println("sw: "+sw);
                System.out.println("port: "+port);
                sw = test.substring(43, 64);
                port = test.substring(70, 71);
                 sw = sw.replace("%3A", ":");
                
                 String couple = sw.concat("-").concat(port);
                 System.out.println("couple: "+couple);
       
        }
       
       
       
     /**
     * @return 
    /************************************************************************************************/
}