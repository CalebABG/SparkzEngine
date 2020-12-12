package com.engine.EngineHelpers;

public enum EINTS {
    PARTICLE_DRAG_AMOUNT(1),
    PARTICLE_BASE_LIFE(65),
    FIREWORKS_AMOUNT(30),
    FIREWORKS_LIFE(90),
    FIREWORKS_WIND(2),
    FIREWORKS_JITTER(5),
    ENGINE_FPS(60),
    ENGINE_TIMER_FPS(1000 / ENGINE_FPS.value, true),
    ENGINE_SAFETY_AMOUNT(120),
    ENGINE_COLOR_CYCLE_RATE(5);

    private int value;
    public boolean exclude = false;

    EINTS() {
    }

    EINTS(int val) {
        this.value = val;
    }

    EINTS(int val, boolean exclude) {
        value = val;
        this.exclude = exclude;
    }

    public void increment() {
        value++;
    }

    public void increment(int val) {
        this.value += val;
    }

    public void decrement() {
        value--;
    }

    public void decrement(int val) {
        this.value -= val;
    }

    public void multiply(int val) {
        this.value *= val;
    }

    public void divide(int val) {
        this.value /= val;
    }

    public void mod(int val) {
        this.value %= val;
    }

    public void setValue(int val) {
        value = val;
    }

    public final int value() {
        return value;
    }

    public final String getValueS() {
        return "" + value;
    }
}
