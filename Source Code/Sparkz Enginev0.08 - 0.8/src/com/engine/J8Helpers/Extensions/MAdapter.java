package com.engine.J8Helpers.Extensions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class MAdapter extends MouseAdapter {
    public Consumer<MouseEvent> release_function, pressed_function;

    public MAdapter(Consumer<MouseEvent> pressed_function, Consumer<MouseEvent> release_function) {
        this.pressed_function = pressed_function;
        this.release_function = release_function;
    }

    public void mousePressed(MouseEvent e) {pressed_function.accept(e);}
    public void mouseReleased(MouseEvent e) {release_function.accept(e);}
}
