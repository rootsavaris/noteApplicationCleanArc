package com.example.rafaelsavaris.noteapplicationmvp.usecase.domain;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class UnMarkNote extends UseCase<UnMarkNote.RequestValues, UnMarkNote.ResponseValue> {

    public final NotesRepository mNotesRepository;

    public UnMarkNote(NotesRepository notesRepository) {
        this.mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        String noteId = requestValues.getNoteId();

        mNotesRepository.unMarkNote(noteId);

        getUseCaseCallback().onSuccess(new ResponseValue());

    }

    public static final class RequestValues implements com.example.rafaelsavaris.noteapplicationmvp.usecase.RequestValues {

        private final String mNoteId;

        public RequestValues(String noteId){
            mNoteId = noteId;
        }

        public String getNoteId() {
            return mNoteId;
        }
    }

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{}

}
