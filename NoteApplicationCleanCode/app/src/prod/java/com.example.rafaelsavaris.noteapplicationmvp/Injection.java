package com.example.rafaelsavaris.noteapplicationmvp;

import android.content.Context;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NoteDatabase;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.local.NotesLocalDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.remote.NotesRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationmvp.utils.AppExecutors;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class Injection {

    public static NotesRepository providesNotesRepository(Context context){

        NoteDatabase database = NoteDatabase.getInstance(context);

        return NotesRepository.getInstance(NotesRemoteDataSource.getInstance(), NotesLocalDataSource.getInstance(new AppExecutors(), database.noteDao()));

    }

}
