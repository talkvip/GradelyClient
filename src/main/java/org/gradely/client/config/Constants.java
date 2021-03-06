/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gradely.client.config;


/**
 * Originally from syncany. Needed for plugin. Plugins for file upload and download. Subject to reworking.
 * @author Philipp C. Heckel
 * @author Matt
 */
public abstract class Constants {
    
        //name of the application, no spaces, all lower case.
    public static final String appName = "gradely";
    public  static final String formalAppName = "Gradely";
    
    //----------------- config file -------------------------------
    public static final String configurationFileName = appName+".props";// = appName + ".props";
    
    //------------------ Deduplication ----------------------------
    /**
     * Block size for deduplication files.
     */
    public static final int blockSize = 1024*5; 
    
    public static final String dateFormat = "yyyy/MM/dd HH:mm:ss Z";
    
    //----------------- Networking Locations ---------------------
    
    

}
