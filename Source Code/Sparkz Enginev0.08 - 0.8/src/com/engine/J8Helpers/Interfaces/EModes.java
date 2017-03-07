package com.engine.J8Helpers.Interfaces;

public interface EModes {
    /*Corresponds to Engine Modes*/
    int NORMAL_MODE         = 0;
    int MULTI_MODE          = 1;
    int FIREWORKS_MODE      = 2;
    int GRAPH_MODE          = 3;
    int RAGDOLL_MODE        = 4;

    /*Corresponds to Particle Type*/
    int PARTICLE            = 0;
    int GRAVITY_POINT       = 1;
    int EMITTER             = 2;
    int FLUX                = 3;
    int QED                 = 4;
    int ION                 = 5;
    int BLACK_HOLE          = 6;
    int DUPLEX              = 7;
    int PORTAL              = 8;

    /*Corresponds to Gravitation Modes*/
    int DEFAULT             = 0;
    int COSINE_SINE         = 1;
    int ARC_TANGENT         = 2;
    int H_WAVE              = 3;
    int V_WAVE              = 4;
    int SPIRALS             = 5;
    int PARTICLE_REPELLENT  = 6;
    int ORGANIC             = 7;

    /*Corresponds to Particle & Fireworks Render Types*/
    int RECTANGLE_NOFILL    = 1;
    int CIRCLE_NOFILL       = 2;
    int RECTANGLE3D_NOFILL  = 3;
    int NUMBERS             = 4;
    int CUSTOM_TEXT         = 5;
    int RECTANGLE3D_FILL    = 6;
    int CIRCLE_FILL         = 7;
    int SMILEY_FACE         = 8;
    int MUSIC_NOTE_1        = 9;
    int MUSIC_NOTE_2        = 10;
    int HEART               = 11;
    int CENT_SYMBOL         = 12;
    int COPYRIGHT_SYMBOL    = 13;
    int TRADEMARK_SYMBOL    = 14;
    int INFINITY_SYMBOL     = 15;
    int KAPPA_SYMBOL        = 16;
    int SPADE_SYMBOL        = 17;
    int CLUB_SYMBOL         = 18;
    int DIAMOND_SYMBOL      = 19;
}