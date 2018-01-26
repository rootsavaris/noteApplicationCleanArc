package com.example.rafaelsavaris.noteapplicationmvp.notes.detail;

import com.example.rafaelsavaris.noteapplicationmvp.notes.UseCaseSchedulerTest;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.CreateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.DeleteNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.GetNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.MarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UnMarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.domain.UpdateNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael.savaris on 11/01/2018.
 */

public class DetailNotePresenterTest {

    private final Note NOTE = new Note("Title", "Text");

    private final Note MARKED_NOTE = new Note("Title", "Text", true);

    private final String INVALID_ID = "";

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private DetailNoteContract.View mView;

    @Captor
    private ArgumentCaptor<NotesDatasource.GetNoteCallBack> mGetNoteCallBackArgumentCaptor;

    private DetailNotePresenter mDetailNotePresenter;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);

        when(mView.isActive()).thenReturn(true);

    }

    @Test
    public void createPresenter_setsThePresenterToView(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        verify(mView).setPresenter(mDetailNotePresenter);

    }

    @Test
    public void getNoteFromRepositoryAndLoadIntoView(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        mDetailNotePresenter.start();

        verify(mNotesRepository).getNote(eq(NOTE.getId()), mGetNoteCallBackArgumentCaptor.capture());

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);

        mGetNoteCallBackArgumentCaptor.getValue().onNoteLoaded(NOTE);

        inOrder.verify(mView).setLoadingIndicator(false);

        verify(mView).showTitle(NOTE.getTitle());

        verify(mView).showText(NOTE.getText());

        verify(mView).showMarkedStatus(false);


    }

    @Test
    public void getMarkedNoteFromRepositoryAndLoadIntoView(){

        mDetailNotePresenter = getPresenter(MARKED_NOTE.getId());

        mDetailNotePresenter.start();

        verify(mNotesRepository).getNote(eq(MARKED_NOTE.getId()), mGetNoteCallBackArgumentCaptor.capture());

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);

        mGetNoteCallBackArgumentCaptor.getValue().onNoteLoaded(MARKED_NOTE);

        inOrder.verify(mView).setLoadingIndicator(false);

        verify(mView).showTitle(MARKED_NOTE.getTitle());

        verify(mView).showText(MARKED_NOTE.getText());

        verify(mView).showMarkedStatus(true);

    }

    @Test
    public void getUnknownNoteFromRepositoryAndLoadIntoView(){

        mDetailNotePresenter = getPresenter(INVALID_ID);

        mDetailNotePresenter.start();

        verify(mView).showMissingNote();

    }

    @Test
    public void deleteNote(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        mDetailNotePresenter.deleteNote();

        verify(mNotesRepository).deleteNote(NOTE.getId());

        verify(mView).showNoteDeleted();

    }

    @Test
    public void markNote(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        mDetailNotePresenter.start();

        mDetailNotePresenter.markNote();

        verify(mNotesRepository).markNote(NOTE.getId());

        verify(mView).showNoteMarked();

    }

    @Test
    public void unMarkNote(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        mDetailNotePresenter.start();

        mDetailNotePresenter.unMarkNote();

        verify(mNotesRepository).unMarkNote(NOTE.getId());

        verify(mView).showNoteUnMarked();

    }


    @Test
    public void showNoteWhenEditing(){

        mDetailNotePresenter = getPresenter(NOTE.getId());

        mDetailNotePresenter.editNote();

        verify(mView).showEditNote(NOTE.getId());

    }

    @Test
    public void invalidNoteIsNotShowWhenEditing(){

        mDetailNotePresenter = getPresenter(INVALID_ID);

        mDetailNotePresenter.editNote();

        verify(mView, never()).showEditNote(INVALID_ID);

        verify(mView).showMissingNote();

    }

    private DetailNotePresenter getPresenter(String noteId){

        UseCaseHandler useCaseHandler = new UseCaseHandler(new UseCaseSchedulerTest());

        GetNote getNote = new GetNote(mNotesRepository);

        MarkNote markNote = new MarkNote(mNotesRepository);

        UnMarkNote unMarkNote = new UnMarkNote(mNotesRepository);

        DeleteNote deleteNote = new DeleteNote(mNotesRepository);

        return new DetailNotePresenter(noteId, useCaseHandler, getNote, markNote, unMarkNote, deleteNote, mView);

    }

}
