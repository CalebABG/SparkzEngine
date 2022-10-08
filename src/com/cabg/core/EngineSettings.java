package com.cabg.core;

import com.cabg.enums.EngineMode;
import com.cabg.enums.GravitationMode;
import com.cabg.enums.MoleculeRenderMode;
import com.cabg.enums.ParticleType;

import static com.cabg.enums.EngineMode.NORMAL;
import static com.cabg.enums.GravitationMode.DEFAULT;
import static com.cabg.enums.MoleculeRenderMode.RECTANGLE_NO_FILL;
import static com.cabg.enums.ParticleType.PARTICLE;

public class EngineSettings {
    public transient boolean
            running,
            controlKeyIsDown,
            leftMouseButtonIsDown,
            rightMouseButtonIsDown;

    public boolean
            paused,
            reactiveColorsEnabled = true,
            connectParticles,
            particleFriction = true,
            particleBoundless,
            particlesGravitateToMouse = true,
            cycleReactiveColors,
            showGravityPointsLink,
            showParticlesLink,
            duplexContain,
            smoothRenderingEnabled;

    public int
            particleDragAmount = 1,
            particleLife = 65,
            fireworksAmount = 30,
            fireworksLife = 90,
            fireworksWind = 2,
            fireworksJitter = 5,
            fireworksParticleSafetyAmount = 120,
            desiredFramesPerSecond = 60,
            reactiveColorsCycleRateInSeconds = 5;

    public float
            singleClickSizeMin = 0.8f,
            singleClickSizeMax = 0.9f,
            multiClickSizeMin = 0.8f,
            multiClickSizeMax = 0.9f,
            fireworksSizeMin = 0.35f,
            fireworksSizeMax = 0.45f,
            particleDragSizeMin = 0.25f,
            particleDragSizeMax = 0.45f,
            singleClickSpeed = 5.85f,
            multiClickSpeed = 5.95f,
            fireworksSpeed = 5.19f,
            particleDragSpeed = 1.25f;

    public EngineMode engineMode = NORMAL;
    public ParticleType particleType = PARTICLE;
    public GravitationMode gravitationMode = DEFAULT;
    public MoleculeRenderMode particleRenderMode = RECTANGLE_NO_FILL;
    public MoleculeRenderMode fireworksRenderMode = RECTANGLE_NO_FILL;

    public void toggleGraphicsSmoothing() {
        smoothRenderingEnabled = !smoothRenderingEnabled;
    }

    public void toggleParticleFriction() {
        particleFriction = !particleFriction;
    }

    public void toggleMouseGravitation() {
        particlesGravitateToMouse = !particlesGravitateToMouse;
    }

    public void toggleConnectedParticlesMode() {
        connectParticles = !connectParticles;
    }

    public void toggleReactiveColors() {
        reactiveColorsEnabled = !reactiveColorsEnabled;
    }

    public void toggleParticleBoundsChecking() {
        particleBoundless = !particleBoundless;
    }

    public void togglePause() {
        paused = !paused;
    }

    public void toggleDuplexMode() {
        duplexContain = !duplexContain;
    }

    public void toggleParticlesLinkVisibility() {
        showParticlesLink = !showParticlesLink;
    }

    public void toggleGravityPointsLinkVisibility() {
        showGravityPointsLink = !showGravityPointsLink;
    }

    public void changeEngineMode(boolean advance) {
        engineMode = EngineMethods.getMode(engineMode, advance);
    }

    public void changeParticleType(boolean advance) {
        particleType = EngineMethods.getMode(particleType, advance);
    }

}
