/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.apache.karaf.shell.tui.demos;

import dev.tamboui.terminal.Backend;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.app.ToolkitRunner;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.Panel;
import dev.tamboui.tui.TuiConfig;
import static dev.tamboui.tui.TuiConfig.DEFAULT_POLL_TIMEOUT;
import static dev.tamboui.tui.TuiConfig.DEFAULT_RESIZE_GRACE_PERIOD;
import static dev.tamboui.tui.TuiConfig.DEFAULT_TICK_TIMEOUT;
import dev.tamboui.tui.bindings.BindingSets;
import dev.tamboui.tui.error.RenderErrorHandlers;
import java.time.Duration;
import java.util.Collections;

/**
 *
 * @author cgarcia
 */
public class HelloDsl extends ToolkitApp {
        
 
    private Backend backend;
    
    private TuiConfig config = null;

    public HelloDsl(Backend backend) {
        this.backend = backend;
        config = new TuiConfig(
                true,                        // rawMode
                true,                        // alternateScreen
                false,                        // hideCursor
                false,                       // mouseCapture
                false,                       // bracketedPaste
                Duration.ofMillis(DEFAULT_POLL_TIMEOUT),      // pollTimeout
                Duration.ofMillis(DEFAULT_TICK_TIMEOUT),      // tickRate
                Duration.ofMillis(DEFAULT_RESIZE_GRACE_PERIOD),  // resizeGracePeriod
                false,                        // shutdownHook
                BindingSets.defaults(),      // bindings
                RenderErrorHandlers.displayAndQuit(),  // errorHandler
                System.err,                  // errorOutput
                false,                       // fpsOverlayEnabled
                Collections.emptyList(),     // postRenderProcessors
                backend,                          // backend (allows for lazy backend creation)
                null                         // scheduler
            );           
    }
    
    public void setBackend(Backend backend) {
        this.backend = backend;
        
        config = new TuiConfig(
                true,                        // rawMode
                true,                        // alternateScreen
                false,                        // hideCursor
                false,                       // mouseCapture
                false,                       // bracketedPaste
                Duration.ofMillis(DEFAULT_POLL_TIMEOUT),      // pollTimeout
                Duration.ofMillis(DEFAULT_TICK_TIMEOUT),      // tickRate
                Duration.ofMillis(DEFAULT_RESIZE_GRACE_PERIOD),  // resizeGracePeriod
                false,                        // shutdownHook
                BindingSets.defaults(),      // bindings
                RenderErrorHandlers.displayAndQuit(),  // errorHandler
                System.err,                  // errorOutput
                false,                       // fpsOverlayEnabled
                Collections.emptyList(),     // postRenderProcessors
                backend,                          // backend (allows for lazy backend creation)
                null                         // scheduler
            );   
        
        System.out.println("Config: " + config.toString());           
    }

    @Override
    protected TuiConfig configure() {      
        return config;
    }

    @Override
    protected void onStart() {
        //
    }

    @Override
    protected void onStop() {
        //
    }
        
    @Override
    protected Element render() {
        return panel("Hello",
            text("Welcome to TamboUI DSL!").bold().cyan(),
            spacer(),
            text("Press 'q' to quit").dim()
        ).rounded();
    }

    public void runApp(Backend backend) throws Exception {
        new HelloDsl(backend).run();
    }  
    
}
