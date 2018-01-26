package com.example.rafaelsavaris.noteapplicationmvp.notes.add;

import com.example.rafaelsavaris.noteapplicationmvp.notes.UseCaseSchedulerTest;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.CreateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.GetNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UpdateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael.savaris on 11/01/2018.
 */

public class AddEditPresenterTest {

    private final String TITLE = "title";

    private final String TEXT = "text";

    private final String EXISTING_ID = "1";

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private AddEditNoteContract.View mView;

    @Captor
    private ArgumentCaptor<NotesDatasource.GetNoteCallBack> mGetNoteCallBackArgumentCaptor;

    private AddEditNotePresenter mAddEditNotePresenter;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);

        when(mView.isActive()).thenReturn(true);

    }

    @Test
    public void createPresenter_setsThePresenterToView(){

        mAddEditNotePresenter = getPresenter(null);

        verify(mView).setPresenter(mAddEditNotePresenter);

    }

    @Test
    public void saveNewNoteToRepository_showsSuccessMessageUi(){

        mAddEditNotePresenter = getPresenter("1");

        mAddEditNotePresenter.saveNote(TITLE, TEXT);

        mNotesRepository.saveNote(any(Note.class));

        verify(mView).showNotesList();

    }

    @Test
    public void saveNote_emptyNoteShowsErrorUi(){

        mAddEditNotePresenter = getPresenter("1");

        mAddEditNotePresenter.saveNote("", "");

        verify(mView).showEmptyNotesError();

    }

    @Test
    public void saveExistingNoteToRepository_showsSuccessMessageUi(){

        mAddEditNotePresenter = getPresenter(EXISTING_ID);

        mAddEditNotePresenter.saveNote(TITLE, TEXT);

        verify(mNotesRepository).saveNote(any(Note.class));

        verify(mView).showNotesList();

    }

    @Test
    public void populateNote_callsRepoAndUpdatesView(){

        Note note = new Note(TITLE, TEXT);

        mAddEditNotePresenter = getPresenter(note.getId());

        mAddEditNotePresenter.populateNote();

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallBackArgumentCaptor.capture());

        assertThat(mAddEditNotePresenter.isDataMissing(), is(true));

        mGetNoteCallBackArgumentCaptor.getValue().onNoteLoaded(note);

        verify(mView).setTitle(note.getTitle());

        verify(mView).setText(note.getText());

        assertThat(mAddEditNotePresenter.isDataMissing(), is(false));

    }

    private AddEditNotePresenter getPresenter(String noteId){

        UseCaseHandler useCaseHandler = new UseCaseHandler(new UseCaseSchedulerTest());

        GetNote getNote = new GetNote(mNotesRepository);

        CreateNote createNote = new CreateNote(mNotesRepository);

        UpdateNote updateNote = new UpdateNote(mNotesRepository);

        return new AddEditNotePresenter(null, useCaseHandler, getNote, createNote, updateNote, mView, true);

    }

}
