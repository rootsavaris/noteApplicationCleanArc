package com.example.rafaelsavaris.noteapplicationmvp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rafaelsavaris.noteapplicationmvp.usecase.model.Note;

import java.util.List;

/**
 * Created by rafael.savaris on 04/01/2018.
 */

@Dao
public interface NoteDao {

    @Query("SELECT * from notes")
    List<Note> getNotes();

    @Query("SELECT * from notes WHERE id = :noteId")
    Note getNoteById(String noteId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Update
    int updateNote(Note note);

    @Query("UPDATE notes SET marked = :marked WHERE id = :noteId")
    void updateMarked(String noteId, boolean marked);

    @Query("DELETE FROM notes WHERE id = :noteId")
    int deleteNoteById(String noteId);

    @Query("DELETE FROM notes WHERE marked = 1")
    int deleteMarkedNotes();

    @Query("DELETE FROM notes")
    void deleteNotes();

}
