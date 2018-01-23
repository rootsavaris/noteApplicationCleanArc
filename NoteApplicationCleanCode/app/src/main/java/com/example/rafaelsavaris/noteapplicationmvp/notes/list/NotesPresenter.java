package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.app.Activity;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.notes.add.AddEditNoteActivity;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.usecase.GetNotes;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseCallback;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesPresenter implements NotesContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final GetNotes mGetNotes;

    private final NotesRepository mRepository;

    private final NotesContract.View mView;

    private NotesFilterType mFilterType = NotesFilterType.ALL_NOTES;

    private boolean firstLoad = true;

    public NotesPresenter(UseCaseHandler useCaseHandler, GetNotes getNotes, NotesRepository repository, NotesContract.View view) {
        mUseCaseHandler = useCaseHandler;
        mGetNotes = getNotes;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    public void setFilter(NotesFilterType mFilterType){
        this.mFilterType = mFilterType;
    }

    public NotesFilterType getFilter(){
        return this.mFilterType;
    }

    @Override
    public void result(int requestCode, int resultCode) {

        if (AddEditNoteActivity.REQUEST_ADD_NOTE == requestCode && Activity.RESULT_OK == resultCode){
            mView.showSuccessfullySavedMessage();
        }

    }

    @Override
    public void start() {
        loadNotes(false);
    }

    @Override
    public void loadNotes(boolean forceUpdate) {

        loadNotes(forceUpdate || firstLoad, true);

        firstLoad = false;

    }

    @Override
    public void markNote(Note markedNote) {
        mRepository.markNote(markedNote);
        mView.showNoteMarked();
        loadNotes(false, false);
    }

    @Override
    public void unMarkNote(Note markedNote) {
        mRepository.unMarkNote(markedNote);
        mView.showNoteUnMarked();
        loadNotes(false, false);
    }

    @Override
    public void clearMarkedNotes() {
        mRepository.clearMarkedNotes();
        mView.showNotesCleared();
        loadNotes(false, false);
    }

    @Override
    public void addNewNote() {
        mView.showAddNewNote();
    }

    @Override
    public void openNoteDetails(Note note) {
        mView.showNoteDetailUi(note.getId());
    }

    private void loadNotes(boolean forceUpdate, final boolean showLoading){

        if (showLoading){
            mView.setLoadingIndicator(true);
        }

        GetNotes.RequestValues requestValues = new GetNotes.RequestValues(forceUpdate, mFilterType);

        mUseCaseHandler.execute(mGetNotes, requestValues, new UseCaseCallback<GetNotes.ResponseValue>() {

            @Override
            public void onSuccess(GetNotes.ResponseValue response) {

                List<Note> notes = response.getNotes();

                if (!mView.isActive()){
                    return;
                }

                if (showLoading){
                    mView.setLoadingIndicator(false);
                }

                processNotes(notes);

            }

            @Override
            public void onError() {

                if (!mView.isActive()){
                    return;
                }

                mView.showLoadingNotesError();

            }
        });

    }

    private void processNotes(List<Note> notes){

        if (notes.isEmpty()){
            processEmptyNotes();
        } else {
            mView.showNotes(notes);
            showFilterLabel();
        }

    }

    private void processEmptyNotes(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showNoMarkedNotes();
                break;

            default:
                mView.showNoNotes();
                break;

        }

    }

    private void showFilterLabel(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showMarkedFilterLabel();
                break;
            default:
                mView.showAllFilterLabel();
                break;

        }

    }

}
