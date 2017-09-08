package com.engine.J8Helpers.Interfaces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface MouseAdapterX extends MouseListener, MouseMotionListener {
    default void mouseClicked(MouseEvent e) {}
    default void mousePressed(MouseEvent e) {}
    default void mouseReleased(MouseEvent e) {}
    default void mouseEntered(MouseEvent e) {}
    default void mouseExited(MouseEvent e) {}
    default void mouseMoved(MouseEvent e) {}
    default void mouseDragged(MouseEvent e) {}
}

