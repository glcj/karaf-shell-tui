
package org.apache.karaf.shell.tui;

import dev.tamboui.terminal.Backend;
import dev.tamboui.terminal.BackendProvider;
import dev.tamboui.toolkit.app.ToolkitApp;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.api.console.Terminal;
import org.apache.karaf.shell.tamboui.JLineBackendProvider;
import org.apache.karaf.shell.tui.demos.HelloDsl;
import org.apache.karaf.shell.tui.demos.ListElementDemo;
import org.apache.karaf.shell.tui.demos.TableDemo;
import org.apache.karaf.shell.tui.demos.ChartDemo;

@Command(scope = "tui", name = "demo", description = "Tamboui tui integrator")
@Service
public class CommandDemo implements Action {

    @Reference
    Session session;
    
    @Reference
    BackendProvider provider;
    
    @Option(name = "-d", aliases = { "--demo" }, description = "Demo selection", required = false, multiValued = false)
    private int option = 0;

    @Override
    public Object execute() throws Exception {
        Terminal terminal = session.getTerminal();
        
        ((JLineBackendProvider) provider).setTerminal((org.jline.terminal.Terminal) terminal);
        Backend backend = provider.create();
        
        ToolkitApp app = null;

        switch (option) {
            case 1 : app = new HelloDsl(backend);
                break;
            case 2 : app = new ListElementDemo(backend);
                break;
            case 3 : app = new TableDemo(backend);
                break;
            case 4 : app = new ChartDemo(backend);
                break;
        }
        if (null != app)    
            app.run();        
        
         return null;
    }
}
