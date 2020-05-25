/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class ComputeTerminateElem {
    private List<String> input;
    private List<String> output;
    public ComputeTerminateElem() {
        output = new ArrayList();
        input = new ArrayList();
    }
    
    public void setTerminateRequest(List<String> in) {
        input = in;
    }
    public List<String> getTerminateRequest() {
        return input;
    }
    public void setTerminateReply(List<String> out) {
        output = out;
    }
    public List<String> getTerminateReply() {
        return output;
    }
    public void addTerminateReplyElem(String el) {
        output.add(el);
    }
}
