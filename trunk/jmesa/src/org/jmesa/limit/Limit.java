/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesa.limit;

import java.io.Serializable;

/**
 * <p>
 * The name Limit comes from the MySQL limit command, and the the purpose of the Limit interface is
 * to know how to limit the table results. The implemenation of the Limit knows how the user
 * interacted with the table with regards to sorting, filtering, paging, max rows to display, and
 * exporting. With this information you will be able to display the requested page filtered and
 * sorted correctly in the most efficient manner possible.
 * </p>
 * 
 * <p>
 * The RowSelect needs to be added to the Limit so that the row information is available.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface Limit extends Serializable {

    /**
     * @return The code to uniquely identify the table.
     */
    public String getId();

    /**
     * <p>
     * A RowSelect represents the row information.
     * </p>
     */
    public RowSelect getRowSelect();

    /**
     * <p>
     * The RowSelect needs to be set on the Limit for the Limit to be useful. Of course the
     * RowSelect cannot be created until the total rows is known.
     * </p>
     * 
     * <p>
     * The idea is you first create a Limit and use the FilterSet to retrieve the total rows. Once
     * you have the total rows you can create a RowSelect and pass it in here.
     * </p>
     * 
     * @param rowSelect The RowSelect to use for this Limit.
     */
    public void setRowSelect(RowSelect rowSelect);

    /**
     * <p>
     * A FilterSet represents the set of Filter objects.
     * </p>
     */
    public FilterSet getFilterSet();

    public void setFilterSet(FilterSet filterSet);

    /**
     * <p>
     * A SortSet represents the set of Sort objects.
     * </p>
     */
    public SortSet getSortSet();

    public void setSortSet(SortSet sortSet);

    /**
     * <p>
     * Check to see if the user is trying to export a table.
     * </p>
     *
     * <p>
     * Note: Should be using the isExported() method now.
     * </p>
     * 
     * @return Is true if the user invoked an export.
     */
    @Deprecated
    public boolean isExportable();

    /**
     * <p>
     * Check to see if the user is trying to export a table.
     * </p>
     * 
     * @return Is true if the user invoked an export.
     */
    public boolean isExported();

    /**
     * <p>
     * The Export represents the export that the user invoked.
     * @deprecated Replaced by {@link #getExportType()}
     * </p>
     */
    @Deprecated
    public Export getExport();

    /**
     * @param export The current export requested.
     * @deprecated Replaced by {@link #setExportType(ExportType)}
     */
    @Deprecated
    public void setExport(Export export);

    /**
     * <p>
     * The ExportType represents the export that the user invoked.
     * </p>
     */
    public ExportType getExportType();

    public void setExportType(ExportType exportType);

    public boolean isComplete();
}