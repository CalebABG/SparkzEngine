package com.calebabg.inputs;

import com.calebabg.elements.MoleculeFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static com.calebabg.core.EngineVariables.MouseVec;

public class MouseMotionHandler implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {
        updateMouseVec(e);
        MoleculeFactory.handleDrag(e);
    }

    public void mouseMoved(MouseEvent e) {
        updateMouseVec(e);
    }

    public static void updateMouseVec(MouseEvent e) {
        MouseVec.x = e.getX();
        MouseVec.y = e.getY();
    }
}
