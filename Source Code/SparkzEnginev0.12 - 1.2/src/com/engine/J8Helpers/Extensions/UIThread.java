package com.engine.J8Helpers.Extensions;

import javax.swing.*;

public class UIThread {
    public static void openUI(final Runnable runnable){
        SwingWorker s = new SwingWorker() {
            protected Object doInBackground() throws Exception {
                runnable.run();
                return null;
            }
        };
        s.execute();
        // Get s ready for garbage collector
        s = null;
    }
}
