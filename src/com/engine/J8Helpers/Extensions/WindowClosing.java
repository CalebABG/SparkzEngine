package com.engine.J8Helpers.Extensions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class WindowClosing extends WindowAdapter {
    public Consumer<WindowEvent> closing_function;

    public WindowClosing(Consumer<WindowEvent> closing_function) {
        this.closing_function = closing_function;
    }

    public void windowClosing(WindowEvent e) {
        closing_function.accept(e);
    }
}
