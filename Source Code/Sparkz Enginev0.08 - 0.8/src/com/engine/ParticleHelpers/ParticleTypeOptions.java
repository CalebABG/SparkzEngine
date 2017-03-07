package com.engine.ParticleHelpers;

import static com.engine.J8Helpers.Interfaces.EModes.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.GUIWindows.ParticleTypeUI.particleTypeUIs;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.InputWrapper;

public class ParticleTypeOptions {
    public static void baseParticleOptions(int x) {
        try {
            switch (x) {
                case 1: particleRenderType = RECTANGLE_NOFILL; break;
                case 2: particleRenderType = CIRCLE_NOFILL; break;
                case 3: particleRenderType = RECTANGLE3D_NOFILL; break;
                case 4: particleRenderType = NUMBERS; break;
                case 5: particleRenderType = CUSTOM_TEXT; baseParticleText = InputWrapper.valueGuardString(1, particleTypeUIs[0], "*", "Enter Custom Text"); break;
                case 6: particleRenderType = RECTANGLE3D_FILL; break;
                case 7: particleRenderType = CIRCLE_FILL; break;
                case 8: particleRenderType = SMILEY_FACE; break;
                case 9: particleRenderType = MUSIC_NOTE_1; break;
                case 10: particleRenderType = MUSIC_NOTE_2; break;
                case 11: particleRenderType = HEART; break;
                case 12: particleRenderType = CENT_SYMBOL; break;
                case 13: particleRenderType = COPYRIGHT_SYMBOL; break;
                case 14: particleRenderType = TRADEMARK_SYMBOL; break;
                case 15: particleRenderType = INFINITY_SYMBOL; break;
                case 16: particleRenderType = KAPPA_SYMBOL; break;
                case 17: particleRenderType = SPADE_SYMBOL; break;
                case 18: particleRenderType = CLUB_SYMBOL; break;
                case 19: particleRenderType = DIAMOND_SYMBOL; break;
                default: break;
            }
        }catch (Exception l){EException.append(l);}
    }

    public static void realFireworksOptions(int x) {
        try {
            switch (x) {
                case 1: fireworksRenderType = RECTANGLE_NOFILL; break;
                case 2: fireworksRenderType = CIRCLE_NOFILL; break;
                case 3: fireworksRenderType = RECTANGLE3D_NOFILL; break;
                case 4: fireworksRenderType = NUMBERS; break;
                case 5: fireworksRenderType = CUSTOM_TEXT; fireworksParticleText = InputWrapper.valueGuardString(1, particleTypeUIs[1], "*", "Enter Custom Text"); break;
                case 6: fireworksRenderType = RECTANGLE3D_FILL; break;
                case 7: fireworksRenderType = CIRCLE_FILL; break;
                case 8: fireworksRenderType = SMILEY_FACE; break;
                case 9: fireworksRenderType = MUSIC_NOTE_1; break;
                case 10: fireworksRenderType = MUSIC_NOTE_2; break;
                case 11: fireworksRenderType = HEART; break;
                case 12: fireworksRenderType = CENT_SYMBOL; break;
                case 13: fireworksRenderType = COPYRIGHT_SYMBOL; break;
                case 14: fireworksRenderType = TRADEMARK_SYMBOL; break;
                case 15: fireworksRenderType = INFINITY_SYMBOL; break;
                case 16: fireworksRenderType = KAPPA_SYMBOL; break;
                case 17: fireworksRenderType = SPADE_SYMBOL; break;
                case 18: fireworksRenderType = CLUB_SYMBOL; break;
                case 19: fireworksRenderType = DIAMOND_SYMBOL; break;
                default: break;
            }
        }catch (Exception ex){EException.append(ex);}
    }
}