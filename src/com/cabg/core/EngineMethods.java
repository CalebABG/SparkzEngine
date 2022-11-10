package com.cabg.core;

import com.cabg.components.CMenuBar;
import com.cabg.enums.GravitationMode;
import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.*;
import com.cabg.moleculetypes.Molecule;
import com.cabg.moleculetypes.Particle;
import com.cabg.reactivecolors.ReactiveColorsRandomizer;
import com.cabg.utilities.ColorUtil;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;
import com.cabg.utilities.NotificationUtil;
import com.cabg.verlet.Physics;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.EngineMode.*;
import static com.cabg.enums.MoleculeType.*;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputUtil.minValueGuard;

public class EngineMethods {
    // Molecule Methods
    public static void setParticleSize(int size) {
        for (int i = 0; i < Particles.size(); i++)
            if (size > -1) Particles.get(i).radius = size;
    }

    public static void increaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++)
            Particles.get(i).radius += 0.2;
    }

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

    public static void decreaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 0.2f;
            Particle p = Particles.get(i);
            p.radius -= n;
            if (p.radius < n) p.radius = n;
        }
    }

    private static void showGravitationOptions() {
        int gravityMode = (int) minValueGuard(0, engineSettings.gravitationMode.ordinal(), HTMLUtil.ParticleGravitationOptions);
        if (gravityMode < GravitationMode.values().length) engineSettings.gravitationMode = GravitationMode.values()[gravityMode];
        CMenuBar.updateGravitationModeRadios();
    }

    private static void showFireworksOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HeadingTag(3, HTMLUtil.FireworksOptions), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (rfoInt) {
            case 1: ParticleSlideEditor.showFireworksWindAmountDialog(); break;
            case 2: ParticleSlideEditor.showParticleLifeAmountDialog(); break;
            case 3: ParticleSlideEditor.showFireworksJitterAmountDialog(); break;
        }
    }

    private static void showParticleSizeSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HTMLUtil.ParticleSizeSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (seedOpt) {
            case 1: ParticleSizeEditor.getInstance(0); break;
            case 2: ParticleSizeEditor.getInstance(1); break;
            case 3: ParticleSizeEditor.getInstance(2); break;
        }
    }

    private static void showParticleSpeedSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HTMLUtil.ParticleSpeedSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (seedOpt) {
            case 1: ParticleSpeedEditor.getInstance(0); break;
            case 2: ParticleSpeedEditor.getInstance(1); break;
            case 3: ParticleSpeedEditor.getInstance(2); break;
        }
    }

    private static void showParticleSizeDialog() {
        float size = minValueGuard(0, .95f, HeadingTag(3, "Enter Particle Size"), OptionsMenu.frame);
        if (Particles.size() > 0) {
            for (int i = 0; i < Particles.size(); i++)
                Particles.get(i).radius = size;
        }
    }

    private static void showReactiveColorsCycleTimeOptions() {
        engineSettings.reactiveColorsCycleRateInSeconds = (int) minValueGuard(1, engineSettings.reactiveColorsCycleRateInSeconds,
                HeadingTag(3, "Enter Cycle Time (In Seconds)"));

        if (engineSettings.cycleReactiveColors)
            ReactiveColorsRandomizer.restartCycle();
    }

    private static void showParticleDragDialog() {
        engineSettings.particleDragAmount = (int) minValueGuard(1, engineSettings.particleDragAmount,
                HeadingTag(3, "Enter Particle Drag Amount"));
    }

    private static void showFireworksSafetyAmountDialog() {
        engineSettings.fireworksParticleSafetyAmount = (int) minValueGuard(0, engineSettings.fireworksParticleSafetyAmount,
                HeadingTag(3, "Enter Safety Amount"));
    }

    private static void showFireworksAmountDialog() {
        engineSettings.fireworksAmount = (int) minValueGuard(1, engineSettings.fireworksAmount,
                HeadingTag(3, "Enter Fireworks Amount"));
    }

    public static void handleMenuOptionsSelection(int option) {
        switch (option) {
            case 1: showParticleSizeDialog(); break;
            case 2: showParticleDragDialog(); break;
            case 3: showFireworksAmountDialog(); break;
            case 4: MoleculeTypePicker.getInstance(0, "Particle Type Options"); break;
            case 5: ColorUtil.setParticlePlainColor(); break;
            case 6: showGravitationOptions(); break;
            case 7: showParticleSizeSeedOptions(); break;
            case 8: showParticleSpeedSeedOptions(); break;
            case 9: showFireworksOptions(); break;
            case 10: MoleculeTypePicker.getInstance(1, "Firework Type Options"); break;
            case 11: ReactiveColorsEditor.getInstance(); break;
            case 12: showFireworksSafetyAmountDialog(); break;
            case 13: showReactiveColorsCycleTimeOptions(); break;
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

    public static void clearAllMoleculeLists() {
        List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

        for (int i = 0; i < moleculeLists.size(); i++) {
            for (int j = 0; j < moleculeLists.get(i).size(); j++) {
                if (moleculeLists.get(i).size() > 0)
                    moleculeLists.get(i).clear();
            }
        }
    }

    public static void trimAllMoleculeLists() {
        List<List<? extends Molecule>> moleculeLists = getAllMoleculeLists();

        for (int i = 0, t = 3; i < moleculeLists.size(); i++) {
            for (int j = (moleculeLists.get(i).size() - 1) / t; j >= 0; j--) {
                if (moleculeLists.get(i).size() > 0)
                    moleculeLists.get(i).remove(j);
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