/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.util.Comparator;

/**
 *
 * @author Matt
 */
/**
 * @author Matt
 * Sorts DiffOperations by parent or child, then start location.
 */
public class DiffOperationStartValueComparator implements Comparator<DiffOperation>
{
    @Override
    public int compare(DiffOperation a, DiffOperation b) {

        if(a.getStartLocation() != b.getStartLocation())
        {
            if(a.getStartLocation() > b.getStartLocation())
            {
                return 1;
            }
            else if(a.getStartLocation() < b.getStartLocation())
            {
                return -1;
            }
        }

        if(a.getLength() != b.getLength())
        {
            if(a.getLength() > b.getLength())
            {
                return 1;
            }
            else if(a.getLength() < b.getLength())
            {
                return -1;
            }
        }

        return 0;

    }
}
