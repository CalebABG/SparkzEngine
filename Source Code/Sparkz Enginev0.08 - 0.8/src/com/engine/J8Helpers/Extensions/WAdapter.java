package com.engine.J8Helpers.Extensions;

import com.engine.J8Helpers.Interfaces.WindowAdapterX;

import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class WAdapter implements WindowAdapterX {
    public static class WindowOpened extends WAdapter{
        public Consumer<WindowEvent> window_opened;
        public WindowOpened(Consumer<WindowEvent> window_opened){this.window_opened = window_opened;}
        public void windowOpened(WindowEvent e) {window_opened.accept(e);}
    }
    public static class WindowClosing extends WAdapter {
        public Consumer<WindowEvent> window_closing;
        public WindowClosing(Consumer<WindowEvent> window_closing){this.window_closing = window_closing;}
        public void windowClosing(WindowEvent e) {window_closing.accept(e);}
    }
    public static class WindowClosed extends WAdapter {
        public Consumer<WindowEvent> window_closed;
        public WindowClosed(Consumer<WindowEvent> window_closed){this.window_closed = window_closed;}
        public void windowClosed(WindowEvent e) {window_closed.accept(e);}
    }
}
