package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Enums.MoleculeRenderMode.*;
import static com.engine.GUIWindows.ParticleTypePicker.particleTypeUIs;
import static com.engine.Utilities.HTMLUtil.HeadingTag;
import static com.engine.Utilities.InputGuard.valueGuardString;

public class ParticleTypeOptions {
    public static void baseParticleOptions(int x) {
        switch (x) {
            case 0: PARTICLE_RENDER_MODE = RECTANGLE_NO_FILL; break;
            case 1: PARTICLE_RENDER_MODE = CIRCLE_NO_FILL; break;
            case 2: PARTICLE_RENDER_MODE = RECTANGLE3D_NO_FILL; break;
            case 3: PARTICLE_RENDER_MODE = NUMBERS; break;
            case 4: PARTICLE_RENDER_MODE = CUSTOM_TEXT;
            baseParticleText = valueGuardString(1, particleTypeUIs[0], baseParticleText, HeadingTag(3,"Enter Custom Text")); break;
            case 5: PARTICLE_RENDER_MODE = RECTANGLE3D_FILL; break;
            case 6: PARTICLE_RENDER_MODE = CIRCLE_FILL; break;
            case 7: PARTICLE_RENDER_MODE = SMILEY_FACE; break;
            case 8: PARTICLE_RENDER_MODE = MUSIC_NOTE_1; break;
            case 9: PARTICLE_RENDER_MODE = MUSIC_NOTE_2; break;
            case 10: PARTICLE_RENDER_MODE = HEART; break;
            case 11: PARTICLE_RENDER_MODE = CENT_SYMBOL; break;
            case 12: PARTICLE_RENDER_MODE = COPYRIGHT_SYMBOL; break;
            case 13: PARTICLE_RENDER_MODE = TRADEMARK_SYMBOL; break;
            case 14: PARTICLE_RENDER_MODE = INFINITY_SYMBOL; break;
            case 15: PARTICLE_RENDER_MODE = KAPPA_SYMBOL; break;
            case 16: PARTICLE_RENDER_MODE = SPADE_SYMBOL; break;
            case 17: PARTICLE_RENDER_MODE = CLUB_SYMBOL; break;
            case 18: PARTICLE_RENDER_MODE = DIAMOND_SYMBOL; break;
            default: break;
        }
    }

    public static void realFireworksOptions(int x) {
        switch (x) {
            case 0: FIREWORKS_RENDER_MODE = RECTANGLE_NO_FILL; break;
            case 1: FIREWORKS_RENDER_MODE = CIRCLE_NO_FILL; break;
            case 2: FIREWORKS_RENDER_MODE = RECTANGLE3D_NO_FILL; break;
            case 3: FIREWORKS_RENDER_MODE = NUMBERS; break;
            case 4: FIREWORKS_RENDER_MODE = CUSTOM_TEXT;
            fireworksParticleText = valueGuardString(1, particleTypeUIs[1], fireworksParticleText, HeadingTag(3,"Enter Custom Text")); break;
            case 5: FIREWORKS_RENDER_MODE = RECTANGLE3D_FILL; break;
            case 6: FIREWORKS_RENDER_MODE = CIRCLE_FILL; break;
            case 7: FIREWORKS_RENDER_MODE = SMILEY_FACE; break;
            case 8: FIREWORKS_RENDER_MODE = MUSIC_NOTE_1; break;
            case 9: FIREWORKS_RENDER_MODE = MUSIC_NOTE_2; break;
            case 10: FIREWORKS_RENDER_MODE = HEART; break;
            case 11: FIREWORKS_RENDER_MODE = CENT_SYMBOL; break;
            case 12: FIREWORKS_RENDER_MODE = COPYRIGHT_SYMBOL; break;
            case 13: FIREWORKS_RENDER_MODE = TRADEMARK_SYMBOL; break;
            case 14: FIREWORKS_RENDER_MODE = INFINITY_SYMBOL; break;
            case 15: FIREWORKS_RENDER_MODE = KAPPA_SYMBOL; break;
            case 16: FIREWORKS_RENDER_MODE = SPADE_SYMBOL; break;
            case 17: FIREWORKS_RENDER_MODE = CLUB_SYMBOL; break;
            case 18: FIREWORKS_RENDER_MODE = DIAMOND_SYMBOL; break;
            default: break;
        }
    }
}