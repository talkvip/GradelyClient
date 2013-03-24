/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

/**
 * Describes a single diff entry/operation
 */
public class DiffOperation
{
    /**
     * True if it the data is unique to the parent, False if it is unique to the child.
     */
    public boolean isUniqueToParent;

    public long startLocation;

    public int length;

    public byte[] data;
    
    
    DiffOperation()
    {

    }

    /**
     * @return the isUniqueToParent
     */
    public boolean getIsUniqueToParent() {
        return isUniqueToParent;
    }

    /**
     * @return the startLocation
     */
    public long getStartLocation() {
        return startLocation;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param isUniqueToParent the isUniqueToParent to set
     */
    public void setIsUniqueToParent(boolean isUniqueToParent) {
        this.isUniqueToParent = isUniqueToParent;
    }

    /**
     * @param startLocation the startLocation to set
     */
    public void setStartLocation(long startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}
