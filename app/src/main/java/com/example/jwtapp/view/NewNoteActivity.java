package com.example.jwtapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jwtapp.R;
import com.example.jwtapp.database.NotesDatabase;
import com.example.jwtapp.databinding.ActivityNewNoteBinding;
import com.example.jwtapp.model.Note;

public class NewNoteActivity extends AppCompatActivity {
    private ActivityNewNoteBinding activityNewNoteBinding;
    private NotesDatabase notesDatabase;
    private EditText editContent;
    private Spinner spinnerRating;
    private Button buttonSave, cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_new_note);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user").toString();
        String token = bundle.getString("token").toString();
        setContentView(R.layout.activity_new_note);
        editContent = findViewById(R.id.editContent);
        spinnerRating = findViewById(R.id.spinnerRating);
        buttonSave = findViewById(R.id.buttonSave);
        cancel = findViewById(R.id.cancel);
        notesDatabase = NotesDatabase.getInstance(NewNoteActivity.this);


        Integer[] ratings = new Integer[10];
        for (int i = 0; i < 10; i++) ratings[i] = i + 1;

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, ratings);
        spinnerRating.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = editContent.getText().toString().trim();
                int rating = (int) spinnerRating.getSelectedItem();

                if (content.isEmpty()) {
                    Toast.makeText(NewNoteActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Note note = new Note(user, editContent.getText().toString(), spinnerRating.getSelectedItem().toString());
                notesDatabase.notesDAO().insertOne(note);
                Toast.makeText(NewNoteActivity.this, "Note saved with rating " + rating, Toast.LENGTH_SHORT).show();

                finish();
            }

        });

        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(NewNoteActivity.this, ThirdActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("token", token);
            startActivity(intent);
        });

    }
}