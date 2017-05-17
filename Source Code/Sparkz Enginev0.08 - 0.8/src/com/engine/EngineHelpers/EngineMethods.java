package com.engine.EngineHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.J8Helpers.Interfaces.EModes.*;
import static com.engine.Utilities.H5Wrapper.H;
import static com.engine.Utilities.InputWrapper.minValueGuard;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.JComponents.CMenuBar.*;
import com.engine.GUIWindows.*;
import com.engine.JComponents.CMenuBar;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.ParticleTypes.Particle;
import com.engine.Verlet.*;
import com.engine.ThinkingParticles.SCCycle;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.InputWrapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class EngineMethods {
    /**
     * Switches the particle mode depending on the int(mode) passed to the function.
     * @param e Mouse Event.
     * @see com.engine.InputHandlers.MMotionListener
     */
    public static void switchClickMode(MouseEvent e) {
        if (engineMode == NORMAL_MODE){
            if (particleType == PARTICLE)            ParticleModes.singleParticle(e);
            else if (particleType == GRAVITY_POINT)  ParticleModes.singleGravityPoint(e);
            else if (particleType == EMITTER)        ParticleModes.singleEmitter(e);
            else if (particleType == FLUX)           ParticleModes.singleSemtex(e);
            else if (particleType == QED)            ParticleModes.singleQED(e);
            else if (particleType == ION)            ParticleModes.singleIon(e);
            else if (particleType == BLACK_HOLE)     ParticleModes.singleBlackHole(e);
            else if (particleType == DUPLEX)         ParticleModes.singleDuplex(e);
            else if (particleType == PORTAL)         ParticleModes.singlePortal(e);
        }

        else if (engineMode == MULTI_MODE){
            if (particleType == PARTICLE)            ParticleModes.multiParticle(e);
            else if (particleType == GRAVITY_POINT)  ParticleModes.multiGravityPoint(e, 4);
            else if (particleType == EMITTER)        ParticleModes.singleEmitter(e);
            else if (particleType == FLUX)           ParticleModes.multiSemtex(e, 4);
            else if (particleType == QED)            ParticleModes.multiQED(e, 4);
            else if (particleType == ION)            ParticleModes.multiIon(e, 10);
            else if (particleType == BLACK_HOLE)     ParticleModes.singleBlackHole(e);
            else if (particleType == DUPLEX)         ParticleModes.singleDuplex(e);
            else if (particleType == PORTAL)         ParticleModes.singlePortal(e);
        }

        else if (engineMode == FIREWORKS_MODE) {mouseGravitation = false; ParticleModes.fireworksTarget(e);}
        else if (engineMode == RAGDOLL_MODE) {VPHandler.ragdollMode_ClickState(e);}
    }

    /**
     * Iterates through the ParticlesArray.
     * @param size sets each particles radius to the specified size
     * @see com.engine.GUIWindows.SlideEditor
     */
    public static void setParticleSize(int size) {for (int i = 0; i < ParticlesArray.size(); i++) {if (size >= 0) {ParticlesArray.get(i).radius = size;}}}

    /**
     * Increases each particles radius by .5
     * @see com.engine.InputHandlers.KHandler
     */
    public static void increaseParticleSize() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).radius += 0.5;}

    /**
     * Decreases each particles velocity x and velocity y by 60%
     * @see com.engine.InputHandlers.KHandler
     */
    public static void slowParticles() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).normalize(0.6);}

    /**
     * Multiplies each particles velocity x and velocity by 150%
     * @see com.engine.InputHandlers.KHandler
     */
    public static void scatterParticles() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).normalize(1.5);}

    /**
     * Decreases each particles radius by .5 and limits the particles minimum radius to .95
     * @see com.engine.InputHandlers.KHandler
     */
    public static void decreaseParticleSize(){
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            p.radius -= 0.5;
            if (p.radius < 1) p.radius = 0.95;
        }
    }

    /**
     * Toggles the Engines ability to smooth the graphics. Smooth graphics decrease performance but are appealing.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void toggleGraphicsSmoothing(){SMOOTH = toggle(SMOOTH);}

    /**
     * Handles the Engines graphics smoothing. If the Engines boolean to smooth graphics is true and the particle
     * mode is set to Ragdoll, then Engine applies graphical smoothing (Antialiasing)
     * @see com.engine.Main.Engine
     */
    public static void handleGraphicsSmoothing(){
        if (engineMode == RAGDOLL_MODE){
            if (SMOOTH){
                graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        }
    }

    /**
     * Updates the Engines Mouses' position each update.
     * @see com.engine.InputHandlers.MMotionListener
     */
    public static void updateMouse(MouseEvent e){
        Mouse.x = e.getX(); Mouse.y = e.getY();
        int newX = e.getX(), newY = e.getY();

        if(oldMouseX == -1)  {oldMouseX = newX; oldMouseY = newY; return;}

        pMouseX = Math.abs(oldMouseX - newX);
        pMouseY = Math.abs(oldMouseY - newY);

        oldMouseX = newX;
        oldMouseY = newY;
    }

    /**
     * A useful method to change a booleans value by negation.
     * @see com.engine.JComponents.CMenuBar
     * @see com.engine.InputHandlers.KHandler
     * @see ColorEditor
     * @see com.engine.Verlet.VSim
     */
    public static boolean toggle(boolean bool) {return !bool;}

    /**
     * @param amount Sets the Engines drag amount to the specified amount.
     * @see SlideEditor
     */
    public static void setDragAmount(int amount) {if (amount >= 0) dragAmount = amount;}

    /**
     * @param amount Sets the Engines fireworks amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksAmount(int amount) {if (amount >= 0) fireworksAmount = amount;}

    /**
     * @param amount Sets the Engines life amount to the specified amount
     * @see SlideEditor
     */
    public static void setLifeAmount(int amount) {if (amount >= 0) baseLife = amount;}

    /**
     * @param amount Sets the Engines wind amount to the specified amount
     * @see SlideEditor
     */
    public static void setWindAmount(int amount) {if (amount >= 0) fireworksWind = amount;}

    /**
     * @param amount Sets the Engines jitter amount to the specified amount
     * @see SlideEditor
     */
    public static void setJitterAmount(int amount) {if (amount >= 0) fireworksJitter = amount;}

    /**
     * @param amount Sets the Engines single click minimum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMin(int amount) {if (amount >= 0) singleClickSizeMinVal = amount;}

    /**
     * @param amount Sets the Engines single click maximum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMax(int amount) {if (amount >= 0) singleClickSizeMaxVal = amount;}

    /**
     * @param amount Sets the Engines fireworks minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMin(int amount) {if (amount >= 0) fireworksSizeMinVal = amount;}

    /**
     * @param amount Sets the Engines fireworks maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMax(int amount) {if (amount >= 0) fireworksSizeMaxVal = amount;}

    /**
     * @param amount Sets the Engines drag minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMin(int amount) {if (amount >= 0) dragSizeMinVal = amount;}

    /**
     * @param amount Sets the Engines drag maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMax(int amount) {if (amount >= 0) dragSizeMaxVal = amount;}

    /**
     * @param amount Sets the Engines single click speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSpeed(int amount) {if (amount >= 0) singleClickSpeedVal = amount;}

    /**
     * @param amount Sets the Engines fireworks speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSpeed(int amount) {if (amount >= 0) fireworksSpeedVal = amount;}

    /**
     * @param amount Sets the Engines drag speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSpeed(int amount) {if (amount >= 0) dragSpeedVal = amount;}

    /**
     * Sets the Engines particle friction boolean.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void toggleParticleFriction() {particleFriction = toggle(particleFriction);}

    /**
     * Sets the Engines mouse gravitation boolean.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void mouseGravitationToggle() {mouseGravitation = toggle(mouseGravitation);}

    /**
     * Sets the Engines connect particles boolean.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void connectParticlesMode() {connectParticles = toggle(connectParticles);}

    /**
     * Sets the Engines thinking particles boolean.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void thinkingParticlesMode() {thinkingParticles = toggle(thinkingParticles);}

    /**
     * Determines which gravitation mode to use for particles in the ParticlesArray ArrayList.
     */
    private static void gravitationOptions(){
        int gravityMode = (int) minValueGuard(0, particleGravitationMode, GUIText.particleGravitationOptions());
        switch (gravityMode) {
            case 0: particleGravitationMode = DEFAULT;              break;
            case 1: particleGravitationMode = COSINE_SINE;          break;
            case 2: particleGravitationMode = ARC_TANGENT;          break;
            case 3: particleGravitationMode = H_WAVE;               break;
            case 4: particleGravitationMode = V_WAVE;               break;
            case 5: particleGravitationMode = SPIRALS;              break;
            case 6: particleGravitationMode = PARTICLE_REPELLENT;   break;
            case 7: particleGravitationMode = ORGANIC; OrganicForces.getInstance(OptionsMenu.frame); break;
            default: break;
        }
        updateGravitationModesRadios();
    }

    /**
     * Dialog window for adjusting the fireworks options (Wind, Life, Jitter)
     */
    private static void realFireworks() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, H(3, GUIText.realFireworksOptions()), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = (InputWrapper.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
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
    private static void particleSizeSeedOptions(){
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, H(3, GUIText.particleSizeSeedOptions()), null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputWrapper.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (seedOpt) {
            case 1: ParticleSizeSeed.getInstance(0); break;
            case 2: ParticleSizeSeed.getInstance(1); break;
            case 3: ParticleSizeSeed.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window for adjusting the particles speed in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSpeedSeedOptions(){
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, H(3, GUIText.particleSpeedSeedOptions()), null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputWrapper.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (seedOpt) {
            case 1: ParticleSpeedSeed.getInstance(0); break;
            case 2: ParticleSpeedSeed.getInstance(1); break;
            case 3: ParticleSpeedSeed.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window to set the SlideEditors particle size sliders maximum.
     * @see SlideEditor
     */
    public static void setMaxParticleSizeSlider(){
        SlideEditor.ptsslider.setMaximum((int) minValueGuard(1, SlideEditor.ptsslider.getMaximum(), "Enter Max Particle Size (Integer Only)", SlideEditor.frame));}

    /**
     * Dialog window to set the SlideEditors drag amount sliders maximum.
     * @see SlideEditor
     */
    public static void setMaxDragSlider(){
        SlideEditor.ptdrslider.setMaximum((int) minValueGuard(1, SlideEditor.ptdrslider.getMaximum(), "Enter Max Drag Amount (Integer Only)", SlideEditor.frame));}

    /**
     * Dialog window to set the SlideEditors fireworks sliders maximum.
     * @see SlideEditor
     */
    public static void setMaxFireworksSlider(){
        SlideEditor.ptfrslider.setMaximum((int) minValueGuard(1, SlideEditor.ptfrslider.getMaximum(), "Enter Max Fireworks Amount (Integer Only)", SlideEditor.frame));}

    /**
     * Dialog window to set the radius of all particles in the ParticlesArray.
     */
    private static void particleSize(){
        double r = minValueGuard(0, .95, H(3, "Change Particle Size To (Integer or Double)"), OptionsMenu.frame);
        if (ParticlesArray.size() > 0) {
            for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).radius = r;
        }
    }

    /**
     * Dialog window to set the cycle time for changing particle colors. If cycling colors is on, after a valid number (in
     * second) is entered, the current Timer is stopped and reset and then started with the new cycle time.
     */
    private static void cycleTimeOptions(){
        cycleRate = (int) minValueGuard(1, cycleRate, "Enter Cycle Time (In Seconds)");
        if (cycleColors) {SCCycle.stopCycle(); SCCycle.startCycle();}
    }

    public static void windAmount() {
        fireworksWind = (int) minValueGuard(0, fireworksWind, "Enter Wind Amount (Integer Only)", SlideEditor.frame);}
    public static void baseLifeAmount() {baseLife = (int) minValueGuard(0, baseLife, "Enter Base Life Amount (Integer Only)", SlideEditor.frame);}
    public static void rfLifeAmount() {
        fireworksLife = (int) minValueGuard(0, fireworksLife, "Enter Real Fireworks Life Amount (Integer Only)", SlideEditor.frame);}
    public static void jitterAmount() {
        fireworksJitter = (int) minValueGuard(0, fireworksJitter, "Enter Jitter Amount (Integer Only)", SlideEditor.frame);}
    private static void particleDrag() {dragAmount = (int) minValueGuard(1, dragAmount, "Enter Drag Amount (Integer Only)");}
    private static void safetyAmountOptions() {safetyAmount = (int) minValueGuard(1, safetyAmount, "Enter Safety Amount (Integer Only)");}
    private static void particleFireworks() {fireworksAmount = (int) minValueGuard(1, fireworksAmount, "Enter Fireworks Amount (Integer Only)");}

    /**
     * Sets the Engines title based on the state of the Engine (paused or running).
     * @see CMenuBar
     * @see System
     */
    public static void setEngineTitleState(){if (isPaused) {EFrame.setTitle(title + " - PAUSED");} else {EFrame.setTitle(title);}}


    public static void createEngineInstructionsWindow(JFrame parent) {
        InstructionsWindow.getInstance(0, parent, (int) (width * .41), (int) (height * .55), "Particle Engine Instructions", GUIText.instructions());
    }

    public static void createGraphInstructionsWindow(JFrame parent) {
        InstructionsWindow.getInstance(1, parent, (int) (width * .41), (int) (height * .55), "Particle Graph Instructions", GUIText.particleGraphInstructions());
    }

    /**
     * Toggles the state of the Engine (paused or running).
     * @see com.engine.InputHandlers.KHandler
     */
    public static void pauseSimulation() {
        isPaused = toggle(isPaused); CMenuBar.updateState();
        if (isPaused) Notifier.getInstance("Paused", 240, 85);
        else Notifier.getInstance("Resume", 240, 85);
        setEngineTitleState();
    }

    /**
     * Different options for the Engines Options Menu.
     * @see OptionsMenu
     */
    public static void getOptions(int option) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception x){x.printStackTrace();}
        switch (option) {
            case 1: particleSize();             break;
            case 2: particleDrag();             break;
            case 3: particleFireworks();        break;
            case 4: ParticleTypeUI.getInstance(0, "Particle Type Options"); break; //Regular Particle Type
            case 5: ColorUtility.setColor();  break;
            case 6: gravitationOptions();       break;
            case 7: particleSizeSeedOptions();  break;
            case 8: particleSpeedSeedOptions(); break;
            case 9: realFireworks();            break;
            case 10: ParticleTypeUI.getInstance(1, "Firework Type Options"); break; //Fireworks Particle Type
            case 11: ColorEditor.getInstance(); break;
            case 12: safetyAmountOptions();     break;
            case 13: cycleTimeOptions();        break;
            default: break;
        }
    }

    public static void updateEngineMode() {
        engineMode++;
        if (engineMode > RAGDOLL_MODE) {
            engineMode = NORMAL_MODE;}
        displayEngineMode();
        updateParticleModesRadios();
    }

    /**
     * Handles the action in response to a key listener for the left arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void leftArrowFunction(){
        if (engineMode == RAGDOLL_MODE) {
            VPHandler.MODE--;
            if (VPHandler.MODE < 0) VPHandler.MODE = VPHandler.MAX_MODE;
            displayParticleType();
        }
        else {
            particleType--;
            if (particleType < PARTICLE) particleType = PORTAL;
            displayParticleType();
            updateParticleTypesRadios();
        }
    }

    /**
     * Handles the action in response to a key listener for the right arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void rightArrowFunction(){
        if (engineMode == RAGDOLL_MODE) {
            VPHandler.MODE++;
            if (VPHandler.MODE > VPHandler.MAX_MODE) VPHandler.MODE = 0;
            displayParticleType();
        }
        else {
            particleType++;
            if (particleType > PORTAL) particleType = PARTICLE;
            displayParticleType();
            updateParticleTypesRadios();
        }
    }

    /**
     * Handles the action in response to a key listener for the up arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void upArrowFunction(){
        if (particleType == DUPLEX){
            DUPLEXMODE = toggle(DUPLEXMODE);
            if (DUPLEXMODE) Notifier.getInstance("Contain Mode", 380, 85);
            else Notifier.getInstance("Repel Mode", 350, 85);
        }
        else dragAmount += 1;
    }

    public static void downArrowFunction() {
        dragAmount--;
        if (dragAmount <= 1) dragAmount = 1;
    }

    /**
     * Empties all of the Engines ArrayLists.
     * @see com.engine.InputHandlers.KHandler
     * @see CMenuBar
     */
    public static void clearParticleArrays(){
        try {
            ParticlesArray.clear();
            GravityPointsArray.clear();
            EmitterArray.clear();
            FireworksArray.clear();
            FluxArray.clear();
            EraserArray.clear();
            QEDArray.clear();
            IonArray.clear();
            BlackHoleArray.clear();
            DuplexArray.clear();
            PortalArray.clear();
            POINTS.clear();
            //Make sure to delete reference to selected point for drawing constraints
            VSim.selectedPoint = null;
        }
        catch (Exception e) {EException.append(e);}
    }

    /**
     * Slices all Molecule(base class) ArrayList's sizes.
     * @see com.engine.InputHandlers.KHandler
     * @see CMenuBar
     */
    public static void trimParticleArrays(){
        try {
            synchronized (ParticlesArray) {for (int i = 0; i < ParticlesArray.size(); i++)     ParticlesArray.remove(i);}
            synchronized (GravityPointsArray) {for (int i = 0; i < GravityPointsArray.size(); i++) GravityPointsArray.remove(i);}
            synchronized (FireworksArray) {for (int i = 0; i < FireworksArray.size(); i++)     FireworksArray.remove(i);}
            synchronized (EmitterArray) {for (int i = 0; i < EmitterArray.size(); i++)       EmitterArray.remove(i);}
            synchronized (FluxArray) {for (int i = 0; i < FluxArray.size(); i++)          FluxArray.remove(i);}
            synchronized (EraserArray) {for (int i = 0; i < EraserArray.size(); i++)        EraserArray.remove(i);}
            synchronized (QEDArray) {for (int i = 0; i < QEDArray.size(); i++)           QEDArray.remove(i);}
            synchronized (IonArray) {for (int i = 0; i < IonArray.size(); i++)           IonArray.remove(i);}
            synchronized (BlackHoleArray) {for (int i = 0; i < BlackHoleArray.size(); i++)     BlackHoleArray.remove(i);}
            synchronized (DuplexArray) {for (int i = 0; i < DuplexArray.size(); i++)        DuplexArray.remove(i);}
            synchronized (PortalArray) {for (int i = 0; i < PortalArray.size(); i++)        PortalArray.remove(i);}
        }
        catch (Exception ex) {EException.append(ex);}
    }

    /**
     * Displays the particle mode.
     * @see CMenuBar
     * @see com.engine.InputHandlers.KHandler
     */
    public static void displayEngineMode(){
        switch (engineMode) {
            case 0: Notifier.getInstance("Normal Mode",     380, 80); break;
            case 1: Notifier.getInstance("Multi Mode",      320, 80); break;
            case 2: Notifier.getInstance("Fireworks Mode",  470, 80); break;
            case 3: Notifier.getInstance("Graph Mode",      350, 80); break;
            case 4: Notifier.getInstance("Ragdoll Mode",    390, 80); break;
            default: break;
        }
    }

    /**
     * Displays the particle type.
     * @see CMenuBar
     */
    public static void displayParticleType(){
        if (engineMode == RAGDOLL_MODE) {
            switch (VPHandler.MODE){
                case 0: Notifier.getInstance("Point",       150, 80); break;
                case 1: Notifier.getInstance("Stick",       140, 80); break;
                case 2: Notifier.getInstance("IK Chain",    240, 80); break;
                case 3: Notifier.getInstance("Box",         120, 80); break;
                case 4: Notifier.getInstance("Solid Mesh",  290, 80); break;
                case 5: Notifier.getInstance("Elastic Mesh",345, 80); break;
                case 6: Notifier.getInstance("Cloth",       140, 80); break;
                default: break;
            }
        }
        else {
            switch (particleType){
                case 0: Notifier.getInstance("Particle",      200, 80); break;
                case 1: Notifier.getInstance("Gravity Point", 375, 80); break;
                case 2: Notifier.getInstance("Emitter",       200, 80); break;
                case 3: Notifier.getInstance("Flux",          140, 80); break;
                case 4: Notifier.getInstance("Q.E.D",         180, 80); break;
                case 5: Notifier.getInstance("Ion",           140, 80); break;
                case 6: Notifier.getInstance("Black Hole",    290, 80); break;
                case 7: Notifier.getInstance("Duplex",        190, 80); break;
                case 8: Notifier.getInstance("Portal",        170, 80); break;
                default: break;
            }
        }
    }

    /**
     * Draws a horizontal line with a mid point for the Engines Graph mode.
     */
    public static void drawMid() {
        int neg_W = -canvas.getWidth() / 2, pos_W = canvas.getWidth() / 2;
        graphics2D.setColor(Color.gray);
        graphics2D.drawLine(neg_W, 0, pos_W, 0);
        graphics2D.drawLine(0, 0, 0, -20);
        graphics2D.drawLine(0, 0, 0, 20);
    }

    /**
     * Returns whether the Engines cycle colors boolean is true or false.
     * @see ColorEditor
     */
    public static String getCycle() {return (cycleColors) ? "On" : "Off";}

    /**
     * Returns whether the Engines thinking particles boolean is true or false.
     * @see StatsPanel
     */
    public static String getThinkingText(){return (thinkingParticles) ? "Thinking Particles: On" : "Thinking Particles: Off";}

    /**
     * Returns whether the Engines mouse gravitation boolean is true or false.
     * @see StatsPanel
     */
    public static String getMouseAttraction(){return (mouseGravitation) ? "Mouse Attraction: On" : "Mouse Attraction: Off";}

    /**
     * Returns whether the Engines particle friction boolean is true or false.
     * @see StatsPanel
     */
    public static String getFrictionText(){ return (particleFriction) ? "Particle Friction: On" : "Particle Friction: Off";}

    /**
     * Returns whether the Engines connect particles boolean is true or false and whether the ParticlesArray ArrayList.size() is
     * less than or equal to 100.
     * @see StatsPanel
     */
    public static String getConnectText(){return (connectParticles && ParticlesArray.size() <= 100) ? "On" : "Off";}

    /**
     * Returns the current particle mode in string representation as follows: 'Particle Mode: _____'.
     * @see StatsPanel
     */
    public static String getModeText(){
        if (engineMode == NORMAL_MODE) return "Particle Mode: Normal"; else if (engineMode == MULTI_MODE) return "Particle Mode: Multi";
        else if (engineMode == FIREWORKS_MODE) return "Particle Mode: Fireworks"; else if (engineMode == GRAPH_MODE) return "Particle Mode: Graph";
        else return "Particle Mode: Ragdoll";
    }

    //Updates all particles in the Engines ArrayLists
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private static void updateParticlesArray() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).update();}
    private static void updateGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) GravityPointsArray.get(i).update();}
    private static void updateEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) EmitterArray.get(i).update();}
    private static void updateFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) FireworksArray.get(i).update();}
    private static void updateFluxArray() {for (int i = 0; i < FluxArray.size(); i++) FluxArray.get(i).update();}
    private static void updateObstructArray() {for (int i = 0; i < EraserArray.size(); i++) EraserArray.get(i).update();}
    private static void updateQEDArray() {for (int i = 0; i < QEDArray.size(); i++) QEDArray.get(i).update();}
    private static void updateIonsArray() {for (int i = 0; i < IonArray.size(); i++) IonArray.get(i).update();}
    private static void updateBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) BlackHoleArray.get(i).update();}
    private static void updateDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) DuplexArray.get(i).update();}
    private static void updatePortalArray() {for (int i = 0; i < PortalArray.size(); i++) PortalArray.get(i).update();}

    //Renders all particles in the Engines ArrayLists
    //--------------------------------------------------------------------------------------------------------------------------------------//
    private static void renderParticlesArray() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).render();}
    private static void renderGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) GravityPointsArray.get(i).render();}
    private static void renderEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) EmitterArray.get(i).render();}
    private static void renderFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) FireworksArray.get(i).render();}
    private static void renderFluxArray() {for (int i = 0; i < FluxArray.size(); i++) FluxArray.get(i).render();}
    private static void renderObstructArray() {for (int i = 0; i < EraserArray.size(); i++) EraserArray.get(i).render();}
    private static void renderQEDArray() {for (int i = 0; i < QEDArray.size(); i++) QEDArray.get(i).render();}
    private static void renderIonsArray() {for (int i = 0; i < IonArray.size(); i++) IonArray.get(i).render();}
    private static void renderBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) BlackHoleArray.get(i).render();}
    private static void renderDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) DuplexArray.get(i).render();}
    private static void renderPortalArray() {for (int i = 0; i < PortalArray.size(); i++) PortalArray.get(i).render();}
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /**
     * Makes sure that the Engines fireworks mode does not cause an exception due to too many particle explosions
     * and makes sure that Ragdoll collisions stay under or equal to 285.
     */
    public static void safetyBooleanChecks(){
        isSafeAmount = ParticlesArray.size() <= safetyAmount;
        if (VSim.COLLISION_DETECTION){VSim.COLLISION_DETECTION = POINTS.size() <= VSim.MAX_COLLISIONS;}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /**
     * Updates all the particles in their respective ArrayLists.
     */
    public static void handleUpdates() {
        try{
            if (engineMode != RAGDOLL_MODE) {
                updateParticlesArray(); updateGravityPointsArray(); updateFwParticlesArray();
                updateEmitterArray(); updateFluxArray(); updateObstructArray();
                updateQEDArray(); updateIonsArray(); updateBlackHoleArray();
                updateDuplexArray(); updatePortalArray();
            }
        } catch (Exception e){EException.append(e);}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /**
     * Renders all the particles in their respective ArrayLists and updates + renders Ragdoll physics.
     */
    public static void handleRenders() {
        try {
            if (engineMode == RAGDOLL_MODE) {
                Physics.update(); Physics.render(); VSim.debugPhysics();
            }
            else {
                if (engineMode == GRAPH_MODE) drawMid();
                renderParticlesArray(); renderGravityPointsArray(); renderFwParticlesArray();
                renderEmitterArray(); renderFluxArray(); renderObstructArray();
                renderQEDArray(); renderIonsArray(); renderBlackHoleArray();
                renderDuplexArray(); renderPortalArray();
            }
        } catch (Exception e){EException.append(e);}
    }
}