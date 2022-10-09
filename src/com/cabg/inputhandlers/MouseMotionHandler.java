package com.cabg.inputhandlers;

import com.cabg.particlehelpers.ParticleModes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static com.cabg.core.EngineMethods.updateMouse;

public class MouseMotionHandler implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {
        updateMouse(e);
        ParticleModes.dragMode(e);
    }

    public void mouseMoved(MouseEvent e) {
        updateMouse(e);
    }
}
