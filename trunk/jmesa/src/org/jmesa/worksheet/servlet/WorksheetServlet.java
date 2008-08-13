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
package org.jmesa.worksheet.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.WorksheetUpdater;
import org.jmesa.worksheet.WorksheetUpdaterImpl;

/**
 * The default invoker of the worksheet updater.
 *
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebContext webContext = new HttpServletRequestWebContext(request);
        WorksheetUpdater worksheetUpdater = new WorksheetUpdaterImpl();
        worksheetUpdater.update(webContext);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
