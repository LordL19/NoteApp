package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.noteapp.Activity.InsertNotesActivity;
import com.example.noteapp.Adapter.NotesAdapter;
import com.example.noteapp.Model.Notes;
import com.example.noteapp.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;

    TextView nofilter,hightolow,lowtohigh;
    List<Notes> filternotesallList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecycler=findViewById(R.id.notesRecycler);

        nofilter=findViewById(R.id.nofilter);
        hightolow=findViewById(R.id.hightolow);
        lowtohigh=findViewById(R.id.lowtohigh);

        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
        nofilter.setOnClickListener(v -> {
            loadData(0);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);

        });
        hightolow.setOnClickListener(v -> {
            loadData(1);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });
        lowtohigh.setOnClickListener(v -> {
            loadData(2);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filternotesallList=notes;


            }
        });
        

    }

    private void loadData(int i) {
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList=notes;

                }
            });
        } else if (i==1) {
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList=notes;

                }
            });
            
        } else if (i==2) {
            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList=notes;

                }
            });

        }
    }

    public void setAdapter(List<Notes> notes){
        notesRecycler.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new NotesAdapter(MainActivity.this,notes);
        notesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes,menu);

        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Busca tu nota aquí...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
        ArrayList<Notes> FilterNames = new ArrayList<>();
        for(Notes notes:this.filternotesallList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText))
            {
                FilterNames.add(notes);
            }
        }
        this.adapter.searchNotes(FilterNames);

    }
}