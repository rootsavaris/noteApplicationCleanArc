package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.app.Activity;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.ClearMarkedNotes;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.notes.add.AddEditNoteActivity;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.GetNotes;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.MarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UnMarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseCallback;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;

import java.util.List;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesPresenter implements NotesContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final GetNotes mGetNotes;

    private final MarkNote mMarkNote;

    private final UnMarkNote mUnMarkNote;

    private final ClearMarkedNotes mClearMarkedNotes;

    private final NotesContract.View mView;

    private NotesFilterType mFilterType = NotesFilterType.ALL_NOTES;

    private boolean firstLoad = true;

    public NotesPresenter(UseCaseHandler useCaseHandler, GetNotes getNotes, MarkNote markNote, UnMarkNote unMarkNote, ClearMarkedNotes clearMarkedNotes, NotesContract.View view) {
        mUseCaseHandler = useCaseHandler;
        mGetNotes = getNotes;
        mMarkNote = markNote;
        mUnMarkNote = unMarkNote;
        mClearMarkedNotes = clearMarkedNotes;
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

        MarkNote.RequestValues requestValues = new MarkNote.RequestValues(markedNote.getId());

        mUseCaseHandler.execute(mMarkNote, requestValues, new UseCaseCallback<MarkNote.ResponseValue>() {

            @Override
            public void onSuccess(MarkNote.ResponseValue response) {

                mView.showNoteMarked();
                loadNotes(false, false);

            }

            @Override
            public void onError() {
                mView.showLoadingNotesError();
            }
        });

    }

    @Override
    public void unMarkNote(Note markedNote) {

        UnMarkNote.RequestValues requestValues = new UnMarkNote.RequestValues(markedNote.getId());

        mUseCaseHandler.execute(mUnMarkNote, requestValues, new UseCaseCallback<UnMarkNote.ResponseValue>() {

            @Override
            public void onSuccess(UnMarkNote.ResponseValue response) {
                mView.showNoteUnMarked();
                loadNotes(false, false);
            }

            @Override
            public void onError() {
                mView.showLoadingNotesError();
            }

        });

    }

    @Override
    public void clearMarkedNotes() {

        mUseCaseHandler.execute(mClearMarkedNotes, new ClearMarkedNotes.RequestValues(), new UseCaseCallback<ClearMarkedNotes.ResponseValue>() {
            @Override
            public void onSuccess(ClearMarkedNotes.ResponseValue response) {
                mView.showNotesCleared();
                loadNotes(false, false);
            }

            @Override
            public void onError() {
                mView.showLoadingNotesError();
            }
        });

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
