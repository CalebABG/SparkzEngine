package com.engine.EngineHelpers;

public enum EBOOLS {
    /* 0 */    ENGINE_RUNNING(false),
    /* 1 */    ENGINE_IS_PAUSED(false),
    /* 2 */    THINKING_PARTICLES(true),
    /* 3 */    CONNECT_PARTICLES(false),
    /* 4 */    PARTICLE_FRICTION(true),
    /* 5 */    PARTICLE_BOUNDLESS(false),
    /* 6 */    MOUSE_GRAVITATION(true),
    /* 7 */    PARTICLE_IS_SAFE_AMOUNT(true),
    /* 8 */    CONTROL_IS_DOWN(false),
    /* 9 */    LEFT_MOUSE_IS_DOWN(false),
    /* 10 */    RIGHT_MOUSE_IS_DOWN(false),
    /* 11 */    ENGINE_CYCLE_COLORS(false),
    /* 12 */    TOGGLE_GRAVITYPOINTS_LINK_RENDER(false),
    /* 13 */    TOGGLE_PARTICLE_LINK_RENDER(false),
    /* 14 */    TOGGLE_DUPLEX_MODE(false),
    /* 15 */    ENGINE_ENABLE_SMOOTH_RENDER(false)

    ;

    private boolean value;
    EBOOLS(){}
    EBOOLS(boolean val) {this.value = val;}

    public void toggleValue(){this.value = !this.value;}
    public void setValue(boolean val){value = val;}
    public boolean value() {return value;}
    public String valueS(){return ""+value;}
}
