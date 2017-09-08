package com.engine.InputHandlers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import static com.engine.EngineHelpers.EINTS.PARTICLE_DRAG_AMOUNT;

public class MWheelListener implements MouseWheelListener {
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            PARTICLE_DRAG_AMOUNT.increment(e.getWheelRotation());
            if (PARTICLE_DRAG_AMOUNT.value() <= 1) PARTICLE_DRAG_AMOUNT.setValue(1);
        }
    }
}
