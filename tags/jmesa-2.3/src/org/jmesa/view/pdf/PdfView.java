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
package org.jmesa.view.pdf;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlSnippets;
import org.jmesa.view.html.HtmlSnippetsImpl;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the Flying Saucer API to generate Pdf documents.
 * 
 * @since 2.2
 * @author Paul Horn
 */
public class PdfView implements View {

    private static Logger logger = LoggerFactory.getLogger(PdfView.class);
    private HtmlTable table;
    private HtmlSnippets snippets;
    private WebContext webContext;
    private CoreContext coreContext;
    private String cssLocation;
    private String doctype;

    public PdfView(HtmlTable table, Toolbar toolbar, WebContext webContext, CoreContext coreContext) {
        this.table = table;
        this.webContext = webContext;
        this.coreContext = coreContext;
        this.snippets = new HtmlSnippetsImpl(table, toolbar, coreContext);

        this.cssLocation = coreContext.getPreference("pdf.cssLocation");
        this.doctype = coreContext.getPreference("pdf.doctype");
    }

    public HtmlTable getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = (HtmlTable) table;
    }

    /**
     * @return The stylesheet to use for this pdf.
     */
    public String getCssLocation() {
        return cssLocation;
    }

    /**
     * <p>
     * The css to use for this pdf file. Will be relative to the servlet context.
     * </p>
     * 
     * <p>
     * example: /css/jmesa-pdf-landscape.css
     * <p>
     * 
     * @param cssLocation The path and name of the jmesa css file.
     */
    public void setCssLocation(String cssLocation) {
        this.cssLocation = cssLocation;
    }

    public byte[] getBytes() {
        String render = (String) render();

        try {
            return render.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("Not able to process the PDF file using the UTF-8 encoding.");
        }

        return render.getBytes();
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();

        String contextPath = webContext.getContextPath();

        html.append(doctype);

        html.html().close();

        html.head().close();

        html.link().rel("stylesheet").type("text/css").href(contextPath + cssLocation).media("print").end();

        html.headEnd();

        html.body().close();

        html.append(snippets.themeStart());

        html.append(snippets.tableStart());

        html.append(snippets.theadStart());

        html.append(snippets.header());

        html.append(snippets.theadEnd());

        html.append(snippets.tbodyStart());
        
        decorateCellEditors();
        
        html.append(snippets.body());

        html.append(snippets.tbodyEnd());

        html.append(snippets.footer());

        html.append(snippets.statusBar());

        html.append(snippets.tableEnd());

        html.append(snippets.themeEnd());

        html.bodyEnd();

        html.htmlEnd();

        return html.toString();
    }

    /**
     * Decorate the cellEditors to escape xml values.
     */
    protected void decorateCellEditors() {
        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();
        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            HtmlCellRenderer cellRenderer = column.getCellRenderer();
            CellEditor cellEditor = cellRenderer.getCellEditor();
            
            // decorate the cell editors
            EscapeXmlCellEditor escapeXmlCellEditor = new EscapeXmlCellEditor();
            escapeXmlCellEditor.setCellEditor(cellEditor);
            cellRenderer.setCellEditor(escapeXmlCellEditor);
        }
    }

    /**
     * Decorate the cell editor with one that can escape values.
     */
    private static class EscapeXmlCellEditor implements CellEditor {

        private CellEditor cellEditor;

        public void setCellEditor(CellEditor cellEditor) {
            this.cellEditor = cellEditor;
        }

        public Object getValue(Object item, String property, int rowcount) {
            Object value = cellEditor.getValue(item, property, rowcount);
            if (value != null) {
                return StringEscapeUtils.escapeXml(value.toString());
            }

            return null;
        }
    }
}
