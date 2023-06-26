package com.example.noteapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.noteapp.Model.Notes;
import com.example.noteapp.R;
import com.example.noteapp.ViewModel.NotesViewModel;
import com.example.noteapp.databinding.ActivityInsertNotesBinding;

import java.text.DateFormat;
import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title,subtitle,notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        //Modificacion de botones de prioridad
        binding.redPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.baseline_done_24);

            priority = "3";
        });

        binding.yellowPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.baseline_done_24);
            binding.redPriority.setImageResource(0);
            priority = "2";
        });

        binding.greenPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(R.drawable.baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "1";
        });



        binding.doneNotesBtn.setOnClickListener(v -> {

            title=binding.notesTitle.getText().toString();
            subtitle=binding.notesSubtitle.getText().toString();
            notes=binding.notesData.getText().toString();

            CreateNotes(title,subtitle,notes);

        });
    }

    private void CreateNotes(String title, String subtitle, String notes) {

        Date date= new Date();
       // CharSequence sequence = DateFormat.getDateTimeInstance().format("");
        //CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());
        CharSequence sequence = android.text.format.DateFormat.format("MMMM d, yyyy", date.getTime());
        //CharSequence sequence = DateFormat.format("MMMM d, YYYY", date.getTime());
        Notes notes1 = new Notes();
        notes1.notesTitle=title;
        notes1.notesSubtitle=subtitle;
        notes1.notes=notes;
        notes1.notesPriority=priority;
        notes1.notesDate=sequence.toString();

        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Se creó la nota exitosamente", Toast.LENGTH_SHORT).show();

        finish();
    }
}