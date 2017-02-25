package com.engine.Interfaces_Extensions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class KAdapter extends KeyAdapter {
    public Consumer<KeyEvent> release_function, pressed_function;

    public KAdapter(Consumer<KeyEvent> pressed_function, Consumer<KeyEvent> release_function) {
        this.pressed_function = pressed_function;
        this.release_function = release_function;
    }

    public void keyReleased(KeyEvent e) {release_function.accept(e);}
    public void keyPressed(KeyEvent e) {pressed_function.accept(e);}
}
