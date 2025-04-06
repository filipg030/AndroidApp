package com.example.jwtapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jwtapp.R;
import com.example.jwtapp.database.NotesDatabase;
import com.example.jwtapp.databinding.ActivityThirdBinding;
import com.example.jwtapp.model.Note;
import com.example.jwtapp.model.NoteAdapter;
import com.example.jwtapp.model.PostAdapter;
import com.example.jwtapp.viewmodel.LoggedUserViewModel;

import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private ActivityThirdBinding activityThirdBinding;
    private NotesDatabase notesDatabase;
    private LoggedUserViewModel loggedUserViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityThirdBinding = ActivityThirdBinding.inflate(getLayoutInflater());
        View view = activityThirdBinding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user").toString();
        String token = bundle.getString("token").toString();
        notesDatabase = NotesDatabase.getInstance(ThirdActivity.this);
        loggedUserViewModel = new LoggedUserViewModel();
        loggedUserViewModel.setupLoggedUser(user);
        activityThirdBinding.setLoggedUserViewModel(loggedUserViewModel);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteList = notesDatabase.notesDAO().getAll();
        noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        Toolbar toolbar = activityThirdBinding.topToolbar;
        setSupportActionBar(toolbar);

        activityThirdBinding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, NewNoteActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("token", token);
            startActivity(intent);
        });

        activityThirdBinding.fab2.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("token", token);
            startActivity(intent);
        });

    }
}