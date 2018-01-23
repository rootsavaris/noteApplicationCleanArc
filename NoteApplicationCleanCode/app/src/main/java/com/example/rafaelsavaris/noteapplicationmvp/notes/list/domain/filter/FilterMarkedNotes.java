package com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.filter;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class FilterMarkedNotes implements NoteFilter {

    @Override
    public List<Note> filter(List<Note> notes) {

        List<Note> noteList = new ArrayList<>();

        for(Note note : notes){

            if (note.isMarked()){
                noteList.add(note);
            }

        }

        return noteList;

    }

}
