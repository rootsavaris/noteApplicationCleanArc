package com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.action;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class ClearMarkedNotes extends UseCase<ClearMarkedNotes.RequestValues, ClearMarkedNotes.ResponseValue> {

    public final NotesRepository mNotesRepository;

    public ClearMarkedNotes(NotesRepository notesRepository) {
        this.mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        mNotesRepository.clearMarkedNotes();

        getUseCaseCallback().onSuccess(new ResponseValue());

    }

    public static final class RequestValues implements com.example.rafaelsavaris.noteapplicationmvp.usecase.RequestValues {
    }

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{
    }

}
