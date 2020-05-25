/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.crosshaulplugin.events.abstraction;

//import java.lang.String;

/**
 *
 * @author fhuser
 */
public class Parsedomainlist {
    private String filename;
    
    public Parsedomainlist() {
        filename = "";
    }
    public Parsedomainlist(String str) {
        filename = str;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String str) {
        filename = str;
    }
}
