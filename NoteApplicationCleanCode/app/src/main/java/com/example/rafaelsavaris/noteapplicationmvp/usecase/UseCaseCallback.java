package com.example.rafaelsavaris.noteapplicationmvp.usecase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public interface UseCaseCallback<R> {

    void onSuccess(R response);

    void onError();

}
