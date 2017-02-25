package com.engine.Interfaces_Extensions;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public interface WindowAdapterX extends WindowListener {
    default void windowOpened(WindowEvent e) {}
    default void windowClosing(WindowEvent e) {}
    default void windowClosed(WindowEvent e) {}
    default void windowIconified(WindowEvent e) {}
    default void windowDeiconified(WindowEvent e) {}
    default void windowActivated(WindowEvent e) {}
    default void windowDeactivated(WindowEvent e) {}
}

