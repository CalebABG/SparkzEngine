package com.engine.InputHandlers;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MWheelListener implements MouseWheelListener{
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            dragAmount += e.getWheelRotation(); if (dragAmount < 1) {dragAmount = 1;}
        }
    }
}
