package com.example.rafaelsavaris.noteapplicationmvp.usecase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class UseCaseCallbackWrapper<V extends ResponseValue> implements UseCaseCallback<V> {

    private final UseCaseCallback<V> mCaseCallback;

    private final UseCaseHandler mUseCaseHandler;

    public UseCaseCallbackWrapper(UseCaseCallback<V> caseCallback, UseCaseHandler useCaseHandler){
        mCaseCallback = caseCallback;
        mUseCaseHandler = useCaseHandler;
    }

    @Override
    public void onSuccess(V response) {
        mUseCaseHandler.notifyResponse(response, mCaseCallback);
    }

    @Override
    public void onError() {
        mUseCaseHandler.notifyError(mCaseCallback);
    }

}
