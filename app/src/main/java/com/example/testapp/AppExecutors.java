package com.example.testapp;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {

    private final Executor mDiskIO;

    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mMainThread = mainThread;
    }

    /**
     * Instantiates a new App executors.
     */
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(),
                new MainThreadExecutor());
    }

    /**
     * Disk io executor.
     *
     * @return the executor
     */
    public Executor diskIO() {
        return mDiskIO;
    }

    /**
     * Main thread executor.
     *
     * @return the executor
     */
    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
