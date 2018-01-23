package com.example.rafaelsavaris.noteapplicationmvp.usecase;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class UseCaseSchedulerThreadPool implements UseCaseScheduler {

    private final Handler mHandler = new Handler();

    private static final int POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int TIMEOUT = 30;

    ThreadPoolExecutor mThreadPoolExecutor;

    public UseCaseSchedulerThreadPool(){
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends ResponseValue> void notifyResponse(final V response, final UseCaseCallback<V> useCaseCallback) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });

    }

    @Override
    public <V extends ResponseValue> void onError(final UseCaseCallback<V> useCaseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError();
            }
        });
    }

}
