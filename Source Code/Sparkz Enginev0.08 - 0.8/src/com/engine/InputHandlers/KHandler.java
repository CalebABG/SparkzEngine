package com.engine.InputHandlers;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.EngineHelpers.EngineMethods;
import com.engine.MGrapher.ParticleGraph;
import com.engine.GUIWindows.*;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.Utilities.ColorConverter;
import com.engine.Utilities.Settings;
import com.engine.Verlet.VSim;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KHandler extends KeyAdapter{
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();

        //Keyboard button: SPACE-BAR
        if (pressed == KeyEvent.VK_SPACE) {
            ParticleModes.singleGravityPoint((int) (Math.random() * canvas.getWidth()), (int) (Math.random() * canvas.getHeight()));
        }

        //Keyboard button: UP
        if (pressed == KeyEvent.VK_UP) {
            EngineMethods.upArrowFunction();
        }

        //Keyboard button: DOWN
        if (pressed == KeyEvent.VK_DOWN) {
            EngineMethods.downArrowFunction();
        }

        //Keyboard button: 1
        if (pressed == KeyEvent.VK_1) {
            EngineMethods.increaseParticleSize();
        }

        //Keyboard button: 2
        if (pressed == KeyEvent.VK_2) {
            EngineMethods.decreaseParticleSize();
        }

        //Keyboard button: 3
        if (pressed == KeyEvent.VK_3) {
            EngineMethods.trimParticleArrays();
        }

        //Keyboard button: CTRL
        if (pressed == KeyEvent.VK_CONTROL) {
            isCTRLDown = true;
        }

        //Keyboard button: F
        if (pressed == KeyEvent.VK_F) {
            EngineMethods.slowParticles();
        }

        //Keyboard button: G
        if (pressed == KeyEvent.VK_G) {
            EngineMethods.scatterParticles();
        }

        //Force Quit
        if (e.isShiftDown() && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q) {System.exit(0);}
    }

    public void keyReleased(KeyEvent e) {
        int pressed = e.getKeyCode();
        //System.out.println(pressed);

        //Keyboard button: 0
        if (pressed == KeyEvent.VK_0) {
            EngineMethods.mouseGravitationToggle();
        }

        //Keyboard button: W
        if (pressed == KeyEvent.VK_W) {
            try {
                ColorEditor.getInstance();}catch (Exception ex){EException.append(ex);}
        }

        //Keyboard button: 4
        if (pressed == KeyEvent.VK_4) {
            EngineMethods.updateEngineMode();
        }

        //Keyboard button: 5
        if (pressed == KeyEvent.VK_5) {
            EngineMethods.thinkingParticlesMode();
        }

        //Keyboard button: 6
        if (pressed == KeyEvent.VK_6) {
            EngineMethods.connectParticlesMode();
        }

        //Keyboard button: 7
        if (pressed == KeyEvent.VK_7) {
            try {OptionsMenu.getInstance();}catch (Exception ex){EException.append(ex);}
        }

        //Keyboard button: 8
        if (pressed == KeyEvent.VK_8) {
            EngineMethods.pauseSimulation();
        }

        //Keyboard button: CTRL
        if (pressed == KeyEvent.VK_CONTROL) {
            isCTRLDown = false;
        }

        //Keyboard button: 9
        if (pressed == KeyEvent.VK_9) {
            try {SlideEditor.getInstance();} catch (Exception ex){EException.append(ex);}
        }

        //Keyboard button: K
        if (pressed == KeyEvent.VK_K) {
            VSim.toggleCollisions();
        }

        //Keyboard button: M
        if (pressed == KeyEvent.VK_M) {
            VSim.toggleGravity();
        }

        //Keyboard button: N
        if (pressed == KeyEvent.VK_N) {
            VSim.toggleDebug();
        }

        //Keyboard button: C
        if (pressed == KeyEvent.VK_C){
            try {EngineMethods.clearParticleArrays();} catch (Exception x) {EException.append(x);}
        }

        //Keyboard button: Left Arrow
        if (pressed == KeyEvent.VK_LEFT) {
            EngineMethods.leftArrowFunction();
        }

        //Keyboard button: Right Arrow
        if (pressed == KeyEvent.VK_RIGHT) {
            EngineMethods.rightArrowFunction();
        }

        //Keyboard button: Q
        if (pressed == KeyEvent.VK_Q) {
            try {EngineMethods.createEngineInstructionsWindow(EFrame);} catch (Exception ex){EException.append(ex);}
        }

        //Keyboard button: R
        if (pressed == KeyEvent.VK_R) {
            ColorConverter.giveBackgroundColor();
        }

        //Keyboard button: S
        if (pressed == KeyEvent.VK_S) {
            EngineMethods.toggleGraphicsSmoothing();
        }

        //Keyboard button: T
        if (pressed == KeyEvent.VK_T) {
            EngineMethods.toggleParticleFriction();
        }

        //Toggles draw modes for GravityPoint
        if (pressed == KeyEvent.VK_V) {
            GDMODEBOOL = EngineMethods.toggle(GDMODEBOOL);
        }

        //Toggles draw modes for Particles
        if (pressed == KeyEvent.VK_B) {
            PTMODEBOOL = EngineMethods.toggle(PTMODEBOOL);
        }

        //Load in saved or default settings
        if (e.isShiftDown() && (pressed == KeyEvent.VK_Z)) {
            Settings.loadSettings();
        }

        //Keyboard button: Escape
        if (pressed == KeyEvent.VK_ESCAPE) {QuitWindow.getInstance();}

        //Opens StatsPanel: precaution since the menubar is now on this panel
        //If the panel is closed, can always get the panel back
        if (pressed == KeyEvent.VK_I && e.isControlDown()) StatsPanel.getInstance();

        //Open Exception Logger
        if (e.isShiftDown() && e.isControlDown() && (pressed == KeyEvent.VK_V)) {EException.getInstance();}

        //Open Settings Editor
        if (e.isShiftDown() && (pressed == KeyEvent.VK_S)) {
            try{SettingsEditor.getInstance();} catch (Exception ex){EException.append(ex);}
        }

        //Open Particle Graph Editor
        if (e.isShiftDown() && (pressed == KeyEvent.VK_X)) {
            try {ParticleGraph.getInstance();} catch (Exception ez) {EException.append(ez);}
        }
    }
}