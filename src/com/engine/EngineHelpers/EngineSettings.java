package com.engine.EngineHelpers;

public class EngineSettings {
    public transient boolean
            running = false,
            particleCountWithinThreshold = true, controlKeyIsDown = false;

    public boolean
            paused = false,
            reactiveColorsEnabled = true,
            connectParticles = false,
            particleFriction = true,
            particleBoundless = false,
            particlesGravitateToMouse = true;

}
