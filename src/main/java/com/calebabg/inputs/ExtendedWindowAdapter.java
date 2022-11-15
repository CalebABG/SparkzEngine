package com.calebabg.inputs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class ExtendedWindowAdapter extends WindowAdapter {
    private final Consumer<WindowEvent> closingFunction;

    public ExtendedWindowAdapter(Consumer<WindowEvent> closingFunction) {
        this.closingFunction = closingFunction;
    }

    public void windowClosing(WindowEvent e) {
        closingFunction.accept(e);
    }
}
