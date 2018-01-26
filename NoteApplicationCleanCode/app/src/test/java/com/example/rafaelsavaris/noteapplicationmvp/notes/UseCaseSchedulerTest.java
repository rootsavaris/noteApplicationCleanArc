package com.example.rafaelsavaris.noteapplicationmvp.notes;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseCallback;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseScheduler;

/**
 * Created by rafael.savaris on 26/01/2018.
 */

public class UseCaseSchedulerTest implements UseCaseScheduler {

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends ResponseValue> void notifyResponse(V response, UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }

    @Override
    public <V extends ResponseValue> void onError(UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onError();
    }

}
