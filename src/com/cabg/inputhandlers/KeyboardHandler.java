package com.cabg.inputhandlers;

import com.cabg.core.BackgroundThread;
import com.cabg.core.EngineMethods;
import com.cabg.core.EngineSettings;
import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.*;
import com.cabg.moleculehelpers.MoleculeFactory;
import com.cabg.utilities.ColorUtil;
import com.cabg.verlet.Physics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.EngineMode.RAGDOLL;

public class KeyboardHandler extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1) {
            EngineMethods.increaseParticleSize();
        }

        if (key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2) {
            EngineMethods.decreaseParticleSize();
        }

        if (key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3) {
            EngineMethods.trimMoleculeLists();
        }

        if (key == KeyEvent.VK_SPACE) {
            MoleculeFactory.singleGravityPoint(
                    random.nextFloat() * canvas.getWidth(),
                    random.nextFloat() * canvas.getHeight());
        }

        if (key == KeyEvent.VK_UP) {
            EngineMethods.upArrowFunction();
        }

        if (key == KeyEvent.VK_DOWN) {
            EngineMethods.downArrowFunction();
        }

        if (key == KeyEvent.VK_CONTROL) {
            engineSettings.controlKeyIsDown = true;
        }

        if (key == KeyEvent.VK_F) {
            EngineMethods.slowParticles();
        }

        if (key == KeyEvent.VK_G) {
            EngineMethods.scatterParticles();
        }

        if (key == KeyEvent.VK_Q && e.isShiftDown() && e.isControlDown()) {
            // Force quit
            System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_0 || key == KeyEvent.VK_NUMPAD0) {
            engineSettings.toggleMouseGravitation();
        }

        if (key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4) {
            EngineMethods.updateEngineMode();
        }

        if (key == KeyEvent.VK_5 || key == KeyEvent.VK_NUMPAD5) {
            engineSettings.toggleReactiveColors();
        }

        if (key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6) {
            engineSettings.toggleConnectedParticlesMode();
        }

        if (key == KeyEvent.VK_7 || key == KeyEvent.VK_NUMPAD7) {
            BackgroundThread.run(OptionsMenu::getInstance);
        }

        if (key == KeyEvent.VK_8 || key == KeyEvent.VK_NUMPAD8) {
            EngineMethods.togglePauseSimulation();
        }

        if (key == KeyEvent.VK_9 || key == KeyEvent.VK_NUMPAD9) {
            BackgroundThread.run(ParticleSlideEditor::getInstance);
        }

        if (key == KeyEvent.VK_CONTROL) {
            engineSettings.controlKeyIsDown = false;
        }

        if (key == KeyEvent.VK_A) {
            EngineMethods.changePhysicsEditorMode(PhysicsEditorMode.Add);
        }

        if (key == KeyEvent.VK_D) {
            EngineMethods.changePhysicsEditorMode(PhysicsEditorMode.Drag);
        }

        if (key == KeyEvent.VK_S) {
            EngineMethods.changePhysicsEditorMode(PhysicsEditorMode.Select);
        }

        if (key == KeyEvent.VK_W) {
            BackgroundThread.run(ReactiveColorsEditor::getInstance);
        }

        if (key == KeyEvent.VK_M) {
            Physics.toggleGravity();
        }

        if (key == KeyEvent.VK_N) {
            Physics.toggleDebug();
        }

        if (key == KeyEvent.VK_P) {
            Physics.pinSelectedPoint();
        }

        if (key == KeyEvent.VK_C) {
            if (engineSettings.engineMode == RAGDOLL) {
                Physics.resetSelectedVertex();
                Physics.clearItems();
            } else {
                EngineMethods.clearAllMoleculeLists();
            }
        }

        if (key == KeyEvent.VK_LEFT) {
            EngineMethods.leftArrowFunction();
        }

        if (key == KeyEvent.VK_RIGHT) {
            EngineMethods.rightArrowFunction();
        }

        if (key == KeyEvent.VK_Q) {
            InstructionsWindow.createEngineInstructionsWindow(eFrame);
        }

        if (key == KeyEvent.VK_R) {
            ColorUtil.setEngineBackgroundColor();
        }

        if (key == KeyEvent.VK_T) {
            engineSettings.toggleParticleFriction();
        }

        if (key == KeyEvent.VK_V) {
            engineSettings.toggleGravityPointsLinkVisibility();
        }

        if (key == KeyEvent.VK_Y) {
            engineSettings.toggleParticleBoundsChecking();
        }

        if (key == KeyEvent.VK_B) {
            engineSettings.toggleParticlesLinkVisibility();
        }

        if (key == KeyEvent.VK_Z && e.isShiftDown()) {
            EngineSettings.loadSettings();
        }

        if (key == KeyEvent.VK_ESCAPE) {
            BackgroundThread.run(QuitWindow::getInstance);
        }

        if (key == KeyEvent.VK_I && e.isControlDown()) {
            BackgroundThread.run(StatsPanel::getInstance);
        }

        if (key == KeyEvent.VK_V && e.isShiftDown() && e.isControlDown()) {
            BackgroundThread.run(ExceptionLogger::getInstance);
        }

        if (key == KeyEvent.VK_X && e.isShiftDown()) {
            BackgroundThread.run(ParticleGrapher::getInstance);
        }
    }
}