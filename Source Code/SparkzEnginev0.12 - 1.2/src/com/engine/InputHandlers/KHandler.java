package com.engine.InputHandlers;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.*;
import com.engine.J8Helpers.Extensions.UIThread;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.Settings;
import com.engine.Verlet.VSim;
import com.sun.management.OperatingSystemMXBean;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.management.ManagementFactory;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EModes.ENGINE_MODES.RAGDOLL_MODE;
import static com.engine.EngineHelpers.EngineMethods.toggleParticleBoundsChecking;
import static com.engine.EngineHelpers.EBOOLS.*;

public class KHandler extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Keyboard button: SPACE-BAR
        if (key == KeyEvent.VK_SPACE) {
            ParticleModes.singleGravityPoint(random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight());
        }

        //Keyboard button: UP
        if (key == KeyEvent.VK_UP) {
            EngineMethods.upArrowFunction();
        }

        //Keyboard button: DOWN
        if (key == KeyEvent.VK_DOWN) {
            EngineMethods.downArrowFunction();
        }

        //Keyboard button: 1
        if (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1) {
            EngineMethods.increaseParticleSize();
        }

        //Keyboard button: 2
        if (key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2) {
            EngineMethods.decreaseParticleSize();
        }

        //Keyboard button: 3
        if (key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3) {
            EngineMethods.trimParticleArrays();
        }

        //Keyboard button: CTRL
        if (key == KeyEvent.VK_CONTROL) CONTROL_IS_DOWN.setValue(true);

        //Keyboard button: F
        if (key == KeyEvent.VK_F) {
            EngineMethods.slowParticles();
        }

        //Keyboard button: G
        if (key == KeyEvent.VK_G) {
            EngineMethods.scatterParticles();
        }

        //Force Quit
        if (e.isShiftDown() && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q) {System.exit(0);}
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        //Keyboard button: 0
        if (key == KeyEvent.VK_0 || key == KeyEvent.VK_NUMPAD0) {
            EngineMethods.mouseGravitationToggle();
        }

        //Keyboard button: 4
        if (key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4) {
            EngineMethods.updateEngineMode();
        }

        //Keyboard button: 5
        if (key == KeyEvent.VK_5 || key == KeyEvent.VK_NUMPAD5) {
            EngineMethods.thinkingParticlesMode();
        }

        //Keyboard button: 6
        if (key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6) {
            EngineMethods.connectParticlesMode();
        }

        //Keyboard button: 7
        if (key == KeyEvent.VK_7 || key == KeyEvent.VK_NUMPAD7) {
            UIThread.openUI(OptionsMenu::getInstance);
        }

        //Keyboard button: 8
        if (key == KeyEvent.VK_8 || key == KeyEvent.VK_NUMPAD8) {
            EngineMethods.pauseSimulation();
        }

        //Keyboard button: 9
        if (key == KeyEvent.VK_9 || key == KeyEvent.VK_NUMPAD9) {
            UIThread.openUI(SlideEditor::getInstance);
        }

        //Keyboard button: CTRL
        if (key == KeyEvent.VK_CONTROL) CONTROL_IS_DOWN.setValue(false);

        if (key == KeyEvent.VK_A) {
            if (ENGINE_MODE == RAGDOLL_MODE && !Notifier.drawingNotification) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.ADD;
                Notifier.pushNotification(VPhysicsEditor.ADD);
            }
        }

        if (key == KeyEvent.VK_D) {
            if (ENGINE_MODE == RAGDOLL_MODE && !Notifier.drawingNotification) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.DRAG;
                Notifier.pushNotification(VPhysicsEditor.DRAG);
            }
        }

        //Keyboard button: S
        if (key == KeyEvent.VK_S) {
            if (ENGINE_MODE == RAGDOLL_MODE && !Notifier.drawingNotification) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.SELECT;
                Notifier.pushNotification(VPhysicsEditor.SELECT);
            }
        }

        //Keyboard button: W
        if (key == KeyEvent.VK_W) {
            UIThread.openUI(ColorEditor::getInstance);
        }

        //Keyboard button: K
        if (key == KeyEvent.VK_K) {
            VSim.toggleCollisions();
        }

        //Keyboard button: M
        if (key == KeyEvent.VK_M) {
            VSim.toggleGravity();
        }

        //Keyboard button: N
        if (key == KeyEvent.VK_N) {
            VSim.toggleDebug();
        }

        if (key == KeyEvent.VK_P) {
            VSim.pinSelectedPoint();
        }

        //Keyboard button: C
        if (key == KeyEvent.VK_C){
            EngineMethods.clearParticleArrays();
        }

        //Keyboard button: Left Arrow
        if (key == KeyEvent.VK_LEFT) {
            EngineMethods.leftArrowFunction();
        }

        //Keyboard button: Right Arrow
        if (key == KeyEvent.VK_RIGHT) {
            EngineMethods.rightArrowFunction();
        }

        //Keyboard button: Q
        if (key == KeyEvent.VK_Q) {
            EngineMethods.createEngineInstructionsWindow(EFrame);
        }

        //Keyboard button: R
        if (key == KeyEvent.VK_R) {
            ColorUtility.setEngineBackgroundColor();
        }

        //Keyboard button: T
        if (key == KeyEvent.VK_T) {
            EngineMethods.toggleParticleFriction();
        }

        //Toggles draw modes for GravityPoint
        if (key == KeyEvent.VK_V) {
            TOGGLE_GRAVITYPOINTS_LINK_RENDER.toggleValue();
        }

        if (key == KeyEvent.VK_Y) toggleParticleBoundsChecking();

        //Toggles draw modes for Particles
        if (key == KeyEvent.VK_B) {
            TOGGLE_PARTICLE_LINK_RENDER.toggleValue();
        }

        //Load in saved or default settings
        if (e.isShiftDown() && (key == KeyEvent.VK_Z)) {
            Settings.loadSettings();
        }

        //Keyboard button: Escape
        if (key == KeyEvent.VK_ESCAPE) UIThread.openUI(QuitWindow::getInstance);

        //Opens StatsPanel: precaution since the menu-bar is now on this panel
        //If the panel is closed, can always get the panel back
        if (key == KeyEvent.VK_I && e.isControlDown()) UIThread.openUI(StatsPanel::getInstance);

        //Open Exception Logger
        if (e.isShiftDown() && e.isControlDown() && (key == KeyEvent.VK_V)) UIThread.openUI(EException::getInstance);

        //Open Settings Editor
        if (e.isShiftDown() && (key == KeyEvent.VK_S)) UIThread.openUI(SettingsEditor::getInstance);

        //Open Particle Graph Editor
        if (e.isShiftDown() && (key == KeyEvent.VK_X)) UIThread.openUI(ParticleGraph::getInstance);
    }
}