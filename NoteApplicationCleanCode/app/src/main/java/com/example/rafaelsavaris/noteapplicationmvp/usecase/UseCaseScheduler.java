package com.example.rafaelsavaris.noteapplicationmvp.usecase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends ResponseValue> void notifyResponse(final V response, final UseCaseCallback<V> useCaseCallback);

    <V extends ResponseValue> void onError(final UseCaseCallback<V> useCaseCallback);

}
