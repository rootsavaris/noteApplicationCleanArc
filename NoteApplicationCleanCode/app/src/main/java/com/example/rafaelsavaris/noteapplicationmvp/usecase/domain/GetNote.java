package com.example.rafaelsavaris.noteapplicationmvp.usecase.domain;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class GetNote extends UseCase<GetNote.RequestValues, GetNote.ResponseValue> {

    public final NotesRepository mNotesRepository;

    public GetNote(NotesRepository notesRepository) {
        this.mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        mNotesRepository.getNote(requestValues.getIdNote(), new NotesDatasource.GetNoteCallBack() {

            @Override
            public void onNoteLoaded(Note note) {

                if (note != null){

                    ResponseValue responseValue = new ResponseValue(note);

                    getUseCaseCallback().onSuccess(responseValue);

                } else{
                    getUseCaseCallback().onError();
                }

            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements com.example.rafaelsavaris.noteapplicationmvp.usecase.RequestValues {

        private final String mIdNote;

        public RequestValues(String idNote){
            mIdNote = idNote;
        }

        public String getIdNote() {
            return mIdNote;
        }
    }

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{

        private final Note mNote;

        public ResponseValue(Note note){
            mNote = note;
        }

        public Note getNote() {
            return mNote;
        }
    }

}
