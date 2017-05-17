package com.engine.InputHandlers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.J8Helpers.Interfaces.EModes.RAGDOLL_MODE;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.MGrapher.ParticleGraph;
import com.engine.GUIWindows.*;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.Settings;
import com.engine.Verlet.VSim;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KHandler extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Keyboard button: SPACE-BAR
        if (key == KeyEvent.VK_SPACE) {
            ParticleModes.singleGravityPoint((int) (Math.random() * canvas.getWidth()), (int) (Math.random() * canvas.getHeight()));
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
        if (key == KeyEvent.VK_1) {
            EngineMethods.increaseParticleSize();
        }

        //Keyboard button: 2
        if (key == KeyEvent.VK_2) {
            EngineMethods.decreaseParticleSize();
        }

        //Keyboard button: 3
        if (key == KeyEvent.VK_3) {
            EngineMethods.trimParticleArrays();
        }

        //Keyboard button: CTRL
        if (key == KeyEvent.VK_CONTROL) isCTRLDown = true;

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
        if (key == KeyEvent.VK_0) {
            EngineMethods.mouseGravitationToggle();
        }

        if (key == KeyEvent.VK_A) {
            if (engineMode == RAGDOLL_MODE) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.ADD;
                Notifier.getInstance(VPhysicsEditor.ADD,       150, 80);
            }
        }

        if (key == KeyEvent.VK_D) {
            if (engineMode == RAGDOLL_MODE) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.DRAG;
                Notifier.getInstance(VPhysicsEditor.DRAG,       150, 80);
            }
            //VSim.handlePhysicsDeselect();
        }

        //Keyboard button: S
        if (key == KeyEvent.VK_S) {
            if (engineMode == RAGDOLL_MODE) {
                VPhysicsEditor.EDITOR_MODE = VPhysicsEditor.SELECT;
                Notifier.getInstance(VPhysicsEditor.SELECT,       185, 80);
            }
            //EngineMethods.toggleGraphicsSmoothing();
        }

        //Keyboard button: W
        if (key == KeyEvent.VK_W) {
            ColorEditor.getInstance();
        }

        //Keyboard button: 4
        if (key == KeyEvent.VK_4) {
            EngineMethods.updateEngineMode();
        }

        //Keyboard button: 5
        if (key == KeyEvent.VK_5) {
            EngineMethods.thinkingParticlesMode();
        }

        //Keyboard button: 6
        if (key == KeyEvent.VK_6) {
            EngineMethods.connectParticlesMode();
        }

        //Keyboard button: 7
        if (key == KeyEvent.VK_7) {
            OptionsMenu.getInstance();
        }

        //Keyboard button: 8
        if (key == KeyEvent.VK_8) {
            EngineMethods.pauseSimulation();
        }

        //Keyboard button: CTRL
        if (key == KeyEvent.VK_CONTROL) isCTRLDown = false;

        //Keyboard button: 9
        if (key == KeyEvent.VK_9) {
            SlideEditor.getInstance();
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
            ColorUtility.giveBackgroundColor();
        }

        //Keyboard button: T
        if (key == KeyEvent.VK_T) {
            EngineMethods.toggleParticleFriction();
        }

        //Toggles draw modes for GravityPoint
        if (key == KeyEvent.VK_V) {
            GDMODEBOOL = EngineMethods.toggle(GDMODEBOOL);
        }

        //Toggles draw modes for Particles
        if (key == KeyEvent.VK_B) {
            PTMODEBOOL = EngineMethods.toggle(PTMODEBOOL);
        }

        //Load in saved or default settings
        if (e.isShiftDown() && (key == KeyEvent.VK_Z)) {
            Settings.loadSettings();
        }

        //Keyboard button: Escape
        if (key == KeyEvent.VK_ESCAPE) QuitWindow.getInstance();

        //Opens StatsPanel: precaution since the menubar is now on this panel
        //If the panel is closed, can always get the panel back
        if (key == KeyEvent.VK_I && e.isControlDown()) StatsPanel.getInstance();

        //Open Exception Logger
        if (e.isShiftDown() && e.isControlDown() && (key == KeyEvent.VK_V)) EException.getInstance();

        //Open Settings Editor
        if (e.isShiftDown() && (key == KeyEvent.VK_S)) SettingsEditor.getInstance();

        //Open Particle Graph Editor
        if (e.isShiftDown() && (key == KeyEvent.VK_X)) ParticleGraph.getInstance();
    }
}