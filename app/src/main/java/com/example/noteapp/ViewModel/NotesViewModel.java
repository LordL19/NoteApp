package com.example.noteapp.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.noteapp.Model.Notes;
import com.example.noteapp.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {


    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> hightolow;
    public LiveData<List<Notes>> lowtohigh;

    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getallNotes;
        hightolow=repository.hightolow;
        lowtohigh=repository.lowtohigh;
    }

    public void insertNote(Notes notes){

        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        Log.d("NotesViewModel", "Eliminando nota con ID: " + id);
        repository.deleteNotes(id);

    }

    public void updateNote(Notes notes){
        Log.d("NotesViewModel", "Actualizando nota con ID: " + notes.id);
        repository.updateNotes(notes);
    }
}
