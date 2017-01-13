package com.engine.EngineHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Utilities.H5Wrapper.H;
import static com.engine.Utilities.InputWrapper.minValueGuard;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.Point.displayPointArraySize;
import static com.engine.JComponents.CMenuBar.*;
import com.engine.GUIWindows.*;
import com.engine.JComponents.CMenuBar;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.Verlet.*;
import com.engine.ThinkingParticles.SCCycle;
import com.engine.Utilities.ColorConverter;
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
        if (switchMode == 0)
        {
            if (particleType == 0) ParticleModes.singleParticle(e);
            if (particleType == 1) ParticleModes.singleGravityPoint(e);
            if (particleType == 2) ParticleModes.singleEmitter(e);
            if (particleType == 3) ParticleModes.singleSemtex(e);
            if (particleType == 4) ParticleModes.singleQED(e);
            if (particleType == 5) ParticleModes.singleIon(e);
            if (particleType == 6) ParticleModes.singleBlackHole(e);
            if (particleType == 7) ParticleModes.singleDuplex(e);
            if (particleType == 8) ParticleModes.singlePortal(e);
        }

        else if (switchMode == 1)
        {
            if (particleType == 0) ParticleModes.multiParticle(e);
            if (particleType == 1) ParticleModes.multiGravityPoint(e, 4);
            if (particleType == 2) ParticleModes.singleEmitter(e);
            if (particleType == 3) ParticleModes.multiSemtex(e, 5);
            if (particleType == 4) ParticleModes.multiQED(e, 4);
            if (particleType == 5) ParticleModes.multiIon(e, 10);
            if (particleType == 6) ParticleModes.singleBlackHole(e);
            if (particleType == 7) ParticleModes.singleDuplex(e);
            if (particleType == 8) ParticleModes.singlePortal(e);
        }

        else if (switchMode == 2) {mouseGravitation = false; ParticleModes.fireworksTarget(e);}
        else if (switchMode == 4) {VPHandler.handleRagdollMode(e);}
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
    public static void increaseParticleSize() {for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.get(i).radius += 0.5;}}

    /**
     * Decreases each particles velocity x and velocity y by 60%
     * @see com.engine.InputHandlers.KHandler
     */
    public static void slowParticles() {for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.get(i).normalize(0.6);}}

    /**
     * Multiplies each particles velocity x and velocity by 150%
     * @see com.engine.InputHandlers.KHandler
     */
    public static void scatterParticles() {for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.get(i).normalize(1.5);}}

    /**
     * Decreases each particles radius by .5 and limits the particles minimum radius to .99.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void decreaseParticleSize(){
        for (int i = 0; i < ParticlesArray.size(); i++) {
            ParticlesArray.get(i).radius -= 0.5;
            if (ParticlesArray.get(i).radius < 1) {ParticlesArray.get(i).radius = 0.99;}
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
        if (SMOOTH){
            if (switchMode == 4) graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
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
     * @see com.engine.GUIWindows.ParticleColor
     * @see com.engine.Verlet.VSim
     */
    public static boolean toggle(boolean bool) {return !bool;}

    /**
     * @param amount Sets the Engines drag amount to the specified amount.
     * @see SlideEditor
     */
    public static void setDragAmount(int amount) {if (amount >= 0) {dragAmount = amount;}}

    /**
     * @param amount Sets the Engines fireworks amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksAmount(int amount) {if (amount >= 0) {fireworksAmount = amount;}}

    /**
     * @param amount Sets the Engines life amount to the specified amount
     * @see SlideEditor
     */
    public static void setLifeAmount(int amount) {if (amount >= 0) {baseLife = amount;}}

    /**
     * @param amount Sets the Engines wind amount to the specified amount
     * @see SlideEditor
     */
    public static void setWindAmount(int amount) {if (amount >= 0) {rfWind = amount;}}

    /**
     * @param amount Sets the Engines jitter amount to the specified amount
     * @see SlideEditor
     */
    public static void setJitterAmount(int amount) {if (amount >= 0) {rfJitter = amount;}}

    /**
     * @param amount Sets the Engines single click minimum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMin(int amount) {if (amount >= 0) {singleClickSizeMinVal = amount;}}

    /**
     * @param amount Sets the Engines single click maximum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMax(int amount) {if (amount >= 0) {singleClickSizeMaxVal = amount;}}

    /**
     * @param amount Sets the Engines fireworks minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMin(int amount) {if (amount >= 0) {fireworksSizeMinVal = amount;}}

    /**
     * @param amount Sets the Engines fireworks maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMax(int amount) {if (amount >= 0) {fireworksSizeMaxVal = amount;}}

    /**
     * @param amount Sets the Engines drag minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMin(int amount) {if (amount >= 0) {dragSizeMinVal = amount;}}

    /**
     * @param amount Sets the Engines drag maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMax(int amount) {if (amount >= 0) {dragSizeMaxVal = amount;}}

    /**
     * @param amount Sets the Engines single click speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSpeed(int amount) {if (amount >= 0) {singleClickSpeedVal = amount;}}

    /**
     * @param amount Sets the Engines fireworks speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSpeed(int amount) {if (amount >= 0) {fireworksSpeedVal = amount;}}

    /**
     * @param amount Sets the Engines drag speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSpeed(int amount) {if (amount >= 0) {dragSpeedVal = amount;}}

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
        int gravityMode = (int) minValueGuard(0, ptGravitationInt, GUIText.particleGravitationOptions());
        switch (gravityMode)
        {
            case 0: ptGravitationInt = 0; break;
            case 1: ptGravitationInt = 1; break;
            case 2: ptGravitationInt = 2; break;
            case 3: ptGravitationInt = 3; break;
            case 4: ptGravitationInt = 4; break;
            case 5: ptGravitationInt = 5; break;
            case 6: ptGravitationInt = 6; break;
            case 7: ptGravitationInt = 7; OrganicForces.getInstance(OptionsMenu.frame); break;
            default: ptGravitationInt = 0; break;
        }
        updateGravitationModesRadios();
    }

    /**
     * Dialog window for adjusting the fireworks options (Wind, Life, Jitter)
     */
    private static void realFireworks() {
        String input = JOptionPane.showInputDialog(OptionsMenu.frame, H(3, GUIText.realFireworksOptions()), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = (InputWrapper.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (rfoInt)
        {
            case 1: windAmount(); break;
            case 2: baseLifeAmount(); break;
            case 3: jitterAmount(); break;
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
        switch (seedOpt)
        {
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
        switch (seedOpt)
        {
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
        double r = minValueGuard(0, .9, H(3, "Change Particle Size To (Integer or Double)"), OptionsMenu.frame);
        if (ParticlesArray.size() > 0) {
            for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.get(i).radius = r;}
        }
    }

    /**
     * Dialog window to set the cycle time for changing particle colors. If cycling colors is on, after a valid number (in
     * second) is entered, the current Timer is stopped and reset and then started with the new cycle time.
     */
    private static void cycleTimeOptions(){cycleRate = (int) minValueGuard(1, cycleRate, "Enter Cycle Time (In Seconds)");
        if (cycleColors) {SCCycle.stopCycle(); SCCycle.startCycle();}
    }

    // TODO: 3/27/2016 Check inputWrapper minValueGuard does not cause rounding problems
    public static void windAmount() {rfWind = (int) minValueGuard(0, rfWind, "Enter Wind Amount (Integer Only)", SlideEditor.frame);}
    public static void baseLifeAmount() {baseLife = (int) minValueGuard(0, baseLife, "Enter Base Life Amount (Integer Only)", SlideEditor.frame);}
    public static void rfLifeAmount() {rfLife = (int) minValueGuard(0, rfLife, "Enter Real Fireworks Life Amount (Integer Only)", SlideEditor.frame);}
    public static void jitterAmount() {rfJitter = (int) minValueGuard(0, rfJitter, "Enter Jitter Amount (Integer Only)", SlideEditor.frame);}
    //
    private static void particleDrag() {dragAmount = (int) minValueGuard(1, dragAmount, "Enter Drag Amount (Integer Only)");}
    private static void safetyAmountOptions() {safetyAmount = (int) minValueGuard(1, safetyAmount, "Enter Safety Amount (Integer Only)");}
    private static void particleFireworks() {fireworksAmount = (int) minValueGuard(1, fireworksAmount, "Enter Fireworks Amount (Integer Only)");}

    /**
     * Sets the Engines title based on the state of the Engine (paused or running).
     * @see CMenuBar
     * @see System
     */
    public static void setEngineTitleState(){if (isPaused) {EFrame.setTitle(title + " - PAUSED");} else {EFrame.setTitle(title);}}

    /**
     * Toggles the state of the Engine (paused or running).
     * @see com.engine.InputHandlers.KHandler
     */
    public static void pauseSimulation() {
        isPaused = toggle(isPaused); CMenuBar.updateState();
        if (isPaused) {SWindow.getInstance("Paused", 240, 85);} else {SWindow.getInstance("Resume", 240, 85);} setEngineTitleState();
    }

    /**
     * Different options for the Engines Options Menu.
     * @see OptionsMenu
     */
    public static void getOptions(int option) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception x){x.printStackTrace();}
        switch (option) {
            case 1: particleSize(); break;
            case 2: particleDrag(); break;
            case 3: particleFireworks(); break;
            case 4: ParticleTypeUI.getInstance(0); break;
            case 5: ColorConverter.setColor(); break;
            case 6: gravitationOptions(); break;
            case 7: particleSizeSeedOptions(); break;
            case 8: particleSpeedSeedOptions(); break;
            case 9: realFireworks(); break;
            case 10: ParticleTypeUI.getInstance(1); break;
            case 11: ParticleColor.getInstance(); break;
            case 12: safetyAmountOptions(); break;
            case 13: cycleTimeOptions(); break;
            default: break;
        }
    }

    public static void updateEngineMode() {
        switchMode++;
        if (switchMode > 4) {switchMode = 0;}
        displayEngineMode();
        updateParticleModesRadios();
    }

    /**
     * Handles the action in response to a key listener for the left arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void leftArrowFunction(){
        if (switchMode == 4) {
            VPHandler.MODE--;
            if (VPHandler.MODE < 0) {VPHandler.MODE = VPHandler.MAX_MODE;}
            displayParticleType();
        }
        else {
            particleType--;
            if (particleType < 0) {particleType = maxParticleType;}
            displayParticleType();
            updateParticleTypesRadios();
        }
    }

    /**
     * Handles the action in response to a key listener for the right arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void rightArrowFunction(){
        if (switchMode == 4) {
            VPHandler.MODE++;
            if (VPHandler.MODE > VPHandler.MAX_MODE) {VPHandler.MODE = 0;}
            displayParticleType();
        }
        else {
            particleType++;
            if (particleType > maxParticleType) {particleType = 0;}
            displayParticleType();
            updateParticleTypesRadios();
        }
    }

    /**
     * Handles the action in response to a key listener for the up arrow key.
     * @see com.engine.InputHandlers.KHandler
     */
    public static void upArrowFunction(){
        if (particleType == 7){DUPLEXMODE = toggle(DUPLEXMODE);
            if (DUPLEXMODE) {SWindow.getInstance("Contain Mode", 380, 85);}
            else {SWindow.getInstance("Repel Mode", 350, 85);}} else {dragAmount+=1;}
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
        try
        {
            ParticlesArray.clear(); GravityPointsArray.clear();
            EmitterArray.clear(); FireworksArray.clear();
            FluxArray.clear(); EraserArray.clear();
            QEDArray.clear(); IonArray.clear();
            BlackHoleArray.clear(); DuplexArray.clear();
            PortalArray.clear(); POINTS.clear();
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
            for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.remove(i);}
            for (int i = 0; i < GravityPointsArray.size(); i++) {GravityPointsArray.remove(i);}
            for (int i = 0; i < FireworksArray.size(); i++) {FireworksArray.remove(i);}
            for (int i = 0; i < EmitterArray.size(); i++) {EmitterArray.remove(i);}
            for (int i = 0; i < FluxArray.size(); i++) {FluxArray.remove(i);}
            for (int i = 0; i < EraserArray.size(); i++) {EraserArray.remove(i);}
            for (int i = 0; i < QEDArray.size(); i++) {QEDArray.remove(i);}
            for (int i = 0; i < IonArray.size(); i++) {IonArray.remove(i);}
            for (int i = 0; i < BlackHoleArray.size(); i++) {BlackHoleArray.remove(i);}
            for (int i = 0; i < DuplexArray.size(); i++) {DuplexArray.remove(i);}
            for (int i = 0; i < PortalArray.size(); i++) {PortalArray.remove(i);}
        } catch (Exception ex) {EException.append(ex);}
    }

    /**
     * Displays the particle mode.
     * @see CMenuBar
     * @see com.engine.InputHandlers.KHandler
     */
    public static void displayEngineMode(){
        switch (switchMode)
        {
            case 0: SWindow.getInstance("Normal Mode", 380, 80); break;
            case 1: SWindow.getInstance("Multi Mode", 320, 80); break;
            case 2: SWindow.getInstance("Fireworks Mode", 470, 80); break;
            case 3: SWindow.getInstance("Graph Mode", 350, 80); break;
            case 4: SWindow.getInstance("Ragdoll Mode", 390, 80); break;
            default: break;
        }
    }

    /**
     * Displays the particle type.
     * @see CMenuBar
     */
    public static void displayParticleType(){
        if (switchMode == 4)
        {
            switch (VPHandler.MODE){
                case 0: SWindow.getInstance("Point", 150, 80); break;
                case 1: SWindow.getInstance("Stick", 140, 80); break;
                case 2: SWindow.getInstance("IK Chain", 240, 80); break;
                case 3: SWindow.getInstance("Box", 120, 80); break;
                case 4: SWindow.getInstance("Solid Mesh", 290, 80); break;
                case 5: SWindow.getInstance("Elastic Mesh", 345, 80); break;
                case 6: SWindow.getInstance("Cloth", 140, 80); break;
                default: break;
            }
        }

        else
        {
            switch (particleType){
                case 0: SWindow.getInstance("Particle", 200, 80); break;
                case 1: SWindow.getInstance("Gravity Point", 375, 80); break;
                case 2: SWindow.getInstance("Emitter", 200, 80); break;
                case 3: SWindow.getInstance("Flux", 140, 80); break;
                case 4: SWindow.getInstance("Q.E.D", 180, 80); break;
                case 5: SWindow.getInstance("Ion", 140, 80); break;
                case 6: SWindow.getInstance("Black Hole", 290, 80); break;
                case 7: SWindow.getInstance("Duplex", 190, 80); break;
                case 8: SWindow.getInstance("Portal", 170, 80); break;
                default: break;
            }
        }
    }

    /**
     * Draws a horizontal line with a mid point for the Engines Graph mode.
     */
    public static void drawMid() {
        int neg_W = -canvas.getWidth() / 2, pos_W = canvas.getWidth() / 2;
        graphics2D.setColor(Color.gray); graphics2D.drawLine(neg_W, 0, pos_W, 0);
        graphics2D.drawLine(0, 0, 0, -20); graphics2D.drawLine(0, 0, 0, 20);
    }

    /**
     * Returns whether the Engines cycle colors boolean is true or false.
     * @see ParticleColor
     */
    public static String getCycle() {if (cycleColors) {return "On";} else return "Off";}

    /**
     * Returns whether the Engines thinking particles boolean is true or false.
     * @see StatsPanel
     */
    public static String getThinkingText(){if (thinkingParticles) return "Thinking Particles: On"; else return "Thinking Particles: Off";}

    /**
     * Returns whether the Engines mouse gravitation boolean is true or false.
     * @see StatsPanel
     */
    public static String getMouseAttraction(){if (mouseGravitation) return  "Mouse Attraction: On"; else return "Mouse Attraction: Off";}

    /**
     * Returns whether the Engines particle friction boolean is true or false.
     * @see StatsPanel
     */
    public static String getFrictionText(){if (particleFriction) return  "Particle Friction: On"; else return "Particle Friction: Off";}

    /**
     * Returns whether the Engines connect particles boolean is true or false and whether the ParticlesArray ArrayList.size() is
     * less than or equal to 100.
     * @see StatsPanel
     */
    public static String getConnectText(){if (connectParticles && ParticlesArray.size() <= 100) return "Link Mode: On"; else return "Link Mode: Off";}

    /**
     * Returns the current particle mode in string representation as follows: 'Particle Mode: _____'.
     * @see StatsPanel
     */
    public static String getModeText(){
        if (switchMode == 0) return "Particle Mode: Normal"; else if (switchMode == 1) return "Particle Mode: Multi";
        else if (switchMode == 2) return "Particle Mode: Fireworks"; else if (switchMode == 3) return "Particle Mode: Graph";
        else return "Particle Mode: Ragdoll";
    }

    //Updates all particles in the Engines ArrayLists
    //---------------------------------------------------------------------------------------------------------------------------------------//
    public static void updateParticlesArray(){for (int i = 0; i < ParticlesArray.size(); i++){ParticlesArray.get(i).update();}}
    public static void updateGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) {GravityPointsArray.get(i).update();}}
    public static void updateEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) {EmitterArray.get(i).update();}}
    public static void updateFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) {FireworksArray.get(i).update();}}
    public static void updateFluxArray() {for (int i = 0; i < FluxArray.size(); i++) {FluxArray.get(i).update();}}
    public static void updateObstructArray() {for (int i = 0; i < EraserArray.size(); i++) {EraserArray.get(i).update();}}
    public static void updateQEDArray() {for (int i = 0; i < QEDArray.size(); i++) {QEDArray.get(i).update();}}
    public static void updateIonsArray() {for (int i = 0; i < IonArray.size(); i++) {IonArray.get(i).update();}}
    public static void updateBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) {BlackHoleArray.get(i).update();}}
    public static void updateDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) {DuplexArray.get(i).update();}}
    public static void updatePortalArray() {for (int i = 0; i < PortalArray.size(); i++) {PortalArray.get(i).update();}}

    //Renders all particles in the Engines ArrayLists
    //---------------------------------------------------------------------------------------------------------------------------------------//
    public static void renderParticlesArray() {for (int i = 0; i < ParticlesArray.size(); i++) {ParticlesArray.get(i).render();}}
    public static void renderGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) {GravityPointsArray.get(i).render();}}
    public static void renderEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) {EmitterArray.get(i).render();}}
    public static void renderFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) {FireworksArray.get(i).render();}}
    public static void renderFluxArray() {for (int i = 0; i < FluxArray.size(); i++) {FluxArray.get(i).render();}}
    public static void renderObstructArray() {for (int i = 0; i < EraserArray.size(); i++) {EraserArray.get(i).render();}}
    public static void renderQEDArray() {for (int i = 0; i < QEDArray.size(); i++) {QEDArray.get(i).render();}}
    public static void renderIonsArray() {for (int i = 0; i < IonArray.size(); i++) {IonArray.get(i).render();}}
    public static void renderBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) {BlackHoleArray.get(i).render();}}
    public static void renderDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) {DuplexArray.get(i).render();}}
    public static void renderPortalArray() {for (int i = 0; i < PortalArray.size(); i++) {PortalArray.get(i).render();}}
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
            if (switchMode != 4) {
                updateParticlesArray(); updateGravityPointsArray(); updateFwParticlesArray();
                updateEmitterArray(); updateFluxArray(); updateObstructArray();
                updateQEDArray(); updateIonsArray(); updateBlackHoleArray();
                updateDuplexArray(); updatePortalArray();
            }
        }catch (IndexOutOfBoundsException e){EException.append(e);}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /**
     * Renders all the particles in their respective ArrayLists and updates + renders Ragdoll physics.
     */
    public static void handleRenders() {
        try {
            if (switchMode == 3){drawMid();}
            if (switchMode == 4) {displayPointArraySize(); Physics.update(); Physics.render();}
            else {
                renderParticlesArray(); renderGravityPointsArray(); renderFwParticlesArray();
                renderEmitterArray(); renderFluxArray(); renderObstructArray();
                renderQEDArray(); renderIonsArray(); renderBlackHoleArray();
                renderDuplexArray(); renderPortalArray();
            }
        }catch (IndexOutOfBoundsException e){EException.append(e);}
    }
}