package com.example.rafaelsavaris.noteapplicationmvp.data.source.remote;

import android.os.Handler;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesRemoteDataSource implements NotesDatasource {

    private static NotesRemoteDataSource instance;

    private static final int TIME_SERVICE = 5000;

    private final static Map<String, Note> NOTES_DATA;

    static {
        NOTES_DATA = new LinkedHashMap<>(2);
        addNote("Note 1", "This is the Note1");
        addNote("Note 2", "This is the Note2");
    }

    public static NotesRemoteDataSource getInstance(){

        if (instance == null){
            instance = new NotesRemoteDataSource();
        }

        return instance;

    }

    private static void addNote(String title, String description) {
        Note note = new Note(title, description);
        NOTES_DATA.put(note.getId(), note);
    }

    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                loadNotesCallBack.onNotesLoaded(Lists.newArrayList(NOTES_DATA.values()));
            }

        }, TIME_SERVICE);

    }

    @Override
    public void getNote(String noteId, final GetNoteCallBack getNoteCallBack) {

        final Note note = NOTES_DATA.get(noteId);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNoteCallBack.onNoteLoaded(note);
            }
        }, TIME_SERVICE);

    }

    @Override
    public void deleteAllNotes() {
        NOTES_DATA.clear();
    }

    @Override
    public void saveNote(Note note) {
        NOTES_DATA.put(note.getId(), note);
    }

    @Override
    public void refreshNotes() {
    }

    @Override
    public void markNote(Note note) {

        Note markedNote = new Note(note.getTitle(), note.getText(), note.getId(), true);

        NOTES_DATA.put(markedNote.getId(), markedNote);

    }

    @Override
    public void markNote(String noteId) {}

    @Override
    public void unMarkNote(Note note) {

        Note markedNote = new Note(note.getTitle(), note.getText(), note.getId());

        NOTES_DATA.put(markedNote.getId(), markedNote);

    }

    @Override
    public void unMarkNote(String noteId) {

    }

    @Override
    public void clearMarkedNotes() {

        Iterator<Map.Entry<String, Note>> it = NOTES_DATA.entrySet().iterator();

        while (it.hasNext()){

            Map.Entry<String, Note> entry = it.next();

            if (entry.getValue().isMarked()){
                it.remove();
            }

        }

    }

    @Override
    public void deleteNote(String noteId) {

        NOTES_DATA.remove(noteId);

    }

}
