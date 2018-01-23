package com.example.rafaelsavaris.noteapplicationmvp.notes.list.domain.filter;

import com.example.rafaelsavaris.noteapplicationmvp.notes.list.NotesFilterType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafael.savaris on 23/01/2018.
 */

public class FilterFactory {

    private static final Map<NotesFilterType, NoteFilter> mFilters = new HashMap<>();

    public FilterFactory(){
        mFilters.put(NotesFilterType.ALL_NOTES, new FilterAllNotes());
        mFilters.put(NotesFilterType.MARKED_NOTES, new FilterMarkedNotes());
    }

    public NoteFilter create(NotesFilterType filterType){
        return mFilters.get(filterType);
    }

}
