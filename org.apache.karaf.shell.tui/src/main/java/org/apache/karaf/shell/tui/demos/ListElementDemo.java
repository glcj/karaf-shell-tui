//DEPS dev.tamboui:tamboui-widgets:LATEST
//DEPS dev.tamboui:tamboui-toolkit:LATEST
//DEPS dev.tamboui:tamboui-jline3-backend:LATEST
/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package org.apache.karaf.shell.tui.demos;

import dev.tamboui.layout.Constraint;
import dev.tamboui.style.Color;
import dev.tamboui.terminal.Backend;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.ListElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.*;
import dev.tamboui.tui.TuiConfig;
import static dev.tamboui.tui.TuiConfig.DEFAULT_POLL_TIMEOUT;
import static dev.tamboui.tui.TuiConfig.DEFAULT_RESIZE_GRACE_PERIOD;
import static dev.tamboui.tui.TuiConfig.DEFAULT_TICK_TIMEOUT;
import dev.tamboui.tui.bindings.BindingSets;
import dev.tamboui.tui.error.RenderErrorHandlers;
import java.time.Duration;
import java.util.Collections;

/**
 * Demonstrates ListElement's ability to display rich content.
 * <p>
 * Unlike the low-level ListWidget (which only accepts ListItem with text + style),
 * ListElement accepts any StyledElement as items:
 * <ul>
 *   <li>Styled text with icons</li>
 *   <li>Rows combining multiple elements</li>
 *   <li>Nested panels and complex layouts</li>
 * </ul>
 * <p>
 * ListElement also manages its own internal state for selection and scrolling,
 * so no external ListState is needed.
 */
public class ListElementDemo extends ToolkitApp {
    
    private Backend backend;
    
    private TuiConfig config = null;    

    private final ListElement<?> listElement;

    public ListElementDemo(Backend backend) {
        //
        this.backend = backend;
        this.config = new TuiConfig(
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
        
        // Each .add() creates ONE selectable item - all items are independent (flat list)
        listElement = list()
            .add(text("1. Plain text item"))
            .add(text("2. Styled text").bold().cyan())
            .add(row(text("3. ").length(4), text("■ ").cyan().length(2), text("File: document.txt")))
            .add(row(text("4. ").length(4), text("▶ ").yellow().length(2), text("Folder: projects/")))
            .add(row(text("5. ").length(4), text("✗ ").red().length(2), text("Error: build failed")))
            .add(column(text("6. Multi-line item").bold(), text("   (both lines are ONE item)").dim()).length(2))
            .add(row(text("7. ").length(4), text("[").dim().length(1), text("INFO").cyan().length(5), text("] ").dim().length(2), text("Log entry")))
            .add(row(text("8. ").length(4), text("[").dim().length(1), text("WARN").yellow().length(5), text("] ").dim().length(2), text("Log warning")))
            .add(row(text("9. ").length(4), text("[").dim().length(1), text("ERROR").red().length(5), text("] ").dim().length(2), text("Log error")))
            .add(row(text("10. ").length(4), text("Progress: ").length(10), gauge(0.75).gaugeColor(Color.GREEN).fill(), text(" 75%").length(5)))
            .add(panel(text("11. Nested panel")).rounded().borderColor(Color.DARK_GRAY).length(3))
            .add(text("12. Last item"))

            .highlightColor(Color.CYAN)
            .highlightSymbol("> ")
            .autoScroll()
            .scrollbar()
            .scrollbarThumbColor(Color.CYAN);
    }

    @Override
    protected TuiConfig configure() {      
        return this.config;
    }    
    

    @Override
    protected Element render() {
        return column(
            // Header
            panel(
                text(" Rich List Demo - ListElement with StyledElement items ").bold().cyan()
            ).rounded().borderColor(Color.CYAN).length(3),

            // Main content
            row(
                // The rich list
                panel(listElement)
                    .title("Rich Items (" + 12 + " items)")
                    .rounded()
                    .borderColor(Color.WHITE)
                    .id("list-panel")
                    .focusable()
                    .onKeyEvent(this::handleKey)
                    .constraint(Constraint.percentage(60)),

                // Info panel
                panel(
                    column(
                        text("About This Demo").bold().cyan(),
                        text(""),
                        text("ListElement accepts any"),
                        text("StyledElement as items:"),
                        text(""),
                        text(" - Styled text").green(),
                        text(" - Rows & columns").yellow(),
                        text(" - Nested panels").magenta(),
                        text(" - Gauges & more").cyan(),
                        text(""),
                        text("No external ListState needed!").dim(),
                        text("Selection & scroll managed").dim(),
                        text("internally.").dim()
                    )
                )
                .title("Info")
                .rounded()
                .borderColor(Color.DARK_GRAY)
                .constraint(Constraint.percentage(40))
            ).fill(),

            // Footer
            panel(
                text(" Up/Down: Navigate | q: Quit ").dim()
            ).rounded().borderColor(Color.DARK_GRAY).length(3)
        );
    }

    private EventResult handleKey(KeyEvent event) {
        if (event.isUp()) {
            listElement.selectPrevious();
            return EventResult.HANDLED;
        }
        if (event.isDown()) {
            listElement.selectNext(12);
            return EventResult.HANDLED;
        }
        return EventResult.UNHANDLED;
    }
}
