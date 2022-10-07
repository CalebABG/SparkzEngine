package com.engine.EngineHelpers;

import com.engine.GUIWindows.*;
import com.engine.InputHandlers.KeyboardHandler;
import com.engine.InputHandlers.MouseMotionHandler;
import com.engine.JComponents.CMenuBar;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.ReactiveColorsRandomizer;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.InputGuard;
import com.engine.Verlet.Physics;
import com.engine.Verlet.VModes;
import com.engine.Verlet.VPHandler;
import com.engine.Verlet.VSim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EFLOATS.*;
import static com.engine.EngineHelpers.EINTS.*;
import static com.engine.Enums.EngineMode.*;
import static com.engine.Enums.GravitationMode.*;
import static com.engine.Enums.ParticleType.*;
import static com.engine.JComponents.CMenuBar.*;
import static com.engine.Utilities.HTMLUtil.HeadingTag;
import static com.engine.Utilities.InputGuard.minValueGuard;
import static com.engine.Verlet.Vertex.Vertices;

public class EngineMethods {
    /**
     * Switches the particle mode depending on the int(mode) passed to the function.
     *
     * @param e Mouse Event.
     * @see MouseMotionHandler
     */
    public static void switchClickMode(MouseEvent e) {
        switch (ENGINE_MODE) {
            case NORMAL:
                switch (PARTICLE_TYPE) {
                    case PARTICLE: ParticleModes.singleParticle(e); break;
                    case GRAVITY_POINT: ParticleModes.singleGravityPoint(e); break;
                    case EMITTER: ParticleModes.singleEmitter(e); break;
                    case FLUX: ParticleModes.singleSemtex(e); break;
                    case QED: ParticleModes.singleQED(e); break;
                    case ION: ParticleModes.singleIon(e); break;
                    case BLACK_HOLE: ParticleModes.singleBlackHole(e); break;
                    case DUPLEX: ParticleModes.singleDuplex(e); break;
                    case PORTAL: ParticleModes.singlePortal(e); break;
                }
                break;
            case MULTI:
                switch (PARTICLE_TYPE) {
                    case PARTICLE: ParticleModes.multiParticle(e); break;
                    case GRAVITY_POINT: ParticleModes.multiGravityPoint(e, 4); break;
                    case EMITTER: ParticleModes.singleEmitter(e); break;
                    case FLUX: ParticleModes.multiSemtex(e, 4); break;
                    case QED: ParticleModes.multiQED(e, 4); break;
                    case ION: ParticleModes.multiIon(e, 10); break;
                    case BLACK_HOLE: ParticleModes.singleBlackHole(e); break;
                    case DUPLEX: ParticleModes.singleDuplex(e); break;
                    case PORTAL: ParticleModes.singlePortal(e); break;
                }
                break;
            case FIREWORKS:
                GRAVITATE_TO_MOUSE.setValue(false);
                ParticleModes.fireworksTarget(e);
                break;
            case RAGDOLL:
                VPHandler.handleRagdollClickEvent(e);
                break;
        }
    }

    /**
     * Iterates through the ParticlesArray.
     *
     * @param size sets each particles radius to the specified size
     * @see ParticleSlideEditor
     */
    public static void setParticleSize(int size) {
        for (int i = 0; i < Particles.size(); i++)
            if (size >= 0) Particles.get(i).radius = size;
    }

    /**
     * Increases each particles radius by .5
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
            float n = 0.6f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Multiplies each particles velocity x and velocity by 150%
     *
     * @see KeyboardHandler
     */
    public static void scatterParticles() {
        for (int i = 0; i < Particles.size(); i++) {
            float n = 1.5f;
            Particle p = Particles.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Decreases each particles radius by .5 and limits the particles minimum radius to .95
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
        ENGINE_ENABLE_SMOOTH_RENDER.toggleValue();
    }

    /**
     * Handles the Engines graphics smoothing. If the Engines boolean to smooth graphics is true and the particle
     * mode is set to Ragdoll, then Engine applies graphical smoothing (Antialiasing)
     *
     * @see com.engine.Main.Engine
     */
    public static void handleGraphicsSmoothing() {
        if (ENGINE_MODE == RAGDOLL) {
            if (ENGINE_ENABLE_SMOOTH_RENDER.value()) {
                graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
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
     * @see com.engine.JComponents.CMenuBar
     * @see KeyboardHandler
     * @see ReactiveColorsEditor
     * @see com.engine.Verlet.VSim
     */
    public static boolean toggle(boolean bool) {
        return !bool;
    }

    /**
     * @param amount Sets the Engines drag amount to the specified amount.
     * @see ParticleSlideEditor
     */
    public static void setDragAmount(int amount) {
        if (amount > -1) PARTICLE_DRAG_AMOUNT.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksAmount(int amount) {
        if (amount > -1) FIREWORKS_AMOUNT.setValue(amount);
    }

    /**
     * @param amount Sets the Engines life amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setLifeAmount(int amount) {
        if (amount > -1) PARTICLE_BASE_LIFE.setValue(amount);
    }

    /**
     * @param amount Sets the Engines wind amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setWindAmount(int amount) {
        if (amount > -1) FIREWORKS_WIND.setValue(amount);
    }

    /**
     * @param amount Sets the Engines jitter amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setJitterAmount(int amount) {
        if (amount > -1) FIREWORKS_JITTER.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click minimum particle size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSizeMin(int amount) {
        if (amount > -1) SINGLE_CLICK_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click maximum particle size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSizeMax(int amount) {
        if (amount > -1) SINGLE_CLICK_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks minimum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSizeMin(int amount) {
        if (amount > -1) FIREWORKS_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks maximum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSizeMax(int amount) {
        if (amount > -1) FIREWORKS_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag minimum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setDragSizeMin(int amount) {
        if (amount > -1) PARTICLE_DRAG_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag maximum size amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setDragSizeMax(int amount) {
        if (amount > -1) PARTICLE_DRAG_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setSingleClickSpeed(int amount) {
        if (amount > -1) SINGLE_CLICK_SPEED.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setFireworksSpeed(int amount) {
        if (amount > -1) FIREWORKS_SPEED.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag speed amount to the specified amount
     * @see ParticleSlideEditor
     */
    public static void setDragSpeed(int amount) {
        if (amount > -1) PARTICLE_DRAG_SPEED.setValue(amount);
    }

    /**
     * Sets the Engines particle friction boolean.
     *
     * @see KeyboardHandler
     */
    public static void toggleParticleFriction() {
        PARTICLE_FRICTION.toggleValue();
    }

    /**
     * Sets the Engines mouse gravitation boolean.
     *
     * @see KeyboardHandler
     */
    public static void mouseGravitationToggle() {
        GRAVITATE_TO_MOUSE.toggleValue();
    }

    /**
     * Sets the Engines connect particles boolean.
     *
     * @see KeyboardHandler
     */
    public static void connectParticlesMode() {
        CONNECT_PARTICLES.toggleValue();
    }

    /**
     * Sets the Engines thinking particles boolean.
     *
     * @see KeyboardHandler
     */
    public static void thinkingParticlesMode() {
        REACTIVE_PARTICLES_ENABLED.toggleValue();
    }

    public static void toggleParticleBoundsChecking() {
        PARTICLE_BOUNDLESS.toggleValue();
    }

    /**
     * Determines which gravitation mode to use for particles in the ParticlesArray ArrayList.
     */
    private static void gravitationOptions() {
        int gravityMode = (int) minValueGuard(0, PARTICLE_GRAVITATION_MODE.ordinal(), GUIText.ParticleGravitationOptions);
        switch (gravityMode) {
            case 0: PARTICLE_GRAVITATION_MODE = DEFAULT; break;
            case 1: PARTICLE_GRAVITATION_MODE = COSINE_SINE; break;
            case 2: PARTICLE_GRAVITATION_MODE = ARC_TANGENT; break;
            case 3: PARTICLE_GRAVITATION_MODE = HORIZONTAL_WAVE; break;
            case 4: PARTICLE_GRAVITATION_MODE = VERTICAL_WAVE; break;
            case 5: PARTICLE_GRAVITATION_MODE = SPIRALS; break;
            case 6: PARTICLE_GRAVITATION_MODE = PARTICLE_REPELLENT; break;
            case 7: PARTICLE_GRAVITATION_MODE = ORGANIC; break;
            case 8: PARTICLE_GRAVITATION_MODE = FLOW_FIELD; break;
            default: break;
        }
        updateGravitationModesRadios();
    }

    /**
     * Dialog window for adjusting the fireworks options (Wind, Life, Jitter)
     */
    private static void realFireworks() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, HeadingTag(3, GUIText.FireworksOptions), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (rfoInt) {
            case 1: windAmount();     break;
            case 2: baseLifeAmount(); break;
            case 3: jitterAmount();   break;
            default: break;
        }
    }

    /**
     * Dialog window for adjusting the particles size in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSizeSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, GUIText.ParticleSizeSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;

        switch (seedOpt) {
            case 1: ParticleSizeEditor.getInstance(0); break;
            case 2: ParticleSizeEditor.getInstance(1); break;
            case 3: ParticleSizeEditor.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window for adjusting the particles speed in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSpeedSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, GUIText.ParticleSpeedSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (seedOpt) {
            case 1: ParticleSpeedEditor.getInstance(0); break;
            case 2: ParticleSpeedEditor.getInstance(1); break;
            case 3: ParticleSpeedEditor.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window to set the SlideEditors particle size sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxParticleSizeSlider() {
        ParticleSlideEditor.ptsslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptsslider.getMaximum(),
                HeadingTag(3, "Enter Max Particle Size (Integer Only)"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the SlideEditors drag amount sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxDragSlider() {
        ParticleSlideEditor.ptdrslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptdrslider.getMaximum(),
                HeadingTag(3, "Enter Max Drag Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the SlideEditors fireworks sliders maximum.
     *
     * @see ParticleSlideEditor
     */
    public static void setMaxFireworksSlider() {
        ParticleSlideEditor.ptfrslider.setMaximum((int) minValueGuard(1, ParticleSlideEditor.ptfrslider.getMaximum(),
                HeadingTag(3, "Enter Max Fireworks Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    /**
     * Dialog window to set the radius of all particles in the ParticlesArray.
     */
    private static void particleSize() {
        float r = minValueGuard(0, .95f, HeadingTag(3, "Change Particle Size To (Integer or Float)"), OptionsMenu.frame);
        if (Particles.size() > 0) {
            for (int i = 0; i < Particles.size(); i++) Particles.get(i).radius = r;
        }
    }

    /**
     * Dialog window to set the cycle time for changing particle colors. If cycling colors is on, after a valid number (in
     * second) is entered, the current Timer is stopped and reset and then started with the new cycle time.
     */
    private static void cycleTimeOptions() {
        ENGINE_COLOR_CYCLE_RATE.setValue((int) minValueGuard(1, ENGINE_COLOR_CYCLE_RATE.value(),
                HeadingTag(3, "Enter Cycle Time (In Seconds)")));
        if (ENGINE_CYCLE_COLORS.value()) {
            ReactiveColorsRandomizer.stopCycle();
            ReactiveColorsRandomizer.startCycle();
        }
    }

    public static void windAmount() {
        FIREWORKS_WIND.setValue((int) minValueGuard(0, FIREWORKS_WIND.value(),
                HeadingTag(3, "Enter Wind Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    public static void baseLifeAmount() {
        PARTICLE_BASE_LIFE.setValue((int) minValueGuard(0, PARTICLE_BASE_LIFE.value(),
                HeadingTag(3, "Enter Base Life Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    public static void rfLifeAmount() {
        FIREWORKS_LIFE.setValue((int) minValueGuard(0, FIREWORKS_LIFE.value(),
                HeadingTag(3, "Enter Real Fireworks Life Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    public static void jitterAmount() {
        FIREWORKS_JITTER.setValue((int) minValueGuard(0, FIREWORKS_JITTER.value(),
                HeadingTag(3, "Enter Jitter Amount (Integer Only)"), ParticleSlideEditor.frame));
    }

    private static void particleDrag() {
        PARTICLE_DRAG_AMOUNT.setValue((int) minValueGuard(1, PARTICLE_DRAG_AMOUNT.value(),
                HeadingTag(3, "Enter Drag Amount (Integer Only)")));
    }

    private static void safetyAmountOptions() {
        ENGINE_SAFETY_AMOUNT.setValue((int) minValueGuard(0, ENGINE_SAFETY_AMOUNT.value(),
                HeadingTag(3, "Enter Safety Amount (Integer Only, Default: 120)")));
    }

    private static void particleFireworks() {
        FIREWORKS_AMOUNT.setValue((int) minValueGuard(1, FIREWORKS_AMOUNT.value(),
                HeadingTag(3, "Enter Fireworks Amount (Integer Only)")));
    }

    /**
     * Sets the Engines title based on the state of the Engine (paused or running).
     *
     * @see CMenuBar
     * @see System
     */
    public static void setEngineTitleState() {
        if (ENGINE_IS_PAUSED.value()) EFrame.setTitle(title + " - PAUSED");
        else EFrame.setTitle(title);
    }


    public static void createEngineInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(0, parent,
                (int) (d.width * .44), (int) (d.height * .55),
                "Particle Engine Instructions", GUIText.EngineInstructions);
    }

    public static void createGraphInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(1, parent,
                (int) (d.width * .37), (int) (d.height * .55),
                "Particle Graph Instructions", GUIText.ParticleGraphInstructions);
    }

    /**
     * Toggles the state of the Engine (paused or running).
     *
     * @see KeyboardHandler
     */
    public static void pauseSimulation() {
        ENGINE_IS_PAUSED.toggleValue();
        CMenuBar.updateState();
        setEngineTitleState();
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
            case 4: ParticleTypePicker.getInstance(0, "Particle Type Options"); break; //Regular Particle Type
            case 5: ColorUtility.setPlainColor(); break;
            case 6: gravitationOptions(); break;
            case 7: particleSizeSeedOptions(); break;
            case 8: particleSpeedSeedOptions(); break;
            case 9: realFireworks(); break;
            case 10: ParticleTypePicker.getInstance(1, "Firework Type Options"); break; //Fireworks Particle Type
            case 11: ReactiveColorsEditor.getInstance(); break;
            case 12: safetyAmountOptions(); break;
            case 13: cycleTimeOptions(); break;
            default: break;
        }
    }

    public static void updateEngineMode() {
        // Check whether a notification is being requested already
        // If it is then don't update or display until it's finished
        if (!Notifier.drawingNotification) {
            // Increment Mode
            ENGINE_MODE = getMode(ENGINE_MODE, 1);
            displayEngineMode();
            updateParticleModesRadios();
        }
    }

    public static <T extends Enum<T>> T getMode(T enumType, int sign) {
        T[] values = enumType.getDeclaringClass().getEnumConstants();
        if (sign > 0) return values[(enumType.ordinal() + 1) % values.length];
        else return values[(values.length + (enumType.ordinal() - 1)) % values.length];
    }

    public static void changeVPhysicsEditorMode(VModes.EditorModes editorMode){
        if (ENGINE_MODE == RAGDOLL && !Notifier.drawingNotification){
            VerletPhysicsEditor.EDITOR_MODE = editorMode;
            VerletPhysicsEditor.editorModesJComboBox.setSelectedItem(editorMode);
            Notifier.pushNotification(editorMode.name());
        }
    }

    /**
     * Handles the action in response to a key listener for the left arrow key.
     *
     * @see KeyboardHandler
     */
    public static void leftArrowFunction() {
        if (ENGINE_MODE == RAGDOLL) {
            if (!Notifier.drawingNotification) {
                VerletPhysicsEditor.CREATION_MODE = VModes.getMode(VerletPhysicsEditor.CREATION_MODE, -1);
                VerletPhysicsEditor.creationModesJComboBox.setSelectedItem(VerletPhysicsEditor.CREATION_MODE);
                displayParticleType();
            }
        }
        else {
            if (!Notifier.drawingNotification) {
                // Decrement
                PARTICLE_TYPE = getMode(PARTICLE_TYPE, -1);
                displayParticleType();
                updateParticleTypesRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the right arrow key.
     *
     * @see KeyboardHandler
     */
    public static void rightArrowFunction() {
        if (ENGINE_MODE == RAGDOLL) {
            if (!Notifier.drawingNotification) {
                VerletPhysicsEditor.CREATION_MODE = VModes.getMode(VerletPhysicsEditor.CREATION_MODE, 1);
                VerletPhysicsEditor.creationModesJComboBox.setSelectedItem(VerletPhysicsEditor.CREATION_MODE);
                displayParticleType();
            }
        }
        else {
            if (!Notifier.drawingNotification) {
                // Increment
                PARTICLE_TYPE = getMode(PARTICLE_TYPE, 1);
                displayParticleType();
                updateParticleTypesRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the up arrow key.
     *
     * @see KeyboardHandler
     */
    public static void upArrowFunction() {
        if (PARTICLE_TYPE == DUPLEX) {
            if (!Notifier.drawingNotification){
                TOGGLE_DUPLEX_MODE.toggleValue();
                if (TOGGLE_DUPLEX_MODE.value()) Notifier.pushNotification("Contain Mode");
                else Notifier.pushNotification("Repel Mode");
            }
        }
        else PARTICLE_DRAG_AMOUNT.increment();
    }

    public static void downArrowFunction() {
        PARTICLE_DRAG_AMOUNT.decrement();
        if (PARTICLE_DRAG_AMOUNT.value() < 1) PARTICLE_DRAG_AMOUNT.setValue(1);
    }

    /**
     * Empties all of the Engines ArrayLists.
     *
     * @see KeyboardHandler
     * @see CMenuBar
     */
    public static void clearParticleArrays() {
        if (ENGINE_MODE == RAGDOLL) {
            VSim.resetSelectedVertex();
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
     * Slices all Molecule(base class) ArrayList's sizes.
     *
     * @see KeyboardHandler
     * @see CMenuBar
     */
    public static void trimParticleArrays() {
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
        switch (ENGINE_MODE) {
            case NORMAL: Notifier.pushNotification("Normal Mode");       break;
            case MULTI: Notifier.pushNotification("Multi Mode");         break;
            case FIREWORKS: Notifier.pushNotification("Fireworks Mode"); break;
            case GRAPH: Notifier.pushNotification("Graph Mode");         break;
            case RAGDOLL: Notifier.pushNotification("Ragdoll Mode");     break;
            default: break;
        }
    }

    /**
     * Displays the particle type.
     *
     * @see CMenuBar
     */
    public static void displayParticleType() {
        if (ENGINE_MODE == RAGDOLL) {
            switch (VerletPhysicsEditor.CREATION_MODE) {
                case Point: Notifier.pushNotification("Point"); break;
                case Stick: Notifier.pushNotification("Stick"); break;
                case IKChain: Notifier.pushNotification("IKChain"); break;
                case Box: Notifier.pushNotification("Box"); break;
                case SolidMesh: Notifier.pushNotification("SolidMesh"); break;
                case ElasticMesh: Notifier.pushNotification("ElasticMesh"); break;
                case Cloth: Notifier.pushNotification("Cloth"); break;
                default: break;
            }
        }
        else {
            switch (PARTICLE_TYPE) {
                case PARTICLE: Notifier.pushNotification("Particle"); break;
                case GRAVITY_POINT: Notifier.pushNotification("Gravity Point"); break;
                case EMITTER: Notifier.pushNotification("Emitter"); break;
                case FLUX: Notifier.pushNotification("Flux"); break;
                case QED: Notifier.pushNotification("Q.E.D"); break;
                case ION: Notifier.pushNotification("Ion"); break;
                case BLACK_HOLE: Notifier.pushNotification("Black Hole"); break;
                case DUPLEX: Notifier.pushNotification("Duplex"); break;
                case PORTAL: Notifier.pushNotification("Portal"); break;
                default: break;
            }
        }
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
     * Returns whether the Engines cycle colors boolean is true or false.
     *
     * @see ReactiveColorsEditor
     */
    public static String getCycle() {
        return ENGINE_CYCLE_COLORS.value() ? "On" : "Off";
    }

    /**
     * Returns whether the Engines thinking particles boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getThinkingText() {
        return REACTIVE_PARTICLES_ENABLED.value() ? "Thinking Particles: On" : "Thinking Particles: Off";
    }

    /**
     * Returns whether the Engines mouse gravitation boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getMouseAttraction() {
        return GRAVITATE_TO_MOUSE.value() ? "Mouse Attraction: On" : "Mouse Attraction: Off";
    }

    /**
     * Returns whether the Engines particle friction boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getFrictionText() {
        return PARTICLE_FRICTION.value() ? "Particle Friction: On" : "Particle Friction: Off";
    }

    /**
     * Returns whether the Engines connect particles boolean is true or false and whether the ParticlesArray ArrayList.size() is
     * less than or equal to 100.
     *
     * @see StatsPanel
     */
    public static String getConnectText() {
        return CONNECT_PARTICLES.value() && Particles.size() <= 100 ? "On" : "Off";
    }

    /**
     * Returns the current particle mode in string representation as follows: 'Particle Mode: _____'.
     *
     * @see StatsPanel
     */
    public static String getModeText() {
        return "Particle Mode: " + ENGINE_MODE.name();
    }

    //Updates all particles in the Engines ArrayLists
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private static void updateParticlesArray() {for (int i = 0; i < Particles.size(); i++) Particles.get(i).update();}
    private static void updateGravityPointsArray() {for (int i = 0; i < GravityPoints.size(); i++) GravityPoints.get(i).update();}
    private static void updateEmitterArray() {for (int i = 0; i < Emitters.size(); i++) Emitters.get(i).update();}
    private static void updateFwParticlesArray() {for (int i = 0; i < Fireworks.size(); i++) Fireworks.get(i).update();}
    private static void updateFluxArray() {for (int i = 0; i < Fluxes.size(); i++) Fluxes.get(i).update();}
    private static void updateEraserArray() {for (int i = 0; i < Erasers.size(); i++) Erasers.get(i).update();}
    private static void updateQEDArray() {for (int i = 0; i < QEDs.size(); i++) QEDs.get(i).update();}
    private static void updateIonsArray() {for (int i = 0; i < Ions.size(); i++) Ions.get(i).update();}
    private static void updateBlackHoleArray() {for (int i = 0; i < BlackHoles.size(); i++) BlackHoles.get(i).update();}
    private static void updateDuplexArray() {for (int i = 0; i < Duplexes.size(); i++) Duplexes.get(i).update();}
    private static void updatePortalArray() {for (int i = 0; i < Portals.size(); i++) Portals.get(i).update();}

    //Renders all particles in the Engines ArrayLists
    //--------------------------------------------------------------------------------------------------------------------------------------//
    private static void renderParticlesArray() {for (int i = 0; i < Particles.size(); i++) Particles.get(i).render();}
    private static void renderGravityPointsArray() {for (int i = 0; i < GravityPoints.size(); i++) GravityPoints.get(i).render();}
    private static void renderEmitterArray() {for (int i = 0; i < Emitters.size(); i++) Emitters.get(i).render();}
    private static void renderFwParticlesArray() {for (int i = 0; i < Fireworks.size(); i++) Fireworks.get(i).render();}
    private static void renderFluxArray() {for (int i = 0; i < Fluxes.size(); i++) Fluxes.get(i).render();}
    private static void renderEraserArray() {for (int i = 0; i < Erasers.size(); i++) Erasers.get(i).render();}
    private static void renderQEDArray() {for (int i = 0; i < QEDs.size(); i++) QEDs.get(i).render();}
    private static void renderIonsArray() {for (int i = 0; i < Ions.size(); i++) Ions.get(i).render();}
    private static void renderBlackHoleArray() {for (int i = 0; i < BlackHoles.size(); i++) BlackHoles.get(i).render();}
    private static void renderDuplexArray() {for (int i = 0; i < Duplexes.size(); i++) Duplexes.get(i).render();}
    private static void renderPortalArray() {for (int i = 0; i < Portals.size(); i++) Portals.get(i).render();}
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Makes sure that the Engines fireworks mode does not cause an exception due to too many particle explosions
     * and makes sure that Ragdoll collisions stay under or equal to 285.
     */
    public static void safetyBooleanChecks() {
        PARTICLE_IS_SAFE_AMOUNT.setValue(Particles.size() <= ENGINE_SAFETY_AMOUNT.value());
        if (VSim.COLLISION_DETECTION) VSim.COLLISION_DETECTION = Vertices.size() <= VSim.MAX_COLLISIONS;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Updates all the particles in their respective ArrayLists.
     */
    public static void handleUpdates() {
        try {
            if (ENGINE_MODE != RAGDOLL) {
                updateParticlesArray();
                updateGravityPointsArray();
                updateFwParticlesArray();
                updateEmitterArray();
                updateFluxArray();
                updateEraserArray();
                updateQEDArray();
                updateIonsArray();
                updateBlackHoleArray();
                updateDuplexArray();
                updatePortalArray();
            }
        }
        catch (Exception e) {
            ExceptionLogger.append(e);}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Renders all the particles in their respective ArrayLists and updates + renders Ragdoll physics.
     */
    public static void handleRenders() {
        try {
            if (ENGINE_MODE == GRAPH)
                graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
            else
                graphics2D.translate(0, 0);

            if (ENGINE_MODE == RAGDOLL) {
                if (VSim.DEBUG_MODE) VSim.debugPhysics();
                Physics.update();
                Physics.render();
            }
            else {
                if (ENGINE_MODE == GRAPH){
                    drawMid();
                }
                renderParticlesArray();
                renderGravityPointsArray();
                renderFwParticlesArray();
                renderEmitterArray();
                renderFluxArray();
                renderEraserArray();
                renderQEDArray();
                renderIonsArray();
                renderBlackHoleArray();
                renderDuplexArray();
                renderPortalArray();
            }
        }
        catch (Exception e) {
            ExceptionLogger.append(e);}
    }
}