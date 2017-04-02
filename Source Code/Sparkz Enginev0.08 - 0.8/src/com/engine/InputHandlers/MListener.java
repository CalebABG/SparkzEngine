package com.engine.InputHandlers;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Verlet.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){isLeftMouseDown = true;}
        if (SwingUtilities.isRightMouseButton(e)){isRightMouseDown = true;}
    }
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){isLeftMouseDown = false;}
        if (SwingUtilities.isRightMouseButton(e)){isRightMouseDown = false;}
        VSim.dragPoint = null;
    }
    public void mouseClicked(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e)) {EngineMethods.switchClickMode(e);}
    }
}
