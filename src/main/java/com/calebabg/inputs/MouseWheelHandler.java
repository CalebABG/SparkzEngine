package com.calebabg.inputs;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static com.calebabg.core.EngineVariables.engineSettings;

public class MouseWheelHandler implements MouseWheelListener {
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            engineSettings.particleDragAmount += e.getWheelRotation();
            if (engineSettings.particleDragAmount < 2) engineSettings.particleDragAmount = 1;
        }
    }
}
