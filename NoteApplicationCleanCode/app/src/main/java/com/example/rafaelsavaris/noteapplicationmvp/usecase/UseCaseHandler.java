package com.example.rafaelsavaris.noteapplicationmvp.usecase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class UseCaseHandler {

    private static UseCaseHandler mInstance;

    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler){
        mUseCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance(){

        if (mInstance == null){
            mInstance = new UseCaseHandler(new UseCaseSchedulerThreadPool());
        }

        return mInstance;

    }

    public <T extends RequestValues, R extends ResponseValue> void execute(final UseCase<T, R> useCase, T requestValues, UseCaseCallback<R> callback){

        useCase.setRequestValues(requestValues);

        useCase.setUseCaseCallback(new UseCaseCallbackWrapper<R>(callback, this));

        mUseCaseScheduler.execute(new Runnable() {

            @Override
            public void run() {

                useCase.run();

            }
        });

    }

    public <V extends ResponseValue> void notifyResponse(final V response, final UseCaseCallback<V> useCaseCallback){
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    public <V extends ResponseValue> void notifyError(final UseCaseCallback useCaseCallback){
        mUseCaseScheduler.onError(useCaseCallback);
    }

}
