package com.cabg.inputhandlers;

import com.cabg.moleculehelpers.MoleculeFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static com.cabg.core.EngineMethods.updateMouse;

public class MouseMotionHandler implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {
        updateMouse(e);
        MoleculeFactory.handleDrag(e);
    }

    public void mouseMoved(MouseEvent e) {
        updateMouse(e);
    }
}
