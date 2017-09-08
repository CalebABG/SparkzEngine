package com.engine.EngineHelpers;

public enum EINTS {
    /* 0 */    PARTICLE_DRAG_AMOUNT(1),
    /* 1 */    PARTICLE_BASE_LIFE(65),
    /* 2 */    FIREWORKS_AMOUNT(30),
    /* 3 */    FIREWORKS_LIFE(90),
    /* 4 */    FIREWORKS_WIND(2),
    /* 5 */    FIREWORKS_JITTER(5),
    /* 6 */    ENGINE_FPS(60),
    /* 7 */    ENGINE_TIMER_FPS(1000 / ENGINE_FPS.value),
    /* 8 */    ENIGNE_OLD_MOUSE_X(-1),
    /* 9 */    ENIGNE_OLD_MOUSE_Y(-1),
    /* 10 */    ENGINE_MOUSE_DX(0),
    /* 11 */    ENGINE_MOUSE_DY(0),
    /* 12 */    ENGINE_SAFETY_AMOUNT(120),
    /* 13 */    ENGINE_COLOR_CYCLE_RATE(5)
    ;

    private int value;
    EINTS(){}
    EINTS(final int val) {this.value = val;}

    public void increment(){value++;}
    public void increment(int val){this.value += val;}
    public void decrement(){value--;}
    public void decrement(int val){this.value -= val;}
    public void multiply(int val){this.value *= val;}
    public void divide(int val){this.value /= val;}
    public void mod(int val){this.value %= val;}

    public void setValue(int val){value = val;}
    public final int value() {return value;}
    public final String getValueS(){return ""+value;}
}
