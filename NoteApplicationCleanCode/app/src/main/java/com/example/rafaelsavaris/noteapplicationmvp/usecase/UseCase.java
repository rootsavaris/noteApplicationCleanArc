package com.example.rafaelsavaris.noteapplicationmvp.usecase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public abstract class UseCase<Q extends RequestValues, P extends ResponseValue> {

    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    public void setRequestValues(Q requestValues){
        mRequestValues = requestValues;
    }

    public Q getRequestValues(){
        return mRequestValues;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    void run(){executeUseCase(mRequestValues);}

    protected abstract void executeUseCase(Q requestValues);

}
