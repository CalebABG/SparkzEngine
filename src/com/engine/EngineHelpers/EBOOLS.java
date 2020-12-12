package com.engine.EngineHelpers;

public enum EBOOLS {
    ENGINE_RUNNING(false, true),
    ENGINE_IS_PAUSED(false),
    THINKING_PARTICLES(true),
    CONNECT_PARTICLES(false),
    PARTICLE_FRICTION(true),
    PARTICLE_BOUNDLESS(false),
    MOUSE_GRAVITATION(true),
    PARTICLE_IS_SAFE_AMOUNT(true, true),
    CONTROL_IS_DOWN(false, true),
    LEFT_MOUSE_IS_DOWN(false, true),
    RIGHT_MOUSE_IS_DOWN(false, true),
    ENGINE_CYCLE_COLORS(false),
    TOGGLE_GRAVITYPOINTS_LINK_RENDER(false),
    TOGGLE_PARTICLE_LINK_RENDER(false),
    TOGGLE_DUPLEX_MODE(false),
    ENGINE_ENABLE_SMOOTH_RENDER(false)
    ;

    private boolean value;
    public boolean exclude = false;

    EBOOLS(){}
    EBOOLS(boolean val) {this.value = val;}
    EBOOLS(boolean val, boolean exclude) {this.value = val; this.exclude = exclude;}

    public void toggleValue(){this.value = !this.value;}
    public void setValue(boolean val){value = val;}
    public boolean value() {return value;}
    public String valueS(){return ""+value;}
}
