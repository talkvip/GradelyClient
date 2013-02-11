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
package org.gradely.client.gui.settings;

import org.gradely.client.FilePath;

/**
 * This class represents a item in the left-hand part of the main SettingsDialog. Used to create the TreeNodes as TreeNodes take user-created arguments in their constructor. 
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class TreeItem {
    
    /**
     * The displayed test of the tree node 
     */
    private String description;
    
    /**
     * The path to the icon displayed next to the text.
     */
    private FilePath iconFilePath;

    public TreeItem(String description, FilePath iconFilePath) {
        this.description = description;
        this.iconFilePath = iconFilePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }        

    public FilePath getIconFilePath() {
        return iconFilePath;
    }

    @Override
    public String toString() {
        return description;
    }

}
