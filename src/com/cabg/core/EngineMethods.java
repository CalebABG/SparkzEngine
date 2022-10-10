package com.cabg.core;

import com.cabg.moleculehelpers.MoleculeFactory;
import com.cabg.components.CMenuBar;
import com.cabg.enums.GravitationMode;
import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.*;
import com.cabg.inputhandlers.KeyboardHandler;
import com.cabg.inputhandlers.MouseMotionHandler;
import com.cabg.moleculetypes.Particle;
import com.cabg.reactivecolors.ReactiveColorsRandomizer;
import com.cabg.utilities.ColorUtil;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;
import com.cabg.utilities.NotificationUtil;
import com.cabg.verlet.Physics;
import com.cabg.verlet.PhysicsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.cabg.components.CMenuBar.*;
import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.EngineMode.*;
import static com.cabg.enums.ParticleType.*;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputUtil.minValueGuard;
import static com.cabg.verlet.Vertex.Vertices;

public class EngineMethods {
    /**
     * Switches the particle mode depending on the int(mode) passed to the function.
     *
     * @param e Mouse Event.
     * @see MouseMotionHandler
     */
    public static void switchClickMode(MouseEvent e) {
        switch (engineSettings.engineMode) {
            case NORMAL:
                switch (engineSettings.particleType) {
                    case PARTICLE: MoleculeFactory.singleParticle(e); break;
                    case GRAVITY_POINT: MoleculeFactory.singleGravityPoint(e); break;
                    case EMITTER: MoleculeFactory.singleEmitter(e); break;
                    case FLUX: MoleculeFactory.singleFlux(e); break;
                    case QED: MoleculeFactory.singleQED(e); break;
                    case ION: MoleculeFactory.singleIon(e); break;
                    case BLACK_HOLE: MoleculeFactory.singleBlackHole(e); break;
                    case DUPLEX: MoleculeFactory.singleDuplex(e); break;
                    case PORTAL: MoleculeFactory.singlePortal(e); break;
                }
                break;
            case MULTI:
                switch (engineSettings.particleType) {
                    case PARTICLE: MoleculeFactory.multiParticle(e); break;
                    case GRAVITY_POINT: MoleculeFactory.multiGravityPoint(e, 4); break;
                    case EMITTER: MoleculeFactory.singleEmitter(e); break;
                    case FLUX: MoleculeFactory.multiFlux(e, 4); break;
                    case QED: MoleculeFactory.multiQED(e, 4); break;
                    case ION: MoleculeFactory.multiIon(e, 10); break;
                    case BLACK_HOLE: MoleculeFactory.singleBlackHole(e); break;
                    case DUPLEX: MoleculeFactory.singleDuplex(e); break;
                    case PORTAL: MoleculeFactory.singlePortal(e); break;
                }
                break;
            case FIREWORKS:
                engineSettings.particlesGravitateToMouse = false;
                MoleculeFactory.fireworksTarget(e);
                break;
            case RAGDOLL:
                PhysicsHandler.handleRagdollClickEvent(e);
                break;
        }
    }

    /**
     * Iterates through the Particles list.
     *
     * @param size sets each particle's radius to the specified size
     * @see ParticleSlideEditor
     */
    public static void setParticleSize(int size) {
        for (int i = 0; i < Particles.size(); i++)
            if (size > -1) Particles.get(i).radius = size;
    }

    /**
     * Increases each particles radius
     *
     * @see KeyboardHandler
     */
    public static void increaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++)
            Particles.get(i).radius += 0.2;
    }

    /**
     * Decreases each particles velocity x and velocity y by 60%
     *
     * @see KeyboardHandler
     */
    public static void slowParticles() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 0.6f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Multiplies each particles velocity x and velocity by 120%
     *
     * @see KeyboardHandler
     */
    public static void scatterParticles() {
        for (int i = 0; i < Particles.size(); i++) {
            final float n = 1.2f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Decreases each particles radius and limits the particles minimum radius
     *
     * @see KeyboardHandler
     */
    public static void decreaseParticleSize() {
        for (int i = 0; i < Particles.size(); i++) {
            Particle p = Particles.get(i);
            p.radius -= 0.2;
            if (p.radius < 0.2) p.radius = 0.2f;
        }
    }

    /**
     * Toggles the Engines ability to smooth the graphics. Smooth graphics decrease performance but are appealing.
     *
     * @see KeyboardHandler
     */
    public static void toggleGraphicsSmoothing() {
        engineSettings.toggleGraphicsSmoothing();
    }

    /**
     * Handles the Engines graphics smoothing. If the Engines boolean to smooth graphics is true and the particle
     * mode is set to Ragdoll, then Engine applies graphical smoothing (Antialiasing)
     *
     * @see Engine
     */
    public static void handleGraphicsSmoothing() {
        if (engineSettings.engineMode == RAGDOLL && engineSettings.smoothRenderingEnabled) {
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    /**
     * Updates the Engines Mouses' position each update.
     *
     * @see MouseMotionHandler
     */
    public static void updateMouse(MouseEvent e) {
        Mouse.x = e.getX(); Mouse.y = e.getY();
    }

    /**
     * A useful method to change a booleans value by negation.
     *
     * @see com.cabg.components.CMenuBar
     * @see KeyboardHandler
     * @see ReactiveColorsEditor
     * @see PhysicsHandler
     */
    public static boolean toggle(boolean bool) {
        return !bool;
    }

    /**
     * @param amount Sets the Engines drag amount to the specified amount.
     * @see ParticleSlideEditor
     */
    public static void setDragAmount(int amount) {
        if (amount > -1) engineSettings.particleDragAmount = amount;
    }

    /**
     * @param amount Sets the Engines fireworks amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksAmount(int amount) {
        if (amount > -1) engineSettings.fireworksAmount = amount;
    }

    /**
     * @param amount Sets the Engines life amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setParticleLifeAmount(int amount) {
        if (amount > -1) engineSettings.particleLife = amount;
    }

    /**
     * @param amount Sets the Engines wind amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksWindAmount(int amount) {
        if (amount > -1) engineSettings.fireworksWind = amount;
    }

    /**
     * @param amount Sets the Engines jitter amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksJitterAmount(int amount) {
        if (amount > -1) engineSettings.fireworksJitter = amount;
    }

    /**
     * @param amount Sets the Engines single click minimum particle size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSizeMin(int amount) {
        if (amount > -1) engineSettings.singleClickSizeMin = amount;
    }

    /**
     * @param amount Sets the Engines single click maximum particle size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSizeMax(int amount) {
        if (amount > -1) engineSettings.singleClickSizeMax = amount;
    }

    /**
     * @param amount Sets the Engines fireworks minimum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSizeMin(int amount) {
        if (amount > -1) engineSettings.fireworksSizeMin = amount;
    }

    /**
     * @param amount Sets the Engines fireworks maximum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSizeMax(int amount) {
        if (amount > -1) engineSettings.fireworksSizeMax = amount;
    }

    /**
     * @param amount Sets the Engines drag minimum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setParticleDragSizeMin(int amount) {
        if (amount > -1) engineSettings.particleDragSizeMin = amount;
    }

    /**
     * @param amount Sets the Engines drag maximum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setParticleDragSizeMax(int amount) {
        if (amount > -1) engineSettings.particleDragSizeMin = amount;
    }

    /**
     * @param amount Sets the Engines single click speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSpeed(int amount) {
        if (amount > -1) engineSettings.singleClickSpeed = amount;
    }

    /**
     * @param amount Sets the Engines fireworks speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSpeed(int amount) {
        if (amount > -1) engineSettings.fireworksSpeed = amount;
    }

    /**
     * @param amount Sets the Engines drag speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setDragSpeed(int amount) {
        if (amount > -1) engineSettings.particleDragSpeed = amount;
    }

    /**
     * Sets the Engines particle friction boolean.
     *
     * @see KeyboardHandler
     */
    public static void toggleParticleFriction() {
        engineSettings.toggleParticleFriction();
    }

    /**
     * Sets the Engines mouse gravitation boolean.
     *
     * @see KeyboardHandler
     */
    public static void toggleMouseGravitation() {
        engineSettings.toggleMouseGravitation();
    }

    /**
     * Sets the Engines connect particles boolean.
     *
     * @see KeyboardHandler
     */
    public static void toggleConnectedParticlesMode() {
        engineSettings.toggleConnectedParticlesMode();
    }

    /**
     * Sets the Engines ReactiveColors boolean.
     *
     * @see KeyboardHandler
     */
    public static void toggleReactiveColors() {
        engineSettings.toggleReactiveColors();
    }

    public static void toggleParticleBoundsChecking() {
        engineSettings.toggleParticleBoundsChecking();
    }

    /**
     * Determines which gravitation mode to use for particles in the Particles List.
     */
    private static void gravitationOptions() {
        int gravityMode = (int) minValueGuard(0, engineSettings.gravitationMode.ordinal(), HTMLUtil.ParticleGravitationOptions);
        if (gravityMode < GravitationMode.values().length) engineSettings.gravitationMode = GravitationMode.values()[gravityMode];
        updateGravitationModeRadios();
    }

    /**
     * Dialog window for adjusting the fireworks options (Wind, Life, Jitter)
     */
    private static void realFireworks() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HeadingTag(3, HTMLUtil.FireworksOptions), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = (InputUtil.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (rfoInt) {
            case 1: fireworksWindAmount(); break;
            case 2: particleLifeAmount(); break;
            case 3: jitterAmount(); break;
        }
    }

    /**
     * Dialog window for adjusting the particles size in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSizeSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HTMLUtil.ParticleSizeSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputUtil.canParseStringInt(input)) ? Integer.parseInt(input) : -1;

        switch (seedOpt) {
            case 1: ParticleSizeEditor.getInstance(0); break;
            case 2: ParticleSizeEditor.getInstance(1); break;
            case 3: ParticleSizeEditor.getInstance(2); break;
        }
    }

    /**
     * Dialog window for adjusting the particles speed in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSpeedSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HTMLUtil.ParticleSpeedSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputUtil.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (seedOpt) {
            case 1: ParticleSpeedEditor.getInstance(0); break;
            case 2: ParticleSpeedEditor.getInstance(1); break;
            case 3: ParticleSpeedEditor.getInstance(2); break;
        }
    }

    /**
     * Dialog window to set the SlideEditors particle size sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxParticleSizeSlider() {
        ParticleSlideEditor.ptsslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptsslider.getMaximum(),
                HeadingTag(3, "Enter Max Particle Size"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the SlideEditors drag amount sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxDragSlider() {
        ParticleSlideEditor.ptdrslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptdrslider.getMaximum(),
                HeadingTag(3, "Enter Max Drag Amount"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the SlideEditors fireworks sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxFireworksSlider() {
        ParticleSlideEditor.ptfrslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptfrslider.getMaximum(),
                HeadingTag(3, "Enter Max Fireworks Amount"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the radius of all particles in the Particles list.
     */
    private static void particleSize() {
        float r = minValueGuard(0, .95f, HeadingTag(3, "Enter Particle Size"), OptionsMenu.frame);
        if (Particles.size() > 0) {
            for (int i = 0; i < Particles.size(); i++) Particles.get(i).radius = r;
        }
    }

    /**
     * Dialog window to set the cycle time for changing particle colors. If cycling colors is on, after a valid number (in
     * second) is entered, the current Timer is stopped and reset and then started with the new cycle time.
     */
    private static void cycleTimeOptions() {
        engineSettings.reactiveColorsCycleRateInSeconds = (int) minValueGuard(1, engineSettings.reactiveColorsCycleRateInSeconds,
                HeadingTag(3, "Enter Cycle Time (In Seconds)"));

        if (engineSettings.cycleReactiveColors) {
            ReactiveColorsRandomizer.stopCycle();
            ReactiveColorsRandomizer.startCycle();
        }
    }

    public static void particleLifeAmount() {
        engineSettings.particleLife = (int) minValueGuard(0, engineSettings.particleLife,
                HeadingTag(3, "Enter Particle Life Amount"), ParticleSlideEditor.frame);
    }

    public static void fireworksWindAmount() {
        engineSettings.fireworksWind = (int) minValueGuard(0, engineSettings.fireworksWind,
                HeadingTag(3, "Enter Fireworks Wind Amount"), ParticleSlideEditor.frame);
    }

    public static void fireworksLifeAmount() {
        engineSettings.fireworksLife = (int) minValueGuard(0, engineSettings.fireworksLife,
                HeadingTag(3, "Enter Fireworks Life Amount"), ParticleSlideEditor.frame);
    }

    public static void jitterAmount() {
        engineSettings.fireworksJitter = (int) minValueGuard(0, engineSettings.fireworksJitter,
                HeadingTag(3, "Enter Fireworks Jitter Amount"), ParticleSlideEditor.frame);
    }

    private static void particleDrag() {
        engineSettings.particleDragAmount = (int) minValueGuard(1, engineSettings.particleDragAmount,
                HeadingTag(3, "Enter Particle Drag Amount"));
    }

    private static void safetyAmountOptions() {
        engineSettings.fireworksParticleSafetyAmount = (int) minValueGuard(0, engineSettings.fireworksParticleSafetyAmount,
                HeadingTag(3, "Enter Safety Amount"));
    }

    private static void particleFireworks() {
        engineSettings.fireworksAmount = (int) minValueGuard(1, engineSettings.fireworksAmount,
                HeadingTag(3, "Enter Fireworks Amount"));
    }

    /**
     * Sets the Engines title based on the state of the Engine (paused or running).
     *
     * @see CMenuBar
     * @see System
     */
    public static void setEngineTitle() {
        if (engineSettings.paused) EFrame.setTitle(title + " - PAUSED");
        else EFrame.setTitle(title);
    }


    public static void createEngineInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(0, parent,
                (int) (d.width * .44), (int) (d.height * .55),
                "Particle Engine Instructions", HTMLUtil.EngineInstructions);
    }

    public static void createGraphInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(1, parent,
                (int) (d.width * .37), (int) (d.height * .55),
                "Particle Graph Instructions", HTMLUtil.ParticleGraphInstructions);
    }

    /**
     * Toggles the state of the Engine (paused or running).
     *
     * @see KeyboardHandler
     */
    public static void togglePauseSimulation() {
        engineSettings.togglePause();
        CMenuBar.updateState();
        setEngineTitle();
    }

    /**
     * Different options for the Engines Options Menu.
     *
     * @see OptionsMenu
     */
    public static void getOptions(int option) {
        switch (option) {
            case 1: particleSize(); break;
            case 2: particleDrag(); break;
            case 3: particleFireworks(); break;
            case 4: ParticleTypePicker.getInstance(0, "Particle Type Options"); break;
            case 5: ColorUtil.setParticlePlainColor(); break;
            case 6: gravitationOptions(); break;
            case 7: particleSizeSeedOptions(); break;
            case 8: particleSpeedSeedOptions(); break;
            case 9: realFireworks(); break;
            case 10: ParticleTypePicker.getInstance(1, "Firework Type Options"); break;
            case 11: ReactiveColorsEditor.getInstance(); break;
            case 12: safetyAmountOptions(); break;
            case 13: cycleTimeOptions(); break;
        }
    }

    public static void updateEngineMode() {
        // Check whether a notification is being requested already
        // If it is then don't update or display until it's finished
        if (!NotificationUtil.drawingNotification) {
            engineSettings.changeEngineMode(true);
            displayEngineMode();
            updateEngineModeRadios();
        }
    }

    public static <T extends Enum<T>> T getMode(T enumType, boolean advance) {
        T[] values = enumType.getDeclaringClass().getEnumConstants();
        if (advance) return values[(enumType.ordinal() + 1) % values.length];
        else return values[(values.length + (enumType.ordinal() - 1)) % values.length];
    }

    public static void changePhysicsEditorMode(PhysicsEditorMode editorMode){
        if (engineSettings.engineMode == RAGDOLL && !NotificationUtil.drawingNotification){
            PhysicsEditor.EDITOR_MODE = editorMode;
            PhysicsEditor.editorModesJComboBox.setSelectedItem(editorMode);
            NotificationUtil.pushNotification(editorMode.name());
        }
    }

    /**
     * Handles the action in response to a key listener for the left arrow key.
     *
     * @see KeyboardHandler
     */
    public static void leftArrowFunction() {
        if (engineSettings.engineMode == RAGDOLL) {
            if (!NotificationUtil.drawingNotification) {
                PhysicsEditor.ITEM_TYPE = getMode(PhysicsEditor.ITEM_TYPE, false);
                PhysicsEditor.creationModesJComboBox.setSelectedItem(PhysicsEditor.ITEM_TYPE);
                displayParticleType();
            }
        } else {
            if (!NotificationUtil.drawingNotification) {
                engineSettings.changeParticleType(false);
                displayParticleType();
                updateParticleTypeRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the right arrow key.
     *
     * @see KeyboardHandler
     */
    public static void rightArrowFunction() {
        if (engineSettings.engineMode == RAGDOLL) {
            if (!NotificationUtil.drawingNotification) {
                PhysicsEditor.ITEM_TYPE = getMode(PhysicsEditor.ITEM_TYPE, true);
                PhysicsEditor.creationModesJComboBox.setSelectedItem(PhysicsEditor.ITEM_TYPE);
                displayParticleType();
            }
        } else {
            if (!NotificationUtil.drawingNotification) {
                engineSettings.changeParticleType(true);
                displayParticleType();
                updateParticleTypeRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the up arrow key.
     *
     * @see KeyboardHandler
     */
    public static void upArrowFunction() {
        if (engineSettings.particleType == DUPLEX) {
            if (!NotificationUtil.drawingNotification) {
                engineSettings.toggleDuplexMode();
                if (engineSettings.duplexContain) NotificationUtil.pushNotification("Contain Mode");
                else NotificationUtil.pushNotification("Repel Mode");
            }
        } else {
            ++engineSettings.particleDragAmount;
        }
    }

    public static void downArrowFunction() {
        if (--engineSettings.particleDragAmount < 1)
            engineSettings.particleDragAmount = 1;
    }

    /**
     * Empties all the Engines molecule lists.
     *
     * @see KeyboardHandler
     * @see CMenuBar
     */
    public static void clearAllMoleculeLists() {
        if (engineSettings.engineMode == RAGDOLL) {
            PhysicsHandler.resetSelectedVertex();
            if (Vertices.size() > 0) Vertices.clear();
        } else {
            if (Particles.size() > 0) Particles.clear();
            if (GravityPoints.size() > 0) GravityPoints.clear();
            if (Emitters.size() > 0) Emitters.clear();
            if (Fireworks.size() > 0) Fireworks.clear();
            if (Fluxes.size() > 0) Fluxes.clear();
            if (Erasers.size() > 0) Erasers.clear();
            if (QEDs.size() > 0) QEDs.clear();
            if (Ions.size() > 0) Ions.clear();
            if (BlackHoles.size() > 0) BlackHoles.clear();
            if (Duplexes.size() > 0) Duplexes.clear();
            if (Portals.size() > 0) Portals.clear();
        }
    }

    /**
     * Slices all Molecule list sizes.
     *
     * @see KeyboardHandler
     * @see CMenuBar
     */
    public static void trimMoleculeLists() {
        int t = 3;
        if (Particles.size() > 0) for (int i = (Particles.size() - 1) / t; i >= 0; i--) Particles.remove(i);
        if (GravityPoints.size() > 0) for (int i = (GravityPoints.size() - 1) / t; i >= 0; i--) GravityPoints.remove(i);
        if (Fireworks.size() > 0) for (int i = (Fireworks.size() - 1) / t; i >= 0; i--) Fireworks.remove(i);
        if (Emitters.size() > 0) for (int i = (Emitters.size() - 1) / t; i >= 0; i--) Emitters.remove(i);
        if (Fluxes.size() > 0) for (int i = (Fluxes.size() - 1) / t; i >= 0; i--) Fluxes.remove(i);
        if (Erasers.size() > 0) for (int i = (Erasers.size() - 1) / t; i >= 0; i--) Erasers.remove(i);
        if (QEDs.size() > 0) for (int i = (QEDs.size() - 1) / t; i >= 0; i--) QEDs.remove(i);
        if (Ions.size() > 0) for (int i = (Ions.size() - 1) / t; i >= 0; i--) Ions.remove(i);
        if (BlackHoles.size() > 0) for (int i = (BlackHoles.size() - 1) / t; i >= 0; i--) BlackHoles.remove(i);
        if (Duplexes.size() > 0) for (int i = (Duplexes.size() - 1) / t; i >= 0; i--) Duplexes.remove(i);
        if (Portals.size() > 0) for (int i = (Portals.size() - 1) / t; i >= 0; i--) Portals.remove(i);
    }

    /**
     * Displays the particle mode.
     *
     * @see CMenuBar
     * @see KeyboardHandler
     */
    public static void displayEngineMode() {
        NotificationUtil.pushNotification(engineSettings.engineMode.name());
    }

    /**
     * Displays the particle type.
     *
     * @see CMenuBar
     */
    public static void displayParticleType() {
        if (engineSettings.engineMode == RAGDOLL) NotificationUtil.pushNotification(PhysicsEditor.ITEM_TYPE.name());
        else NotificationUtil.pushNotification(engineSettings.particleType.name());
    }

    /**
     * Draws a horizontal line with a mid point for the Engines Graph mode.
     */
    public static void drawMid() {
        int vHeight = 35;
        graphics2D.setColor(Color.gray);
        graphics2D.drawLine(-canvas.getWidth() / 2, 0, canvas.getWidth() / 2, 0);
        graphics2D.drawLine(0, 0, 0, -vHeight);
        graphics2D.drawLine(0, 0, 0, vHeight);
    }

    /**
     * Returns whether the Engines reactive colors boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getReactiveColorsStatus() {
        return "Reactive Colors: " + (engineSettings.reactiveColorsEnabled ? "On" : "Off");
    }

    /**
     * Returns whether the Engines mouse gravitation boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getMouseAttraction() {
        return "Mouse Attraction: " + ( engineSettings.particlesGravitateToMouse ? "On" : "Off");
    }

    /**
     * Returns whether the Engines particle friction boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getFrictionText() {
        return "Particle Friction: " + (engineSettings.particleFriction ? "On" : "Off");
    }

    /**
     * Returns whether the Engines connect particles boolean is true or false and whether the Particles list size is
     * less than or equal to 100.
     *
     * @see StatsPanel
     */
    public static String getConnectText() {
        return engineSettings.connectParticles && Particles.size() < 101 ? "On" : "Off";
    }

    /**
     * Current particle mode in string representation.
     *
     * @see StatsPanel
     */
    public static String getModeText() {
        return "Particle Mode: " + engineSettings.engineMode.name();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    private static void updateParticles() {for (int i = 0; i < Particles.size(); i++) Particles.get(i).update();}
    private static void updateGravityPoints() {for (int i = 0; i < GravityPoints.size(); i++) GravityPoints.get(i).update();}
    private static void updateEmitters() {for (int i = 0; i < Emitters.size(); i++) Emitters.get(i).update();}
    private static void updateFireworks() {for (int i = 0; i < Fireworks.size(); i++) Fireworks.get(i).update();}
    private static void updateFluxes() {for (int i = 0; i < Fluxes.size(); i++) Fluxes.get(i).update();}
    private static void updateErasers() {for (int i = 0; i < Erasers.size(); i++) Erasers.get(i).update();}
    private static void updateQEDs() {for (int i = 0; i < QEDs.size(); i++) QEDs.get(i).update();}
    private static void updateIons() {for (int i = 0; i < Ions.size(); i++) Ions.get(i).update();}
    private static void updateBlackHoles() {for (int i = 0; i < BlackHoles.size(); i++) BlackHoles.get(i).update();}
    private static void updateDuplexes() {for (int i = 0; i < Duplexes.size(); i++) Duplexes.get(i).update();}
    private static void updatePortals() {for (int i = 0; i < Portals.size(); i++) Portals.get(i).update();}

    //--------------------------------------------------------------------------------------------------------------------------------------//
    private static void renderParticles() {for (int i = 0; i < Particles.size(); i++) Particles.get(i).render();}
    private static void renderGravityPoints() {for (int i = 0; i < GravityPoints.size(); i++) GravityPoints.get(i).render();}
    private static void renderEmitters() {for (int i = 0; i < Emitters.size(); i++) Emitters.get(i).render();}
    private static void renderFireworks() {for (int i = 0; i < Fireworks.size(); i++) Fireworks.get(i).render();}
    private static void renderFluxes() {for (int i = 0; i < Fluxes.size(); i++) Fluxes.get(i).render();}
    private static void renderErasers() {for (int i = 0; i < Erasers.size(); i++) Erasers.get(i).render();}
    private static void renderQEDs() {for (int i = 0; i < QEDs.size(); i++) QEDs.get(i).render();}
    private static void renderIons() {for (int i = 0; i < Ions.size(); i++) Ions.get(i).render();}
    private static void renderBlackHoles() {for (int i = 0; i < BlackHoles.size(); i++) BlackHoles.get(i).render();}
    private static void renderDuplexes() {for (int i = 0; i < Duplexes.size(); i++) Duplexes.get(i).render();}
    private static void renderPortals() {for (int i = 0; i < Portals.size(); i++) Portals.get(i).render();}

    /**
     * Updates all the particles in their respective ArrayLists.
     */
    public static void handleUpdates() {
        try {
            if (engineSettings.engineMode != RAGDOLL) {
                updateParticles();
                updateGravityPoints();
                updateFireworks();
                updateEmitters();
                updateFluxes();
                updateErasers();
                updateQEDs();
                updateIons();
                updateBlackHoles();
                updateDuplexes();
                updatePortals();
            }
            else {
                PhysicsHandler.COLLISION_DETECTION = Vertices.size() <= PhysicsHandler.MAX_COLLISIONS;
            }
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    /**
     * Renders all the particles and updates + renders Ragdoll physics.
     */
    public static void handleRenders() {
        try {
            graphics2D.setColor(backgroundColor);
            graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            if (engineSettings.engineMode == GRAPH) graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
            else graphics2D.translate(0, 0);

            switch (engineSettings.engineMode) {
                case GRAPH:
                    drawMid();
                    renderParticles();
                    break;
                case RAGDOLL:
                    PhysicsHandler.debugPhysics();
                    Physics.update();
                    Physics.render();
                    break;
                default:
                    renderParticles();
                    renderGravityPoints();
                    renderFireworks();
                    renderEmitters();
                    renderFluxes();
                    renderErasers();
                    renderQEDs();
                    renderIons();
                    renderBlackHoles();
                    renderDuplexes();
                    renderPortals();
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }
}