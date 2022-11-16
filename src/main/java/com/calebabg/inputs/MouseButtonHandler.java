package com.calebabg.inputs;

import com.calebabg.molecules.MoleculeFactory;
import com.calebabg.physics.Physics;
import com.calebabg.physics.PhysicsFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.calebabg.core.EngineVariables.engineSettings;

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
        Physics.resetSelectedVertex();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (engineSettings.engineMode) {
                case NORMAL: MoleculeFactory.handleLeftClickNormal(e); break;
                case MULTI: MoleculeFactory.handleLeftClickMulti(e); break;
                case FIREWORKS: MoleculeFactory.handleLeftClickFireworksTarget(e); break;
                case PHYSICS: PhysicsFactory.handleLeftClick(e); break;
            }
        }
    }
}
