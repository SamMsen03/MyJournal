package com.example.myjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.widget.AdapterView.OnItemLongClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.content.Intent;

//This is basically Java, albeit with a few different values. Akin to Java, android studio is very case sensitive.
//You do not have to download libraries to import these things, like java..but I do reccomend doing research.
//You will have to import all of them if you want to create a program similar to this, however!
public class MainActivity extends AppCompatActivity {
static ArrayList<String> notes = new ArrayList<>();
static ArrayAdapter arrayAdapter;
    //This is the 'options menu (3 little dots in the right hand corner)' for the application.
    //I am calling this method from the 'add_note_menu.xml'.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    //This will create a new note upon the pressing of the 'add note' button!
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add_note)
        {
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);
            return true;

        }
        return false;
    }
    //This is where the magic happens.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.ListView);
        //Shared Prefences is used as our permanent storage. I have placed it here so that the program 'documents' that a new note has been created.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.myjournal", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        //If there are no pre existing notes, create a new note.
        if(set == null )
        {
            notes.add("Example note");
        }
        else
        {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        //For the creation of the note, 'set on click' allows you to enter that note and startActivity will allow the user to edit their notes.
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                    startActivity(intent);
            }
        });
        //This is the delete note prompt if the user is to press and hold on the note. Shared Prefences is used as 'permanent storage' here and in other places ex. Note editor
        //as to keep track of the on goings of the program.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                final int itemToDelete = i;
                new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you Sure?")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int i) {
                               notes.remove(itemToDelete);
                               arrayAdapter.notifyDataSetChanged();
                               SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.myjournal", Context.MODE_PRIVATE);

                               HashSet<String> set = new HashSet(MainActivity.notes);
                               sharedPreferences.edit().putStringSet("notes",set).apply();
                           }
                       }
                       ).setNegativeButton("No", null)
                       .show();
               return true;
            }
        });
        }




}


