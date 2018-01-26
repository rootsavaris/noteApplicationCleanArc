package com.example.rafaelsavaris.noteapplicationmvp.usecase.domain;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.NotesFilterType;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.filter.FilterFactory;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.filter.NoteFilter;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCase;

import java.util.List;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class GetNotes extends UseCase<GetNotes.RequestValues, GetNotes.ResponseValue> {

    public final NotesRepository mNotesRepository;

    private final FilterFactory mFilterFactory;

    public GetNotes(NotesRepository notesRepository, FilterFactory filterFactory) {
        this.mNotesRepository = notesRepository;
        this.mFilterFactory = filterFactory;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        if (requestValues.isForceUpdate()){
            mNotesRepository.refreshNotes();
        }

        mNotesRepository.getNotes(new NotesDatasource.LoadNotesCallBack() {

            @Override
            public void onNotesLoaded(List<Note> notes) {

                NotesFilterType notesFilterType = requestValues.getNotesFilterType();

                NoteFilter noteFilter = mFilterFactory.create(notesFilterType);

                List<Note> filteredNotes = noteFilter.filter(notes);

                ResponseValue responseValue = new ResponseValue(filteredNotes);

                getUseCaseCallback().onSuccess(responseValue);

            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements com.example.rafaelsavaris.noteapplicationmvp.usecase.RequestValues {

        private final NotesFilterType mNotesFilterType;

        private final  boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, NotesFilterType notesFilterType){
            mForceUpdate = forceUpdate;
            mNotesFilterType = notesFilterType;
        }

        public NotesFilterType getNotesFilterType() {
            return mNotesFilterType;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

    }

    public static final class ResponseValue implements com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue{

        private final List<Note> mNotes;

        public ResponseValue(List<Note> notes){
            mNotes = notes;
        }

        public List<Note> getNotes() {
            return mNotes;
        }

    }

}
