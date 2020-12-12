package com.engine.EngineHelpers;

public interface EModes {
    /**
     * Please Keep Order, Add to end of each enum, don't switch order
     */

    /*Corresponds to Engine Modes*/
    enum ENGINE_MODES {
        NORMAL_MODE(0),
        MULTI_MODE(1),
        FIREWORKS_MODE(2),
        GRAPH_MODE(3),
        RAGDOLL_MODE(4);

        private int value;

        ENGINE_MODES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getValueS() {
            return value + "";
        }
    }

    static ENGINE_MODES getMode(ENGINE_MODES mode, int sign) {
        if (sign > 0) return ENGINE_MODES.values()[(mode.value + 1) % ENGINE_MODES.values().length];
        else
            return ENGINE_MODES.values()[(ENGINE_MODES.values().length + (mode.value - 1)) % ENGINE_MODES.values().length];
    }

    /*Corresponds to Particle Type*/
    enum PARTICLE_TYPES {
        PARTICLE(0),
        GRAVITY_POINT(1),
        EMITTER(2),
        FLUX(3),
        QED(4),
        ION(5),
        BLACK_HOLE(6),
        DUPLEX(7),
        PORTAL(8);

        private int value;

        PARTICLE_TYPES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getValueS() {
            return value + "";
        }

    }

    static PARTICLE_TYPES getMode(PARTICLE_TYPES mode, int sign) {
        if (sign > 0)
            return PARTICLE_TYPES.values()[(mode.value + 1) % PARTICLE_TYPES.values().length];
        else
            return PARTICLE_TYPES.values()[(PARTICLE_TYPES.values().length + (mode.value - 1)) % PARTICLE_TYPES.values().length];
    }

    /*Corresponds to Gravitation Modes*/
    enum GRAVITATION_MODES {
        DEFAULT(0),
        COSINE_SINE(1),
        ARC_TANGENT(2),
        H_WAVE(3),
        V_WAVE(4),
        SPIRALS(5),
        PARTICLE_REPELLENT(6),
        ORGANIC(7),
        FLOW_FIELD(8);

        private int value;

        GRAVITATION_MODES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getValueS() {
            return value + "";
        }

    }

    static GRAVITATION_MODES getMode(GRAVITATION_MODES mode, int sign) {
        if (sign > 0)
            return GRAVITATION_MODES.values()[(mode.value + 1) % GRAVITATION_MODES.values().length];
        else
            return GRAVITATION_MODES.values()[(GRAVITATION_MODES.values().length + (mode.value - 1)) % GRAVITATION_MODES.values().length];
    }

    /*Corresponds to Particle & Fireworks Render Types*/
    enum MOLECULE_RENDER_MODES {
        RECTANGLE_NOFILL(0),
        CIRCLE_NOFILL(1),
        RECTANGLE3D_NOFILL(2),
        NUMBERS(3),
        CUSTOM_TEXT(4),
        RECTANGLE3D_FILL(5),
        CIRCLE_FILL(6),
        SMILEY_FACE(7),
        MUSIC_NOTE_1(8),
        MUSIC_NOTE_2(9),
        HEART(10),
        CENT_SYMBOL(11),
        COPYRIGHT_SYMBOL(12),
        TRADEMARK_SYMBOL(13),
        INFINITY_SYMBOL(14),
        KAPPA_SYMBOL(15),
        SPADE_SYMBOL(16),
        CLUB_SYMBOL(17),
        DIAMOND_SYMBOL(18);

        private int value;

        MOLECULE_RENDER_MODES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getValueS() {
            return value + "";
        }

    }

    static MOLECULE_RENDER_MODES getMode(MOLECULE_RENDER_MODES mode, int sign) {
        if (sign > 0)
            return MOLECULE_RENDER_MODES.values()[(mode.value + 1) % MOLECULE_RENDER_MODES.values().length];
        else
            return MOLECULE_RENDER_MODES.values()[(MOLECULE_RENDER_MODES.values().length + (mode.value - 1)) % MOLECULE_RENDER_MODES.values().length];
    }
}