package com.engine.InputHandlers;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Verlet.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
* Button Click Hints
* Left click: (button 1)
* Mid click: (button 2)
* Right click: (button 3)
*/

public class MListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) isLeftMouseDown = true;
        else if (e.getButton() == MouseEvent.BUTTON3) isRightMouseDown = true;
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)isLeftMouseDown = false;
        else if (e.getButton() == MouseEvent.BUTTON3) isRightMouseDown = false;
        VSim.dragPoint = null;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) EngineMethods.switchClickMode(e);
    }
}
