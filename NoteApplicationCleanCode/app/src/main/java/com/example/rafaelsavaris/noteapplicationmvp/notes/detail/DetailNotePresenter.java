package com.example.rafaelsavaris.noteapplicationmvp.notes.detail;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.ResponseValue;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseCallback;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.DeleteNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.GetNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.MarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UnMarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.google.common.base.Strings;

/**
 * Created by rafael.savaris on 02/01/2018.
 */

public class DetailNotePresenter implements DetailNoteContract.Presenter {

    private String mNoteId;

    private final DetailNoteContract.View mView;

    private final UseCaseHandler mUseCaseHandler;

    private final GetNote mGetNote;

    private final MarkNote mMarkNote;

    private final UnMarkNote mUnMarkNote;

    private final DeleteNote mDeleteNote;

    public DetailNotePresenter(String noteId, UseCaseHandler useCaseHandler, GetNote getNote, MarkNote markNote, UnMarkNote unMarkNote, DeleteNote deleteNote, DetailNoteContract.View view) {
        mNoteId = noteId;
        mUseCaseHandler = useCaseHandler;
        mGetNote = getNote;
        mMarkNote = markNote;
        mUnMarkNote = unMarkNote;
        mDeleteNote = deleteNote;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mView.setLoadingIndicator(true);

        mUseCaseHandler.execute(mGetNote, new GetNote.RequestValues(mNoteId), new UseCaseCallback<GetNote.ResponseValue>() {

            @Override
            public void onSuccess(GetNote.ResponseValue response) {

                if (!mView.isActive()){
                    return;
                }

                mView.setLoadingIndicator(false);

                Note note = response.getNote();

                if (note == null){
                    mView.showMissingNote();
                } else {
                    showNote(note);
                }

            }

            @Override
            public void onError() {

                if (!mView.isActive()){
                    return;
                }

                mView.showMissingNote();

            }
        });

    }

    private void showNote(Note note) {

        String title = note.getTitle();
        String description = note.getText();

        if (Strings.isNullOrEmpty(title)) {
            mView.hideTitle();
        } else {
            mView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mView.hideText();
        } else {
            mView.showText(description);
        }

        mView.showMarkedStatus(note.isMarked());

    }


    @Override
    public void editNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mView.showEditNote(mNoteId);

    }

    @Override
    public void markNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mUseCaseHandler.execute(mMarkNote, new MarkNote.RequestValues(mNoteId), new UseCaseCallback<MarkNote.ResponseValue>() {
            @Override
            public void onSuccess(MarkNote.ResponseValue response) {
                mView.showNoteMarked();
            }

            @Override
            public void onError() {
            }
        });

    }

    @Override
    public void unMarkNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mUseCaseHandler.execute(mUnMarkNote, new UnMarkNote.RequestValues(mNoteId), new UseCaseCallback<UnMarkNote.ResponseValue>() {
            @Override
            public void onSuccess(UnMarkNote.ResponseValue response) {
                mView.showNoteUnMarked();
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void deleteNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mUseCaseHandler.execute(mDeleteNote, new DeleteNote.RequestValues(mNoteId), new UseCaseCallback<DeleteNote.ResponseValue>() {
            @Override
            public void onSuccess(DeleteNote.ResponseValue response) {
                mView.showNoteDeleted();
            }

            @Override
            public void onError() {

            }
        });

    }

}
