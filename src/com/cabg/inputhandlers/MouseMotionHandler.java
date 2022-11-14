package com.cabg.inputhandlers;

import com.cabg.moleculetypes.MoleculeFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static com.cabg.core.EngineVariables.MouseVec;

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
