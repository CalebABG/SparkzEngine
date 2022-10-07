package com.engine.InputHandlers;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.Verlet.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EBOOLS.LEFT_MOUSE_IS_DOWN;
import static com.engine.EngineHelpers.EBOOLS.RIGHT_MOUSE_IS_DOWN;

/*
 * Button Click Hints
 * Left click: (button 1)
 * Mid click: (button 2)
 * Right click: (button 3)
 */

public class MouseButtonHandler extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: LEFT_MOUSE_IS_DOWN.setValue(true); break;
            case MouseEvent.BUTTON3: RIGHT_MOUSE_IS_DOWN.setValue(true); break;
        }
    }

    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: LEFT_MOUSE_IS_DOWN.setValue(false); break;
            case MouseEvent.BUTTON3: RIGHT_MOUSE_IS_DOWN.setValue(false); break;
        }
        VSim.resetDragVertex();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            EngineMethods.switchClickMode(e);
        }
    }
}
