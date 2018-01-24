package com.example.rafaelsavaris.noteapplicationmvp.notes.add.domain.action;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class UpdateNote extends UseCase<UpdateNote.RequestValues, UpdateNote.ResponseValue> {

    public final NotesRepository mNotesRepository;

    public UpdateNote(NotesRepository notesRepository) {
        this.mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        mNotesRepository.saveNote(requestValues.getNote());

        getUseCaseCallback().onSuccess(new ResponseValue());

    }

    public static final class RequestValues implements com.example.rafaelsavaris.noteapplicationmvp.usecase.RequestValues {

        private final Note mNote;

        public RequestValues(Note note){
            mNote = note;
        }

        public Note getNote() {
            return mNote;
        }
    }

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{
    }

}
