package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.InputWrapper;

public class ParticleTypeOptions {
    public static void baseParticleOptions(int x) {
        try {
            switch (x) {
                case 1: particleMode = 1; break;
                case 2: particleMode = 2; break;
                case 3: particleMode = 3; break;
                case 4: particleMode = 4; break;
                case 5: particleMode = 5; baseParticleText = InputWrapper.valueGuardString(1, "*", "Enter Custom Text"); break;
                case 6: particleMode = 6; break;
                case 7: particleMode = 7; break;
                case 8: particleMode = 8; break;
                case 9: particleMode = 9; break;
                case 10: particleMode = 10; break;
                case 11: particleMode = 11; break;
                case 12: particleMode = 12; break;
                case 13: particleMode = 13; break;
                case 14: particleMode = 14; break;
                case 15: particleMode = 15; break;
                case 16: particleMode = 16; break;
                case 17: particleMode = 17; break;
                case 18: particleMode = 18; break;
                case 19: particleMode = 19; break;
                default: break;
            }
        }catch (Exception l){EException.append(l);}
    }

    public static void realFireworksOptions(int x) {
        try {
            switch (x) {
                case 1: rfParticleMode = 1; break;
                case 2: rfParticleMode = 2; break;
                case 3: rfParticleMode = 3; break;
                case 4: rfParticleMode = 4; break;
                case 5: rfParticleMode = 5; fireworksParticleText = InputWrapper.valueGuardString(1, "*", "Enter Custom Text"); break;
                case 6: rfParticleMode = 6; break;
                case 7: rfParticleMode = 7; break;
                case 8: rfParticleMode = 8; break;
                case 9: rfParticleMode = 9; break;
                case 10: rfParticleMode = 10; break;
                case 11: rfParticleMode = 11; break;
                case 12: rfParticleMode = 12; break;
                case 13: rfParticleMode = 13; break;
                case 14: rfParticleMode = 14; break;
                case 15: rfParticleMode = 15; break;
                case 16: rfParticleMode = 16; break;
                case 17: rfParticleMode = 17; break;
                case 18: rfParticleMode = 18; break;
                case 19: rfParticleMode = 19; break;
                default: break;
            }
        }catch (Exception ex){EException.append(ex);}
    }
}