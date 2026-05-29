
package org.apache.karaf.shell.tui;

import dev.tamboui.terminal.BackendProvider;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.api.console.Terminal;
import org.apache.karaf.shell.tamboui.JLineBackendProvider;
import org.apache.karaf.shell.tui.demos.TableDemo;

@Command(scope = "tui", name = "demo", description = "Tamboui tui integrator")
@Service
public class CommandDemo implements Action {

    @Reference
    Session session;
    
    @Reference
    BackendProvider provider;
    
    @Option(name = "-d", aliases = { "--demo" }, description = "Demo selection", required = false, multiValued = false)
    private String option;

    @Override
    public Object execute() throws Exception {
        Terminal terminal = session.getTerminal();
        ((JLineBackendProvider) provider).setTerminal((org.jline.terminal.Terminal) terminal);
        var app = new TableDemo(provider.create());
        app.run();        
         return null;
    }
}
