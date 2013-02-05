
package org.hwdb.client.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Parses json into meaningful action.
 * @author Matt
 */
public class JsonParser {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public JsonParser() {

    }

    //================= Methods ================================
    
    /**
     * Turns a Json response into a key-value dictionary.
     */
    public LinkedList<JSONObject> produceKeyValueMap(String json) throws ParseException
    {
        JSONParser parser=new JSONParser();
        JSONArray array=(JSONArray)parser.parse(json);
        
        Iterator i = array.iterator();
        
        LinkedList<JSONObject> objLst = new LinkedList();
        
        int j = 0;
        while(i.hasNext())
        {
            objLst.add((JSONObject)array.get(j));
        }
        
        return objLst;

    }
    
    //------------------ Getters and Setters -------------------
}
