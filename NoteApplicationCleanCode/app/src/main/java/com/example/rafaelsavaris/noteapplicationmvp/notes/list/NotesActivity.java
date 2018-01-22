package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rafaelsavaris.noteapplicationmvp.Injection;
import com.example.rafaelsavaris.noteapplicationmvp.R;
import com.example.rafaelsavaris.noteapplicationmvp.utils.ActivityUtils;


public class NotesActivity extends AppCompatActivity {

    private static final String CURRENT_FILTER_KEY = "CURRENT_FILTER_KEY";

    private NotesPresenter notesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (notesFragment == null) {

            notesFragment = NotesFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), notesFragment, R.id.contentFrame);

        }

        notesPresenter = new NotesPresenter(Injection.providesNotesRepository(this), notesFragment);

        if (savedInstanceState != null){

            NotesFilterType notesFilterType = (NotesFilterType) savedInstanceState.getSerializable(CURRENT_FILTER_KEY);

            notesPresenter.setFilter(notesFilterType);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(CURRENT_FILTER_KEY, notesPresenter.getFilter());

        super.onSaveInstanceState(outState);

    }
}
