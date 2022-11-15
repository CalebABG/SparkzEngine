package com.calebabg.inputs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class ExtendedKeyAdapter extends KeyAdapter {
    private final Consumer<KeyEvent> releaseFunction, pressedFunction;

    public ExtendedKeyAdapter(Consumer<KeyEvent> pressedFunction, Consumer<KeyEvent> releaseFunction) {
        this.pressedFunction = pressedFunction;
        this.releaseFunction = releaseFunction;
    }

    public void keyReleased(KeyEvent e) {
        releaseFunction.accept(e);
    }

    public void keyPressed(KeyEvent e) {
        pressedFunction.accept(e);
    }

    public static class KeyPressed implements KeyListener {
        private final Consumer<KeyEvent> pressed;

        public KeyPressed(Consumer<KeyEvent> pressed) {
            this.pressed = pressed;
        }
        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            pressed.accept(e);
        }

        public void keyReleased(KeyEvent e) {}
    }

    public static class KeyReleased implements KeyListener {
        private final Consumer<KeyEvent> released;

        public KeyReleased(Consumer<KeyEvent> released) {
            this.released = released;
        }

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {
            released.accept(e);
        }
    }
}
