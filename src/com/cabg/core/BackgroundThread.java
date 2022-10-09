package com.cabg.core;

import javax.swing.*;

public class BackgroundThread {
    public static void run(final Runnable runnable) {
        SwingWorker<Void, Object> s = new SwingWorker<>() {
            protected Void doInBackground() {
                runnable.run();
                return null;
            }
        };
        s.execute();
    }
}
