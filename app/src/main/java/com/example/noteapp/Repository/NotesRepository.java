package com.example.noteapp.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.noteapp.Dao.NotesDao;
import com.example.noteapp.Database.NotesDatabase;
import com.example.noteapp.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getallNotes;
    public LiveData<List<Notes>> hightolow;
    public LiveData<List<Notes>> lowtohigh;



    public NotesRepository(Application application){
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao=database.notesDao();
        getallNotes = notesDao.getallNotes();
        hightolow=notesDao.highToLow();
        lowtohigh=notesDao.lowtoHigh();
    }

    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id){
        Log.d("NotesRepository", "Eliminando nota con ID:" + id);
        notesDao.deleteNotes(id);


    }

    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }
}
