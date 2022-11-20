package com.calebabg.inputs;

import com.calebabg.core.EngineVariables;
import com.calebabg.molecules.MoleculeFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {
        updateMouseVec(e);
        MoleculeFactory.handleDrag(e);
    }

    public void mouseMoved(MouseEvent e) {
        updateMouseVec(e);
    }

    public static void updateMouseVec(MouseEvent e) {
        EngineVariables.MouseVec.x = e.getX();
        EngineVariables.MouseVec.y = e.getY();
    }
}
