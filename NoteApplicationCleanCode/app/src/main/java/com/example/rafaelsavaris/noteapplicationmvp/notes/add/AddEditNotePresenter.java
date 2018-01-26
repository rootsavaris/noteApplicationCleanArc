package com.example.rafaelsavaris.noteapplicationmvp.notes.add;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.CreateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.GetNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UpdateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseCallback;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public class AddEditNotePresenter implements AddEditNoteContract.Presenter{

    private boolean mIsMarked = false;

    private final AddEditNoteContract.View mView;

    private boolean mIsDataMissing;

    private String mNoteId;

    private final UseCaseHandler mUseCaseHandler;

    private final GetNote mGetNote;

    private final CreateNote mCreateNote;

    private final UpdateNote mUpdateNote;

    public AddEditNotePresenter(String noteId, UseCaseHandler useCaseHandler, GetNote getNote, CreateNote createNote, UpdateNote updateNote, AddEditNoteContract.View view, boolean shouldLoadDataFromRepo) {
        mNoteId = noteId;
        mUseCaseHandler = useCaseHandler;
        mGetNote = getNote;
        mCreateNote = createNote;
        mUpdateNote = updateNote;
        mView = view;
        mIsDataMissing = shouldLoadDataFromRepo;

        mView.setPresenter(this);

    }

    @Override
    public void start() {

        if (!isNewNote() && mIsDataMissing){
            populateNote();
        }

    }

    @Override
    public void saveNote(String title, String text) {

        if (isNewNote()){
            createNote(title, text);
        } else {
            updateNote(title, text);
        }

    }

    public void populateNote() {

        mUseCaseHandler.execute(mGetNote, new GetNote.RequestValues(mNoteId), new UseCaseCallback<GetNote.ResponseValue>() {

            @Override
            public void onSuccess(GetNote.ResponseValue response) {

                Note note = response.getNote();

                if (mView.isActive()) {
                    mView.setTitle(note.getTitle());
                    mView.setText(note.getText());
                    mIsMarked = note.isMarked();
                }

                mIsDataMissing = false;


            }

            @Override
            public void onError() {

                if (mView.isActive()) {
                    mView.showEmptyNotesError();
                }

            }
        });

    }

    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewNote(){
        return mNoteId == null;
    }

    private void createNote(String title, String text){

        Note note = new Note(title, text);

        if (note.isEmpty()){
            mView.showEmptyNotesError();
        } else {

            mUseCaseHandler.execute(mCreateNote, new CreateNote.RequestValues(note), new UseCaseCallback<CreateNote.ResponseValue>() {

                @Override
                public void onSuccess(CreateNote.ResponseValue response) {
                    mView.showNotesList();
                }

                @Override
                public void onError() {
                }
            });

        }

    }

    private void updateNote(String title, String text){

        Note note = new Note(title, text, mNoteId, mIsMarked);

        mUseCaseHandler.execute(mUpdateNote, new UpdateNote.RequestValues(note), new UseCaseCallback<UpdateNote.ResponseValue>() {
            @Override
            public void onSuccess(UpdateNote.ResponseValue response) {
                mView.showNotesList();
            }

            @Override
            public void onError() {}
        });

    }

}
