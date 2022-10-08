package com.cabg.inputhandlers;

import com.cabg.core.EngineMethods;
import com.cabg.verlet.VSim;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.cabg.core.EngineVariables.engineSettings;

/*
 * Button Click Hints
 * Left click: (button 1)
 * Mid click: (button 2)
 * Right click: (button 3)
 */

public class MouseButtonHandler extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: engineSettings.leftMouseButtonIsDown = true; break;
            case MouseEvent.BUTTON3: engineSettings.rightMouseButtonIsDown = true; break;
        }
    }

    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: engineSettings.leftMouseButtonIsDown = false; break;
            case MouseEvent.BUTTON3: engineSettings.rightMouseButtonIsDown = false; break;
        }
        VSim.resetDragVertex();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            EngineMethods.switchClickMode(e);
        }
    }
}
