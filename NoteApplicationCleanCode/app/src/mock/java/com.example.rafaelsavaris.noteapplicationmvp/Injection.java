package com.example.rafaelsavaris.noteapplicationmvp;

import android.arch.persistence.room.Update;
import android.content.Context;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NoteDatabase;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NotesLocalDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.remote.MockRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.notes.add.domain.action.CreateNote;
import com.example.rafaelsavaris.noteapplicationmvp.notes.add.domain.action.GetNote;
import com.example.rafaelsavaris.noteapplicationmvp.notes.add.domain.action.UpdateNote;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.action.ClearMarkedNotes;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.filter.FilterFactory;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.action.GetNotes;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.action.MarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.action.UnMarkNote;
import com.example.rafaelsavaris.noteapplicationmvp.usecase.UseCaseHandler;
import com.example.rafaelsavaris.noteapplicationmvp.utils.AppExecutors;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class Injection {

    public static NotesRepository providesNotesRepository(Context context){

        NoteDatabase database = NoteDatabase.getInstance(context);

        return NotesRepository.getInstance(MockRemoteDataSource.getInstance(), NotesLocalDataSource.getInstance(new AppExecutors(), database.noteDao()));

    }

    public static UseCaseHandler provideUseCasehandler(){
        return UseCaseHandler.getInstance();
    }

    public static GetNotes provideGetNotes(Context context){
        return new GetNotes(providesNotesRepository(context), new FilterFactory());
    }

    public static MarkNote provideMarkNote(Context context){
        return new MarkNote(providesNotesRepository(context));
    }

    public static UnMarkNote provideUnMarkNote(Context context){
        return new UnMarkNote(providesNotesRepository(context));
    }

    public static ClearMarkedNotes provideClearMarkedNotes(Context context){
        return new ClearMarkedNotes(providesNotesRepository(context));
    }

    public static GetNote provideGetNote(Context context){
        return new GetNote(providesNotesRepository(context));
    }

    public static CreateNote provideCreateNote(Context context){
        return new CreateNote(providesNotesRepository(context));
    }

    public static UpdateNote provideUpdateNote(Context context){
        return new UpdateNote(providesNotesRepository(context));
    }


}
