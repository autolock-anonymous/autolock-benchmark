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
 * ------------------------
 * PieToolTipGenerator.java
 * ------------------------
 * (C) Copyright 2001-2003, by Simba Management Limited.
 *
 * Original Author:  David Gilbert (for Simba Management Limited);
 * Contributor(s):   -;
 *
 * $Id: PieToolTipGenerator.java,v 1.2 2003/04/24 15:32:51 mungady Exp $
 *
 * Changes
 * -------
 * 13-Dec-2001 : Version 1 (DG);
 * 16-Jan-2002 : Completed Javadocs (DG);
 * 26-Sep-2002 : Fixed errors reported by Checkstyle (DG);
 * 30-Oct-2002 : Category is now a Comparable instance (DG);
 * 07-Mar-2003 : Changed to KeyedValuesDataset and added pieIndex parameter (DG);
 * 21-Mar-2003 : Updated Javadocs (DG);
 * 24-Apr-2003 : Switched around PieDataset and KeyedValuesDataset (DG);
 *
 */

package org.jfree.chart.tooltips;

import org.jfree.data.PieDataset;

/**
 * Interface for a tooltip generator for plots that use data from a {@link PieDataset}.
 * 
 * @author David Gilbert
 */
public interface PieToolTipGenerator extends ToolTipGenerator {

    /**
     * Generates a tool tip text item for a particular category in the dataset.
     * 
     * @param data  the dataset.
     * @param key  the item key.
     * @param pieIndex  the pie index (differentiates between pies in a 'multi' pie chart).
     *
     * @return The tool tip text.
     */
    public String generateToolTip(PieDataset data, Comparable key, int pieIndex);

}
