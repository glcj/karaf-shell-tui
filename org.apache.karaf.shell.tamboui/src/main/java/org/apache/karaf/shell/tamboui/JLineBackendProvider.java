/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package org.apache.karaf.shell.tamboui;

import java.io.IOException;

import dev.tamboui.terminal.Backend;
import dev.tamboui.terminal.BackendProvider;
import org.jline.terminal.Terminal;

/**
 * {@link BackendProvider} implementation for JLine 3.
 * <p>
 * This provider is registered via the Java {@link java.util.ServiceLoader} mechanism.
 */
public class JLineBackendProvider implements BackendProvider {

    Terminal terminal;
    
    /**
     * Creates a new JLine 3 backend provider.
     */
    public JLineBackendProvider() {
    }

    @Override
    public String name() {
        return "jline3";
    }

    @Override
    public Backend create() throws IOException {      
        return new JLineBackend(terminal);
    }
    
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}
