package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> notes = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter;
    ListView listView;
    Intent intent;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.addNote:
                intent.putExtra("pos",-1);
                startActivity(intent);
                return true;
            default :
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        final SharedPreferences sharedPreferences;
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.zappycode.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes.add("Example Note");
        } else {
            notes = new ArrayList(set);
        }
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra("pos", position);
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete the Note")
                            .setMessage("Are you sure you want to delete the NOTE")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(position);
                                    arrayAdapter.notifyDataSetChanged();
                                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                                    sharedPreferences.edit().putStringSet("notes", set).apply();
                                }
                            })
                            .setNegativeButton("NO", null)
                            .show();
                    return true;
                }
            });
    }
}
