package com.engine.InputHandlers;

import com.engine.ParticleHelpers.ParticleModes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static com.engine.EngineHelpers.EngineMethods.updateMouse;

public class MMotionListener implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {ParticleModes.dragMode(e); updateMouse(e);}
    public void mouseMoved(MouseEvent e) {updateMouse(e);}
}
