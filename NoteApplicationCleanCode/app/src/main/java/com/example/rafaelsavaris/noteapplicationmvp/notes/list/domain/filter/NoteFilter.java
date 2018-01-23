package com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.filter;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.List;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public interface NoteFilter {

    List<Note> filter(List<Note> notes);

}
