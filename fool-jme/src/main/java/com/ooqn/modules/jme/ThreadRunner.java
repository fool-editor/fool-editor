package com.ooqn.modules.jme;

import com.ooqn.modules.FxJmeApplication;

import javafx.application.Platform;

/**
 * Execute code in either the JavaFX or JME thread.
 * Performs a thread check before executing the runnable, and if the tread is not correct, runs in the correct thread.
 *
 * @author jayfella
 *
 */
public class ThreadRunner {

    private final FxJmeApplication application;

    public ThreadRunner(FxJmeApplication application) {
        this.application = application;
    }

    public void runInJfxThread(Runnable runnable) {

        if (Platform.isFxApplicationThread()) {
            runnable.run();
        }
        else {
            Platform.runLater(runnable);
        }
    }

    public void runInJmeThread(Runnable runnable) {

        if (application.isJmeThread()) {
            runnable.run();
        }
        else {
            application.enqueue(runnable);
        }

    }

}
