/* ======================================
 * JFreeChart : a free Java chart library
 * ======================================
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 * Project Lead:  David Gilbert (david.gilbert@object-refinery.com);
 *
 * (C) Copyright 2000-2003, by Simba Management Limited and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * --------------
 * SortOrder.java
 * --------------
 * (C) Copyright 2003 by Simba Management Limited.
 *
 * Original Author:  David Gilbert (for Simba Management Limited);
 * Contributor(s):   -;
 *
 * $Id: SortOrder.java,v 1.1 2003/04/23 16:44:25 mungady Exp $
 *
 * Changes:
 * --------
 * 05-Mar-2003 : Version 1 (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 */

package org.jfree.data;

import java.io.Serializable;

/**
 * Used to indicate sorting order (ascending or descending).
 * 
 * @author David Gilbert
 */
public class SortOrder implements Serializable {

    /** Ascending order. */
    public static final SortOrder ASCENDING = new SortOrder("SortOrder.ASCENDING");
    
    /** Descending order. */
    public static final SortOrder DESCENDING = new SortOrder("SortOrder.DESCENDING");

    /** The name. */
    private String name;
    
    /**
     * Private constructor.
     * 
     * @param name  the name.
     */
    private SortOrder(String name) {
        this.name = name;
    }
    
    /**
     * Returns a string representing the object.
     * 
     * @return The string.
     */
    public String toString() {
        return this.name;
    }
    
    /**
     * Returns <code>true</code> if this object is equal to the specified object, and 
     * <code>false</code> otherwise.
     * 
     * @param o  the other object.
     * 
     * @return A boolean.
     */
    public boolean equals(Object o) {
        
        if (this == o) {
            return true;
        }
        if (!(o instanceof SortOrder)) {
            return false;
        }

        final SortOrder order = (SortOrder) o;
        if (!this.name.equals(order.toString())) {
            return false;
        }

        return true;
        
    }
    
}
