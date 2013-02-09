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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.Serializable;

/**
 * This was added for complience with Syncany's file upload/download plugin architecture. 
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 * 
 */
public interface Configurable extends Serializable {

    
    /**
     * Turns a properties file back into an object.
     * @throws FileNotFoundException If the file does not exist
     * @throws IOException If the file cannot be read.
     */
    public void load() throws FileNotFoundException, IOException;
    
     /**
     * <p>Saves the config file as a properties file.</p>
     * <p>Each sub-configuration is saved in it's own file on the hard disk. The main config.Configuration contains the location of the configuration directory.</p>
     * @throws IOException If the file cannot be written to.
     */
    public void save() throws IOException;
}
