/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.gradely.client.logging.Logging;

/**
 * This is a JSON object. It represents a Json object.
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public class Json {

    //================= Fields =================================
    
    private Map<String, Object> map;
    private String jsonString;
    
    private boolean mapHasChanged = true;
    private boolean stringHasChanged = true;
    
    
    //================= Constructors ===========================

    public Json(Map<String, Object> map) {
        
        this.map = map;
        mapToJson();
        
        mapHasChanged = false;
        stringHasChanged = false;
    }
    
    public Json(String json)
    {
        this.jsonString = json;
        readInJson();
        
        mapHasChanged = false;
        stringHasChanged = false;
    }

    //================= Methods ================================
    
    /**
     * Sets create, read, update, or delete. 
     */
    public void setCRUD(CrudEnum type)
    {
        map.put("action", type.toString().toLowerCase());
    }
    
     /**
     * Turns a key-value dictionary into a json object. 
     */
    private void mapToJson()
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            jsonString = mapper.writeValueAsString(map);
        }
        catch(IOException e)
        {
            Logging.error("There was a IO problem converting data to the Json format for transfer.", e);
        }
    }
    
    /**
     * converts a json string into a key-value map. 
     */
    private void readInJson()
    {
        JsonFactory factory = new JsonFactory(); 
        ObjectMapper mapper = new ObjectMapper(factory);

        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {}; 
        
        try
        {
            map = mapper.readValue(jsonString, typeRef); 
        }
        catch(IOException e)
        {
            Logging.error("A IOException threw when parsing Json from a string. WHY?", e);
        }
    }
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the map
     */
    public Map<String, Object> getMap() {
        
        if(stringHasChanged == true)
        {
            readInJson();
        }
        
        return map;
    }

    /**
     * @return the jsonString
     */
    public String getJsonString() {
        
        if(mapHasChanged == true)
        {
            mapToJson();
        }
        
        return jsonString;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map<String, Object> map) {
        this.map = map;
        mapHasChanged = true;
    }

    /**
     * @param jsonString the jsonString to set
     */
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
        stringHasChanged = true;
    }
            

            
}
