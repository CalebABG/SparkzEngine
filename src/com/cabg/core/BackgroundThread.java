package com.cabg.core;

import javax.swing.*;

public class BackgroundThread {
    public static void run(final Runnable runnable) {
        SwingWorker s = new SwingWorker() {
            protected Object doInBackground() {
                runnable.run();
                return null;
            }
        };
        s.execute();
    }
}
