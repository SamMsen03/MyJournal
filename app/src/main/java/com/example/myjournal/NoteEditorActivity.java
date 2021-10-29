package com.example.myjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.ArrayList;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashSet;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.content.Intent;

public class NoteEditorActivity extends AppCompatActivity {
int noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_editor);
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        //This function allows for the creation of notes. Note, that if there are no notes avaliable then the program will make an example note.
        noteid = intent.getIntExtra("noteId", -1);
        if (noteid != -1)
        {
            editText.setText(MainActivity.notes.get(noteid));
        }
        else
        {
            MainActivity.notes.add("");
            noteid = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }
                //This method below is used to autosave your text while you type and if you back out of the app accidentally.
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    MainActivity.notes.set(noteid, String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    //This serves as our 'permanent storage'. Even if you close the application, the data saves.
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.myjournal", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes",set).apply();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
    }
}
