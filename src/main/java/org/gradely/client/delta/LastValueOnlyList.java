/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

/**
 * Not really a list, beacuse it only holds one value, the last value put in.
 * At some point I would like to make this more generic with type variables instead of only bytes.
 * @author Matt
 */
public class LastValueOnlyList {

    //================= Fields =================================
    
    private byte lastValue;
    
    //================= Constructors ===========================

    public LastValueOnlyList() {

    }

    //================= Methods ================================
    
    public void add(byte[] data)
    {
        lastValue = data[data.length-1];
    }
    
    public void add(byte data)
    {
        lastValue = data;
    }
    
    //------------------ Getters and Setters -------------------
    
    public byte getLastValue()
    {
        return lastValue;
    }

}

