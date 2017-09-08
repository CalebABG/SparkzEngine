package com.engine.EngineHelpers;

public enum EFLOATS {
    SINGLE_CLICK_SIZE_MIN(0.8f),
    SINGLE_CLICK_SIZE_MAX(0.9f),
    MULTI_CLICK_SIZE_MIN(0.8f),
    MULTI_CLICK_SIZE_MAX(0.9f),
    FIREWORKS_SIZE_MIN(0.35f),
    FIREWORKS_SIZE_MAX(0.45f),
    PARTICLE_DRAG_SIZE_MIN(0.25f),
    PARTICLE_DRAG_SIZE_MAX(0.45f),
    SINGLE_CLICK_SPEED(5.93f),
    MULTI_CLICK_SPEED(5.85f),
    FIREWORKS_SPEED(5.19f),
    PARTICLE_DRAG_SPEED(1.25f)
    ;

    private float value;
    EFLOATS(){}
    EFLOATS(float val) {this.value = val;}

    public void increment(){value++;}
    public void increment(int val){this.value += val;}
    public void decrement(){value--;}
    public void decrement(float val){this.value -= val;}
    public void multiply(float val){this.value *= val;}
    public void divide(float val){this.value /= val;}
    public void mod(float val){this.value %= val;}

    public void setValue(float val){value = val;}
    public float value() {return value;}
    public String valueS(){return ""+value;}
}
