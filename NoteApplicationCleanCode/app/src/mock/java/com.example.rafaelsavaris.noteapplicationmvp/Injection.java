package com.example.rafaelsavaris.noteapplicationmvp;

import android.content.Context;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NoteDatabase;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NotesLocalDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.remote.MockRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.filter.FilterFactory;
import com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.usecase.GetNotes;
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

}
