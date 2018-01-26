package com.example.rafaelsavaris.noteapplicationmvp.usecase.domain;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class DeleteNote extends UseCase<DeleteNote.RequestValues, DeleteNote.ResponseValue> {

    public final NotesRepository mNotesRepository;

    public DeleteNote(NotesRepository notesRepository) {
        this.mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        mNotesRepository.deleteNote(requestValues.getNoteId());

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

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{
    }

}
