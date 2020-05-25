/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Creation;

import com.rl.common.objects.DomainElem;
import java.util.*;


/**
 *
 * @author Fabio Ubaldi
 * this event provide the list of domain list that TP has to open
 * a communication socket (HTTP/REST).
 * This message is consumed by the SBI IF class
 */
public class DomainSubscriber {
    private ArrayList<DomainElem> domainlist;
    
    public DomainSubscriber() {
        domainlist = new ArrayList();
    }
    
    //get/set function
    public DomainElem getDomainElem() {
        if (domainlist.isEmpty()) 
            return new DomainElem();
        return domainlist.remove(0);
    }

    public void setDomainElem(DomainElem domelem) {
        domainlist.add(domelem);
    }
    public ArrayList<DomainElem> getDomainList() {
        return domainlist;
    }

    public void setDomainList(ArrayList<DomainElem> domlist) {
        domainlist = domlist;
    }
}
