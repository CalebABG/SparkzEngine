package com.cabg.inputhandlers;

import com.cabg.core.EngineMethods;
import com.cabg.verlet.Physics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.cabg.core.EngineVariables.engineSettings;

/*
 * Button 1: Left Click
 * Button 2: Middle Click
 * Button 3: Right Click
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
        Physics.resetDragVertex();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            EngineMethods.handleLeftClick(e);
    }
}
