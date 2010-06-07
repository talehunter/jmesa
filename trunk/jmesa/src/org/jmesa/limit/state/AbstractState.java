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
package org.jmesa.limit.state;

import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * @since 2.5.1
 * @author Jeff Johnston
 */
public abstract class AbstractState implements State, WebContextSupport {
    private final String id;
    private final String stateAttr;
    private WebContext webContext;

    public AbstractState(String id, String stateAttr) {
        this.id = id;
        this.stateAttr = stateAttr;
    }

    protected String getId() {
        return id;
    }

    protected String getStateAttr() {
        return stateAttr;
    }

    public WebContext getWebContext() {
        return webContext;
    }

    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }
}
