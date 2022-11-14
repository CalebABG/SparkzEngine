package com.cabg.core;

import com.cabg.components.CMenuBar;
import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.PhysicsEditor;
import com.cabg.moleculetypes.Molecule;
import com.cabg.moleculetypes.Particle;
import com.cabg.utilities.NotificationUtil;
import com.cabg.verlet.Physics;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.EngineMode.GRAPH;
import static com.cabg.enums.EngineMode.RAGDOLL;
import static com.cabg.enums.MoleculeType.DUPLEX;

public class EngineMethods {
    // Molecule Methods
    public static void slowParticles() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 0.6f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    public static void scatterParticles() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 1.2f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    public static void increaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++)
            Particles.get(i).radius += 0.2f;
    }

    public static void decreaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 0.2f;
            Particle p = Particles.get(i);
            p.radius -= n;
            if (p.radius < n) p.radius = n;
        }
    }

    public static List<List<? extends Molecule>> getAllMoleculeLists() {
        return List.of(
                Particles, GravityPoints, Fireworks, Emitters,
                Fluxes, Erasers, QEDs, Ions,
                BlackHoles, Duplexes, Portals
        );
    }

    public static int getTotalMoleculesCount() {
        List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

        int count = 0;
        for (int i = 0; i < moleculeLists.size(); i++)
            count += moleculeLists.get(i).size();

        return count;
    }

    public static void clearAllEntityLists() {
        if (engineSettings.engineMode == RAGDOLL) {
            Physics.clearAllItems();
        }
        else {
            List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

            for (int i = 0; i < moleculeLists.size(); i++) {
                for (int j = 0; j < moleculeLists.get(i).size(); j++) {
                    if (moleculeLists.get(i).size() > 0)
                        moleculeLists.get(i).clear();
                }
            }
        }
    }

    public static void trimAllEntityLists() {
        int t = 3;
        if (engineSettings.engineMode == RAGDOLL) {
            if (Vertices.size() > 0) {
                for (int i = (Vertices.size() - 1) / t; i >= 0; i--)
                    Vertices.remove(i);
            }
        } else {
            List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

            for (int i = 0; i < moleculeLists.size(); i++) {
                for (int j = (moleculeLists.get(i).size() - 1) / t; j >= 0; j--) {
                    if (moleculeLists.get(i).size() > 0)
                        moleculeLists.get(i).remove(j);
                }
            }
        }
    }

    // Keyboard Input Methods
    public static void leftArrowFunction() {
        // Todo: Can possible replace check with using a notification queue?
        if (!NotificationUtil.drawingNotification) {
            if (engineSettings.engineMode == RAGDOLL) {
                PhysicsEditor.changeItemType(false);
                displayMoleculeTypeText();
            } else {
                engineSettings.changeParticleType(false);
                displayMoleculeTypeText();
                CMenuBar.updateMoleculeTypeRadios();
            }
        }
    }

    public static void rightArrowFunction() {
        if (!NotificationUtil.drawingNotification) {
            if (engineSettings.engineMode == RAGDOLL) {
                PhysicsEditor.changeItemType(true);
                displayMoleculeTypeText();
            } else {
                engineSettings.changeParticleType(true);
                displayMoleculeTypeText();
                CMenuBar.updateMoleculeTypeRadios();
            }
        }
    }

    public static void upArrowFunction() {
        if (engineSettings.moleculeType == DUPLEX && !NotificationUtil.drawingNotification) {
            engineSettings.toggleDuplexMode();
            if (engineSettings.duplexContain) NotificationUtil.pushNotification("Contain Mode");
            else NotificationUtil.pushNotification("Repel Mode");
        } else {
            ++engineSettings.particleDragAmount;
        }
    }

    public static void downArrowFunction() {
        if (--engineSettings.particleDragAmount < 1)
            engineSettings.particleDragAmount = 1;
    }

    // Engine State Methods
    public static void setEngineTitle() {
        eFrame.setTitle(engineSettings.paused ? title + " - PAUSED" : title);
    }

    public static void setEngineBackgroundColor() {
        Color f = JColorChooser.showDialog(eFrame, "Engine Background Color", backgroundColor);
        backgroundColor = (f != null) ? f : backgroundColor;
    }

    public static void togglePauseEngine() {
        engineSettings.togglePause();
        CMenuBar.updateState();
        setEngineTitle();
    }

    public static void advanceEngineMode() {
        if (!NotificationUtil.drawingNotification) {
            engineSettings.changeEngineMode(true);
            NotificationUtil.pushNotification(engineSettings.engineMode.name());
            CMenuBar.updateEngineModeRadios();
        }
    }

    public static void displayMoleculeTypeText() {
        if (engineSettings.engineMode == RAGDOLL) NotificationUtil.pushNotification(PhysicsEditor.ITEM_TYPE.name());
        else NotificationUtil.pushNotification(engineSettings.moleculeType.name());
    }

    // Physics Methods
    public static void changePhysicsEditorMode(PhysicsEditorMode editorMode) {
        if (engineSettings.engineMode == RAGDOLL && !NotificationUtil.drawingNotification) {
            PhysicsEditor.setEditorMode(editorMode);
            NotificationUtil.pushNotification(editorMode.name());
        }
    }

    public static void handleGraphicsSmoothing() {
        if (engineSettings.engineMode == RAGDOLL && engineSettings.smoothRenderingEnabled) {
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    // Graphing Methods
    public static void drawGraphLines() {
        final int vHeight = 35;
        graphics2D.setColor(Color.gray);
        graphics2D.drawLine(-canvas.getWidth() / 2, 0, canvas.getWidth() / 2, 0);
        graphics2D.drawLine(0, 0, 0, -vHeight);
        graphics2D.drawLine(0, 0, 0, vHeight);
    }

    // Update and Render Methods
    private static void updateAllMoleculeLists() {
        List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

        for (int i = 0; i < moleculeLists.size(); i++) {
            for (int j = 0; j < moleculeLists.get(i).size(); j++) {
                moleculeLists.get(i).get(j).update();
            }
        }
    }

    private static void renderAllMoleculeLists() {
        List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

        for (int i = 0; i < moleculeLists.size(); i++) {
            for (int j = 0; j < moleculeLists.get(i).size(); j++) {
                moleculeLists.get(i).get(j).render();
            }
        }
    }

    public static void handleUpdates() {
        if (engineSettings.engineMode == RAGDOLL) Physics.update();
        else updateAllMoleculeLists();
    }

    public static void handleRenders() {
        handleGraphicsSmoothing();

        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (engineSettings.engineMode == GRAPH) graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        else graphics2D.translate(0, 0);

        if (engineSettings.engineMode == RAGDOLL) {
            Physics.render();
        } else {
            if (engineSettings.engineMode == GRAPH) drawGraphLines();
            renderAllMoleculeLists();
        }

        NotificationUtil.handleNotifications();
    }
}