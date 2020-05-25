/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Creation;

//import java.lang.String;

/**
 *
 * @author fhuser
 */
public class ParseFederatedPopList {
    private String filename;
    
    public ParseFederatedPopList() {
        filename = "";
    }
    public ParseFederatedPopList(String str) {
        filename = str;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String str) {
        filename = str;
    }
}
